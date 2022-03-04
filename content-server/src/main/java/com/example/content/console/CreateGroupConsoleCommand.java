package com.example.content.console;

import com.example.content.data.CreatGroupRequestPacket;
import com.example.content.serializ.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    public static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreatGroupRequestPacket creatGroupRequestPacket = new CreatGroupRequestPacket();

        System.out.print("【拉人群聊】输入userId列表,userId用逗号隔开:");
        String userIds = scanner.next();
        creatGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(creatGroupRequestPacket);
    }
}
