package com.example.content.handler;

import com.example.content.data.QuitGroupResponsePacket;
import com.example.content.decode.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if (quitGroupResponsePacket.isSuccess()){
            String userName = SessionUtil.getSession(channelHandlerContext.channel()).getUserName();
            System.out.println("用户【"+userName+"】退出群聊");
        }
    }
}
