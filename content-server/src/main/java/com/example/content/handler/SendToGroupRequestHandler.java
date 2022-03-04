package com.example.content.handler;

import com.example.content.data.SendToGroupRequestPacket;
import com.example.content.data.SendToGroupResponsePacket;
import com.example.content.decode.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupRequestPacket sendToGroupRequestPacket) throws Exception {
        String groupId = sendToGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        SendToGroupResponsePacket sendToGroupResponsePacket = new SendToGroupResponsePacket();
        sendToGroupResponsePacket.setGroupId(groupId);
        sendToGroupResponsePacket.setMessage(sendToGroupRequestPacket.getMessage());
        sendToGroupResponsePacket.setFromUserName(SessionUtil.getSession(channelHandlerContext.channel()).getUserName());
        channelGroup.writeAndFlush(sendToGroupResponsePacket);
    }
}
