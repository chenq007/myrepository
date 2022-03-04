package com.example.content.handler;

import com.example.content.data.ListGroupMembersRequestPacket;
import com.example.content.data.ListGroupMembersResponsePacket;
import com.example.content.data.Session;
import com.example.content.decode.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE =new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        String groupId = listGroupMembersRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        List<String> userNameList = new ArrayList<>();

        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            userNameList.add(session.getUserName());
        }

        ListGroupMembersResponsePacket listGroupMembersResponsePacket =new ListGroupMembersResponsePacket();
        listGroupMembersResponsePacket.setGroupId(groupId);
        listGroupMembersResponsePacket.setUserNameList(userNameList);

        channelHandlerContext.channel().writeAndFlush(listGroupMembersResponsePacket);

    }
}
