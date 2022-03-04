package com.example.content.data;

import static com.example.content.enmus.Command.LOGOUT_RESPONSE;

public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
