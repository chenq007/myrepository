package com.example.content.netty;

import com.example.content.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
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
                 ch.pipeline().addLast(new FirstClientHandler());
                }
                });

        contect(bootstrap,"127.0.0.1",8080,MAX_RETRY);
         

    }

    private static void contect(Bootstrap bootstrap,String host,int port,int retry){
        bootstrap.connect(host,port).addListener(feature ->{
           if (feature.isSuccess()){
               System.out.println("连接成功!");
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
}
