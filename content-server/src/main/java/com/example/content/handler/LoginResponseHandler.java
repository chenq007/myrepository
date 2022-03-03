package com.example.content.handler;

import com.example.content.data.LoginRequestPacket;
import com.example.content.data.LoginResponsePacket;
import com.example.content.data.Session;
import com.example.content.decode.SessionUtil;
import com.example.content.netty.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId = loginResponsePacket.getUserId();
        String userNmae = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()){
            System.out.println("【"+userNmae+"】登录成功，userId为：" +userId);
            SessionUtil.bindSession(new Session(userId,userNmae),ctx.channel());
        } else {
            System.out.println("【"+userNmae+"】: 客户端登陆失败，原因："+loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
