package com.example.content.handler;

import com.example.content.data.QuitGroupRequestPacket;
import com.example.content.data.QuitGroupResponsePacket;
import com.example.content.decode.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {

        String groupId = quitGroupRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channelHandlerContext.channel());

        //退群响应 发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket =new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(quitGroupRequestPacket.getGroupId());
        quitGroupResponsePacket.setSuccess(true);

        channelHandlerContext.channel().writeAndFlush(quitGroupResponsePacket);

    }
}
