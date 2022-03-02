package com.example.content.handler;

import com.example.content.data.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        //解码
        Packet packet = PackerCodeC.getInstance().decode(byteBuf);

        //判断是否是登陆的请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
//            loginResponsePacket.setVersion(packet.getVersion());
            if (vaild(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                loginResponsePacket.setReason("登陆成功");
            }else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("登陆失败,校验不通过");
            }

            //编码
            ctx.channel().writeAndFlush(loginResponsePacket);

        } else if (packet instanceof MessageRequestPacket) {
        //处理消息
        MessageRequestPacket messageRequestPacket= (MessageRequestPacket) packet;
            System.out.println(new Date() + ":收到客户端消息" +messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复 【"+messageRequestPacket.getMessage()+"】");
            ctx.channel().writeAndFlush(messageResponsePacket);


        }

    }

    private boolean vaild(LoginRequestPacket loginRequestPacket){
        String passWord = loginRequestPacket.getPassWord();
        if ("pwd123".equals(passWord)) {
            return true;
        }
        return false;
    }
}
