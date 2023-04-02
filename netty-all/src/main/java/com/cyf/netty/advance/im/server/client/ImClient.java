package com.cyf.netty.advance.im.server.client;

import com.cyf.netty.advance.im.server.message.ChatRequestMessage;
import com.cyf.netty.advance.im.server.message.LoginRequestMessage;
import com.cyf.netty.advance.im.server.message.LoginResponseMessage;
import com.cyf.netty.advance.protocol.endec.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 陈一锋
 * @date 2022/8/7 1:21 下午
 */
@Slf4j
public class ImClient {

    public static void main(String[] args) {
        final NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            final Bootstrap b = new Bootstrap();
            LoggingHandler loggingHandler = new LoggingHandler();
            CountDownLatch countDownLatch = new CountDownLatch(1);
            AtomicBoolean login = new AtomicBoolean(false);
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0));
//                            p.addLast(loggingHandler);
                            p.addLast(new MessageCodec());
                            p.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    //连接建立后回触发 active事件
                                    new Thread(() -> {
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("请输入账号:");
                                        String username = scanner.nextLine();
                                        System.out.println("请输入密码:");
                                        String password = scanner.nextLine();
                                        LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                                        ctx.writeAndFlush(loginRequestMessage);
                                        System.out.println("等待后续操作");
                                        //等待结果
                                        try {
                                            countDownLatch.await();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        if (!login.get()) {
                                            System.out.println("密码错误,断开连接");
                                            ctx.channel().close();
                                            return;
                                        }
                                        System.out.println("=============");
                                        System.out.println("send [username] [content]");
                                        System.out.println("gsend [group name] [content]");
                                        System.out.println("gcreate [group name] [m1,m2,m3..]");
                                        System.out.println("gmembers [group name]");
                                        System.out.println("gjoin [group name]");
                                        System.out.println("gquit [group name]");
                                        System.out.println("quit");
                                        System.out.println("=============");
                                        while (scanner.hasNext()) {
                                            String line = scanner.nextLine();
                                            String[] s = line.split(" ");
                                            String common = s[0];
                                            if ("send".equals(common)) {
                                                ctx.writeAndFlush(new ChatRequestMessage(s[1], s[2]));
                                            }
                                        }

                                    }).start();
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info("客户端接收到服务器消息:{}", msg);
                                    if (msg instanceof LoginResponseMessage) {
                                        LoginResponseMessage m = (LoginResponseMessage) msg;
                                        login.set(m.isSuccess());
                                        countDownLatch.countDown();
                                    }
                                }
                            });
                        }
                    })
                    .connect("localhost", 8080)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
