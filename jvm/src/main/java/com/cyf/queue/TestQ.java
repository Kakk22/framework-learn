package com.cyf.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;


/**
 * wait/notify实现的堵塞队列
 *
 * @author 陈一锋
 * @date 2022/4/4 1:40 下午
 */
public class TestQ {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 4; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.push(new Message(id, "数据" + id));
            }, "生产者" + i).start();
        }
        Thread.sleep(2000);

        new Thread(() -> {
            while (true) {
                Message take = messageQueue.take();
                System.out.println("获取数据:" + take.toString());
            }
        }, "消费者").start();
    }
}

@Slf4j(topic = "c.MessageQueue")
class MessageQueue {

    private final LinkedList<Message> list = new LinkedList<>();
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public Message take() {
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.error("获取数据为空，开始等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //不为空
            Message message = list.removeFirst();
            log.debug("获取数据成功,开始唤醒线程生产");
            list.notifyAll();
            return message;
        }
    }

    public void push(Message message) {
        synchronized (list) {
            while (list.size() == this.capacity) {
                try {
                    log.debug("数据到达限制,开始等待消费");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //队列未满
            list.add(message);
            log.debug("添加数据成功,开始唤醒线程消费");
            list.notifyAll();
        }
    }
}


@ToString
@Getter
@AllArgsConstructor
class Message {
    private final Integer id;
    private final Object value;
}
