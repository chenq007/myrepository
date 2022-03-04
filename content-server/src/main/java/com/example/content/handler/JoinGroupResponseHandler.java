package com.example.content.handler;

import com.example.content.data.JoinGroupResponsetPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsetPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsetPacket joinGroupResponsetPacket) throws Exception {
       if (joinGroupResponsetPacket.isSuccess() ) {
           String groupId = joinGroupResponsetPacket.getGroupId();
           System.out.println("加入群聊【"+groupId+"】成功");
       }
    }
}
