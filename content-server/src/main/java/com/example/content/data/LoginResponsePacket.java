package com.example.content.data;

import lombok.Data;

import java.io.Serializable;

import static com.example.content.enmus.Command.LOGIN_REQUEST;
import static com.example.content.enmus.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet implements Serializable {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }

}
