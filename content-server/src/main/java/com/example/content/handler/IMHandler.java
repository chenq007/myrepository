package com.example.content.handler;

import com.example.content.data.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.example.content.enmus.Command.*;

@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap =new HashMap<>();
        handlerMap.put(MESSAGE_REQUEST,MessageRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST,JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST,CreatGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST,QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST,ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(SEND_TO_GROUP_REQUEST,SendToGroupRequestHandler.INSTANCE);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
            handlerMap.get(packet.getCommand()).channelRead(channelHandlerContext,packet);
    }
}
