package com.example.content.data;

import lombok.Data;

import java.util.List;

import static com.example.content.enmus.Command.LIST_GROUP_MEMBERS_REQUEST;
import static com.example.content.enmus.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
