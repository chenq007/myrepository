package com.example.content.handler;

import com.example.content.data.LoginRequestPacket;
import com.example.content.data.LoginResponsePacket;
import com.example.content.netty.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("cqq");
        loginRequestPacket.setPassWord("pwd123");


        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()){
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() +": 客户端登陆成功");
        } else {
            System.out.println(new Date()+ ": 客户端登陆失败，原因："+loginResponsePacket.getReason());
        }
    }
}
