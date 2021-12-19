package com.example.content.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioDisCardClient {
    public static void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress("192.168.33.37",18999);

        //获取通道
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);

        //不断的自旋 等待连接完成，或者干别的事情
        while (!socketChannel.finishConnect()){
            System.out.println("进来一次 还没连接成功");
        }
        System.out.println("客户端连接成功");

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.put("hello world chenqiang".getBytes());
        byteBuffer.flip();
        //发送到服务器
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();

    }

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 10; i++) {
            startClient();
        }

    }
}
