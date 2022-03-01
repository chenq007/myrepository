package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
