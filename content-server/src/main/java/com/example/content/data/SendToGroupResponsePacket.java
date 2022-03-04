package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.SEND_TO_GROUP_RESPONSE;

@Data
public class SendToGroupResponsePacket extends Packet {

    private String groupId;

    private String message;

    private String fromUserName;

    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_RESPONSE;
    }
}
