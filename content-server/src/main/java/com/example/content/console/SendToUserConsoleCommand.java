package com.example.content.console;

import com.example.content.data.MessageRequestPacket;
import com.example.content.serializ.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给个人:");
        String toUserId =scanner.next();
        String message =scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId,message));
    }
}
