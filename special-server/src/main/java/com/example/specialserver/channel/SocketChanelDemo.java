package com.example.specialserver.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class SocketChanelDemo {


    public static  void test() throws IOException {

        SocketChannel socketChannel=SocketChannel.open();

        socketChannel.configureBlocking(false);

        socketChannel.connect(new InetSocketAddress("127.0.0.1",80));


    }
}
