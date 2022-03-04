package com.example.content.handler;

import com.example.content.data.CreatGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class CreatGroupResponseHandler extends SimpleChannelInboundHandler<CreatGroupResponsePacket> {

    public static final CreatGroupResponseHandler INSTANCE = new CreatGroupResponseHandler();

    private CreatGroupResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreatGroupResponsePacket creatGroupResponsePacket) throws Exception {
        System.out.print("群创建成功,id为【"+creatGroupResponsePacket.getGroupId()+"】");
        System.out.println("群成员有:"+creatGroupResponsePacket.getUserNameList());
    }
}
