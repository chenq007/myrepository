package com.example.content.handler;

import com.alibaba.fastjson.JSON;
import com.example.content.data.MessageRequestPacket;
import com.example.content.data.MessageResponsePacket;
import com.example.content.data.PackerCodeC;
import com.example.content.data.Session;
import com.example.content.decode.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        //拿到发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        //通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
            System.out.println(JSON.toJSONString(messageResponsePacket));
        } else {
            System.err.println("【"+ messageRequestPacket.getToUserId() +"】 不在线，发送失败!");
        }
        }

}
