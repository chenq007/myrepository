package com.example.content.netty;

import com.example.content.console.LoginConsoleCommand;
import com.example.content.data.LoginRequestPacket;
import com.example.content.data.LoginResponsePacket;
import com.example.content.data.MessageRequestPacket;
import com.example.content.data.PackerCodeC;
import com.example.content.decode.PacketDecoder;
import com.example.content.decode.PacketEncoder;
import com.example.content.decode.SessionUtil;
import com.example.content.decode.Spliter;
import com.example.content.handler.*;
import com.example.content.serializ.impl.ConsoleCommandManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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
                 ch.pipeline().addLast(new Spliter());
                 ch.pipeline().addLast(new PacketDecoder());
                 //登陆响应处理器
                 ch.pipeline().addLast(new LoginResponseHandler());
                 //收消息处理器
                 ch.pipeline().addLast(new MessageResponseHnadler());
                 //创建群响应处理器
                 ch.pipeline().addLast(new CreatGroupResponseHandler());
                 //加群响应处理器
                 ch.pipeline().addLast(new JoinGroupResponseHandler());
                 //获取群成员处理器
                 ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                //退群处理器
                 ch.pipeline().addLast(new QuitGroupResponseHandler());
                 //群发消息处理器
                 ch.pipeline().addLast(new SendToGroupResponseHandler());
                 //编码
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
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner =new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() ->{
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner,channel);
                } else {
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }



}
