package com.example.content.data;

import lombok.Data;

import java.io.Serializable;

import static com.example.content.enmus.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet implements Serializable {

    private String userId;

    //用户名
    private String userName;

    //密码
    private String passWord;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

}
