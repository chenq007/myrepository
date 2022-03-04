package com.example.content.handler;

import com.example.content.data.LoginRequestPacket;
import com.example.content.data.LoginResponsePacket;
import com.example.content.data.PackerCodeC;
import com.example.content.data.Session;
import com.example.content.decode.SessionUtil;
import com.example.content.netty.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;
import java.util.UUID;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if (vaild(loginRequestPacket)) {
            String userId = randomUserId();
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("登陆成功");
            loginResponsePacket.setUserId(userId);
            //设置会话信息
            SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUserName()),channelHandlerContext.channel());
            System.out.println("【"+loginRequestPacket.getUserName()+"】 登陆成功");
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("登陆失败,校验不通过");
        }
        //编码);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }


    private boolean vaild(LoginRequestPacket loginRequestPacket){
        return true;
    }


    private String randomUserId(){
        return String.valueOf(new Random().nextInt(50)+1);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
