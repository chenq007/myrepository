package com.example.content.decode;

import com.example.content.data.Session;
import com.example.content.serializ.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new HashMap<>();

    private static final Map<String, ChannelGroup> groupChannelMap = new HashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static void setGroupChannelMap(String groupId,ChannelGroup channelGroup) {
        groupChannelMap.put(groupId,channelGroup);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static ChannelGroup getChannelGroup(String groupId){return groupChannelMap.get(groupId);}
}
