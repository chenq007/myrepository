package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet{

    private String groupId;
    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
