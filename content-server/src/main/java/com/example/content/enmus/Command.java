package com.example.content.enmus;

import io.netty.buffer.ByteBuf;

public interface Command {
    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;

    Byte CREATE_GROUP_REQUEST= 5;

    Byte LOGOUT_REQUEST = 6;

    Byte LOGOUT_RESPONSE = 7;

    Byte CREATE_GROUP_RESPONSE = 8;

    Byte JOIN_GROUP_REQUEST = 9;

    Byte JOIN_GROUP_RESPONSE = 10;

    Byte LIST_GROUP_MEMBERS_REQUEST = 11;

    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;

    Byte QUIT_GROUP_REQUEST = 13;

    Byte QUIT_GROUP_RESPONSE = 14;

    Byte SEND_TO_GROUP_REQUEST = 15;

    Byte SEND_TO_GROUP_RESPONSE = 16;

    Byte SEND_HEART_BEAT_REQUEST = 17;

    Byte SEND_HEART_BEAT_RESPONSE = 18;

}
