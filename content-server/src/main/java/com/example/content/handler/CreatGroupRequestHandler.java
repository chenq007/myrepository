package com.example.content.handler;

import com.example.content.data.CreatGroupRequestPacket;
import com.example.content.data.CreatGroupResponsePacket;
import com.example.content.decode.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CreatGroupRequestHandler extends SimpleChannelInboundHandler<CreatGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreatGroupRequestPacket creatGroupRequestPacket) throws Exception {
        List<String> userIdList = creatGroupRequestPacket.getUserIdList();
        CreatGroupResponsePacket responsePacket = new CreatGroupResponsePacket();

        List<String> userNmaeList = new ArrayList<>();
        //创建群聊 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        if (userIdList != null && userIdList.size()>0) {
            responsePacket.setGroupId(getGroupId());
            responsePacket.setSuccess(true);

            for (int i = 0; i < userIdList.size(); i++) {
                Channel channel = SessionUtil.getChannel(userIdList.get(i));
                if (channel != null) {
                    channelGroup.add(channel);
                    userNmaeList.add(SessionUtil.getSession(channel).getUserName());
                }
            }
            responsePacket.setUserNameList(userNmaeList);


            SessionUtil.setGroupChannelMap(responsePacket.getGroupId(),channelGroup);
            //给每个客户端发送群聊拉取通知
            channelGroup.writeAndFlush(responsePacket);


            System.out.print("群创建成功,id为【"+responsePacket.getGroupId()+"】");
            System.out.println("群成员有:"+responsePacket.getUserNameList());
        }
    }

    private String getGroupId(){
        return  UUID.randomUUID().toString().split("-")[0];
    }
}
