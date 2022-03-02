package com.example.content.handler;

import com.example.content.data.MessageRequestPacket;
import com.example.content.data.MessageResponsePacket;
import com.example.content.data.PackerCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复 【"+messageRequestPacket.getMessage()+"】");
        System.out.println("服务端回复 【"+messageRequestPacket.getMessage()+"】");
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
