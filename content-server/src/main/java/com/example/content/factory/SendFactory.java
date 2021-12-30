package com.example.content.factory;

import com.example.content.factory.impl.MailSender;
import com.example.content.factory.impl.SmsSender;

public class SendFactory {

    //produce
    public Sender produce(String type){
        if ("Mial".equals(type)){
            return new MailSender();
        }else if ("Sms".equals(type)){
            return new SmsSender();
        }
        System.out.println("请输入正确的类型！！！！");
        return null;
    }
}
