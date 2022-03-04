package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.SEND_TO_GROUP_REQUEST;

@Data
public class SendToGroupRequestPacket extends Packet {

    private String groupId;

    private String message;

    @Override
    public Byte getCommand() {
        return SEND_TO_GROUP_REQUEST;
    }
}
