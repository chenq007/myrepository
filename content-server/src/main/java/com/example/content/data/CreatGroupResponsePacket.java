package com.example.content.data;

import lombok.Data;

import java.util.List;

import static com.example.content.enmus.Command.CREATE_GROUP_REQUEST;
import static com.example.content.enmus.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreatGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
