package com.cyf.nettybook.protocol;

import cn.hutool.http.HttpStatus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Date;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * websocket 文本服务器
 *
 * @author 陈一锋
 * @date 2021/1/12 20:33
 **/
public class WebsocketServer {

    private static final int PORT = 9091;

    public static void main(String[] args) throws InterruptedException {
        new WebsocketServer().run();
    }

    private void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new HttpServerCodec());
                            // 这里目的是将HTTP消息多个部分组合成一条完整的HTTP消息
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            // 主要用于支持浏览器和服务端进行web socket通信
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new WebSocketServerHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("websocket server start success with port:" + PORT);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

        private WebSocketServerHandshaker handshaker;

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            if (msg instanceof FullHttpRequest) {
                handleHttpRequest(ctx, (FullHttpRequest) msg);
            } else if (msg instanceof WebSocketFrame) {
                handleWebsocketFrame(ctx, (WebSocketFrame) msg);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
            // 如果HTTP解码失败,返回http异常
            if (!request.decoderResult().isSuccess()
                    || !("websocket").equals(request.headers().get("Upgrade"))) {
                sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
                return;
            }
            //构建握手协议 本地测试
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:9091/websocket", null, false);
            handshaker = wsFactory.newHandshaker(request);
            if (handshaker == null) {
                //不支持websocket版本
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), request);
            }
        }

        private void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame webSocketFrame) {
            // 判断是否关闭链路的指令
            if (webSocketFrame instanceof CloseWebSocketFrame) {
                handshaker.close(ctx.channel(), ((CloseWebSocketFrame) webSocketFrame).retain());
                return;
            }
            // 判断是否是ping指令
            if (webSocketFrame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                return;
            }
            // 本案例只支持文本 不支持二进制数据
            if (!(webSocketFrame instanceof TextWebSocketFrame)) {
                throw new UnsupportedOperationException(String.format("%s frame type not support", webSocketFrame.getClass().getName()));
            }

            //返回应答消息
            String req = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("receive messages:" + req);
            ctx.channel().write(new TextWebSocketFrame(req + " ,欢迎使用Netty Websocket服务,现在时刻:" + new Date(System.currentTimeMillis()).toString()));
        }

        private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
            //返回应答给客户端
            if (resp.status().code() != HttpStatus.HTTP_OK) {
                ByteBuf byteBuf = Unpooled.copiedBuffer(resp.status().toString(), UTF_8);
                ctx.channel().write(byteBuf);
                byteBuf.release();

            }
            //如果非keep-Alive 关闭连接
            ChannelFuture future = ctx.channel().writeAndFlush(resp);
            if (resp.status().code() != HttpStatus.HTTP_OK | !HttpUtil.isKeepAlive(req)) {
                //关闭连接
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }


}
