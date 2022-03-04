package com.example.content.console;

import com.example.content.data.JoinGroupRequestPacket;
import com.example.content.data.QuitGroupRequestPacket;
import com.example.content.serializ.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket quitGroupRequestPacket =new QuitGroupRequestPacket();

        System.out.print("输入要退出群聊 groupId :");
        String groupId = scanner.next();
        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
