package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.JOIN_GROUP_REQUEST;
import static com.example.content.enmus.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsetPacket extends Packet{

    private String groupId;

    private boolean success;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
