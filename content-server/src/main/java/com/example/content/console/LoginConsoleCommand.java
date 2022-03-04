package com.example.content.console;

import com.example.content.data.LoginRequestPacket;
import com.example.content.serializ.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("输入用户名登陆:");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        String userName = scanner.nextLine();
        loginRequestPacket.setUserName(userName);
        loginRequestPacket.setPassWord("pwd123");
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse(){
        try {
            Thread.sleep(1000);
        }catch (Exception ingrod){

        }
    }
}
