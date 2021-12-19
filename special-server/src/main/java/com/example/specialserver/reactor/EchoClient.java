package com.example.specialserver.reactor;



import com.example.specialserver.util.Dateutil;
import com.example.specialserver.util.Logger;
import com.example.specialserver.util.ThreadUtil;
import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class EchoClient {

    public void start() throws IOException {

        InetSocketAddress address =
                new InetSocketAddress("192.168.33.37",
                        18999);

        // 1、获取通道（channel）
        SocketChannel socketChannel = SocketChannel.open(address);
        System.out.println("客户端连接成功");
        // 2、切换成非阻塞模式
        socketChannel.configureBlocking(false);
        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        //不断的自旋、等待连接完成，或者做一些其他的事情
        while (!socketChannel.finishConnect()) {

        }
        System.out.println("客户端启动成功！");

        //启动接受线程
        Processer processer = new Processer(socketChannel);
        Commander commander = new Commander(processer);
        new Thread(commander).start();
        new Thread(processer).start();

    }

    static class Commander implements Runnable {
        Processer processer;

        Commander(Processer processer) throws IOException {
            //Reactor初始化
            this.processer = processer;
        }

        public void run() {
            while (!Thread.interrupted()) {

                ByteBuffer buffer = processer.getSendBuffer();

                Scanner scanner = new Scanner(System.in);
                while (processer.hasData.get()) {
                    System.out.println("还有消息没有发送完，请稍等");
                    ThreadUtil.sleepMilliSeconds(1000);

                }
                System.out.println("请输入发送内容:");
                if (scanner.hasNext()) {

                    String next = scanner.next();
                    buffer.put((Dateutil.getNow() + " >>" + next).getBytes());

                    processer.hasData.set(true);
                }

            }
        }
    }


    @Data
    static class Processer implements Runnable {
        ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        protected AtomicBoolean hasData = new AtomicBoolean(false);

        final Selector selector;
        final SocketChannel channel;

        Processer(SocketChannel channel) throws IOException {
            //Reactor初始化
            selector = Selector.open();

            this.channel = channel;
            channel.register(selector,
                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()) {
                        SelectionKey sk = it.next();
                        if (sk.isWritable()) {

                            if (hasData.get()) {
                                SocketChannel socketChannel = (SocketChannel) sk.channel();
                                sendBuffer.flip();
                                // 操作三：发送数据
                                socketChannel.write(sendBuffer);
                                sendBuffer.clear();
                                hasData.set(false);
                            }

                        }
                        if (sk.isReadable()) {
                            // 若选择键的IO事件是“可读”事件,读取数据
                            SocketChannel socketChannel = (SocketChannel) sk.channel();

                            int length = 0;
                            while ((length = socketChannel.read(readBuffer)) > 0) {
                                readBuffer.flip();
                                System.out.println(("server echo:" + new String(readBuffer.array(), 0, length)));
                                readBuffer.clear();
                            }

                        }
                        //处理结束了, 这里不能关闭select key，需要重复使用
                        //selectionKey.cancel();
                    }
                    selected.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }
}
