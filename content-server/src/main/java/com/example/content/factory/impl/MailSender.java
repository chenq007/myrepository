package com.example.content.factory.impl;

import com.example.content.factory.Sender;

public class MailSender implements Sender {

    @Override
    public void Send() {
        System.out.println("this is MailSender !");
    }
}
