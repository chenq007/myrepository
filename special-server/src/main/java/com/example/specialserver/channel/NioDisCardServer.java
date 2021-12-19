package com.example.specialserver.channel;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class NioDisCardServer {

    public static void startServer() throws IOException {

        //获取选择器
        Selector selector=Selector.open();
        //获取通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("192.168.33.37",8500));
        System.out.println("服务器启动成功!!!!!!!!");

        //注册到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮训感兴趣的IO就绪事件(选择键集合)
        while (selector.select() > 0){
            //获取选择键集合
            Iterator<SelectionKey> selectionKeys=selector.selectedKeys().iterator();

            while (selectionKeys.hasNext()){
                //获取单个的选择器,并处理
                SelectionKey selectionKey=selectionKeys.next();
                //判断key是什么事件
                if (selectionKey.isAcceptable()){
                    System.out.println("连接就绪");
                    //io事件是 连接就绪 事件，就获取客户端连接
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //客户端的通道也需要注册
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()){
                    System.out.println("数据 可读就绪");
                    //io事件是可读就绪事件 ，读取数据
                    SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
                    //读到数据后给丢弃
                    ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer)) >0 ){
                        byteBuffer.flip();
                        System.out.println(byteBuffer.array() );
                        System.out.println("数据给丢弃了.................");
                        byteBuffer.clear();
                    }
                    socketChannel.close();
                }
                //移除选择键
                selectionKeys.remove();
            }
        }
        //关闭连接
        serverSocketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }

}
