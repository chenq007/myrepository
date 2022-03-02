package com.example.content.handler;

import com.example.content.data.LoginRequestPacket;
import com.example.content.data.LoginResponsePacket;
import com.example.content.data.PackerCodeC;
import com.example.content.netty.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (vaild(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setReason("登陆成功");

            LoginUtil.markAsLogin(channelHandlerContext.channel());
        }else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("登陆失败,校验不通过");
        }
        //编码);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }


    private boolean vaild(LoginRequestPacket loginRequestPacket){
        String passWord = loginRequestPacket.getPassWord();
        if ("pwd123".equals(passWord)) {
            return true;
        }
        return false;
    }
}
