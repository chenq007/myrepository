package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.QUIT_GROUP_REQUEST;
import static com.example.content.enmus.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
