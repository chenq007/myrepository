package com.example.content.data;


import static com.example.content.enmus.Command.SEND_HEART_BEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return SEND_HEART_BEAT_REQUEST;
    }
}
