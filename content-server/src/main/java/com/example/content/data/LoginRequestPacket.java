package com.example.content.data;

import static com.example.content.enmus.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet {

    private Integer userId;

    //用户名
    private String userName;

    //密码
    private String passWord;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }

}
