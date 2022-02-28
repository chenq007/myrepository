package com.example.content.netty;

import java.net.Socket;
import java.util.Date;

public class IOClient {
    public static void main(String[] args) {
        new Thread(() ->{
            try {
                Socket socket =new Socket("127.0.0.1",8000);
                while (true){
                    socket.getOutputStream().write((new Date() +":hello world").getBytes());
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(2000);
                }

            }catch (Exception e){

            }
        }).start();
    }
}
