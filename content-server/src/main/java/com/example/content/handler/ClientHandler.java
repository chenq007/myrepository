package com.example.content.handler;

import com.example.content.data.*;
import com.example.content.netty.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆");

        //创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("cqq");
        loginRequestPacket.setPassWord("pwd123");

        ByteBuf byteBuf = PackerCodeC.getInstance().encode(ctx.alloc(), loginRequestPacket);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PackerCodeC.getInstance().decode(byteBuf);

        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if (loginResponsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() +": 客户端登陆成功");
            } else {
                System.out.println(new Date()+ ": 客户端登陆失败，原因："+loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ":收到服务端的消息：" +messageResponsePacket.getMessage());
        }
    }
}
