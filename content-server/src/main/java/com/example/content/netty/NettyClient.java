package com.example.content.netty;

import com.example.content.data.LoginResponsePacket;
import com.example.content.data.MessageRequestPacket;
import com.example.content.data.PackerCodeC;
import com.example.content.decode.PacketDecoder;
import com.example.content.decode.PacketEncoder;
import com.example.content.handler.ClientHandler;
import com.example.content.handler.FirstClientHandler;
import com.example.content.handler.LoginResponseHandler;
import com.example.content.handler.MessageResponseHnadler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private final static int MAX_RETRY = 10;
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap= new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch){
                 ch.pipeline().addLast(new PacketDecoder());
                 ch.pipeline().addLast(new LoginResponseHandler());
                 ch.pipeline().addLast(new MessageResponseHnadler());
                 ch.pipeline().addLast(new PacketEncoder());
                }
                });

        contect(bootstrap,"127.0.0.1",8081,MAX_RETRY);
         

    }

    private static void contect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(feature ->{
           if (feature.isSuccess()){
               System.out.println("连接成功,启动线程控制台!");
               Channel channel = ((ChannelFuture) feature).channel();
               startConsoleThread(channel);
           }else if (retry ==0){
               System.out.println("重试次数已经用完，放弃连接!");
           }else {
               int order = (MAX_RETRY - retry) + 1;

               int delay = 1 << order;
               System.out.println(new Date() + ":链接失败，第" + order +"次重连............");
               bootstrap.config().group().schedule(() ->contect(bootstrap,host,port,retry - 1),delay, TimeUnit.SECONDS);
           }
        });
    }

    private static void startConsoleThread(Channel channel){
        new Thread(() ->{
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)){
                    System.out.println("输入消息发送至服务器");
                    Scanner sc =new Scanner(System.in);
                    String line = sc.nextLine();
                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                        channel.writeAndFlush(packet);

                }
            }
        }).start();
    }
}
