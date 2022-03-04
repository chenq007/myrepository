package com.example.content.data;

import lombok.Data;

import java.util.List;

import static com.example.content.enmus.Command.CREATE_GROUP_REQUEST;

@Data
public class CreatGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
