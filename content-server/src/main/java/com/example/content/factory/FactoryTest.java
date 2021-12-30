package com.example.content.factory;

public class FactoryTest {

    public static void main(String[] args) {
        SendFactory sendFactory = new SendFactory();
        Sender sender = sendFactory.produce("Sms");
        sender.Send();
    }
}
