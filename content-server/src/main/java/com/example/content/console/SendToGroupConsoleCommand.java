package com.example.content.console;

import com.example.content.data.SendToGroupRequestPacket;
import com.example.content.serializ.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        SendToGroupRequestPacket sendToGroupRequestPacket = new SendToGroupRequestPacket();
        System.out.print("发送消息给群聊:");
        String groupId = scanner.next();
        String message = scanner.next();
        sendToGroupRequestPacket.setGroupId(groupId);
        sendToGroupRequestPacket.setMessage(message);
        channel.writeAndFlush(sendToGroupRequestPacket);
    }
}
