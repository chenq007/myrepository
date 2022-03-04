package com.example.content.data;

import static com.example.content.enmus.Command.SEND_HEART_BEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return SEND_HEART_BEAT_RESPONSE;
    }
}
