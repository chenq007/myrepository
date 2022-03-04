package com.example.content.handler;

import com.example.content.data.SendToGroupResponsePacket;
import com.example.content.data.Session;
import com.example.content.decode.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {

    public static final SendToGroupResponseHandler INSTANCE =new SendToGroupResponseHandler();

    private SendToGroupResponseHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupResponsePacket sendToGroupResponsePacket) throws Exception {
        System.out.println("群成员【"+sendToGroupResponsePacket.getFromUserName()+"】发送消息： "+sendToGroupResponsePacket.getMessage());
    }
}
