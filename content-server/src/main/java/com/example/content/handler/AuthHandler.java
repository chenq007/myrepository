package com.example.content.handler;

import com.example.content.netty.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ( !LoginUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())) {
            System.out.println("大部分全当前连接登陆验证完毕，无需在验证，AuthHandler被移除。");
        } else {
            System.out.println("无登录验证，强制关闭连接");
        }
    }
}
