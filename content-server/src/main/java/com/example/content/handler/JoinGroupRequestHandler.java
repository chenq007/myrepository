package com.example.content.handler;

import com.example.content.data.JoinGroupRequestPacket;
import com.example.content.data.JoinGroupResponsetPacket;
import com.example.content.decode.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(channelHandlerContext.channel());

        //构造加群响应发送给客户端
        JoinGroupResponsetPacket joinGroupResponsetPacket = new JoinGroupResponsetPacket();
        joinGroupResponsetPacket.setGroupId(groupId);
        joinGroupResponsetPacket.setSuccess(true);

        channelHandlerContext.channel().writeAndFlush(joinGroupResponsetPacket);
    }
}
