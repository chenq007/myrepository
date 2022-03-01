package com.example.content.data;

import lombok.Data;

import static com.example.content.enmus.Command.MESSAGE_REQUEST;
import static com.example.content.enmus.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
