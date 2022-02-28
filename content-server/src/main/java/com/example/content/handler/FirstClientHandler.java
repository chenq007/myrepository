package com.example.content.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author Administrator
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println(new Date() +"：客户端写出数据！");
//
//        //获取数据
//        ByteBuf byteBuf =getByteBuf(ctx);
//
//        //写数据
//        ctx.channel().writeAndFlush(byteBuf);

        //回复服务端
        byte[] bytes = "客户端发起连接服务端".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端读取服务端返回的消息 ->"+byteBuf.toString(Charset.forName("utf-8")));


    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();

        byte[] bytes = "hello 闪电侠".getBytes(Charset.forName("UTF-8"));
        buffer.writeBytes(bytes);

        return buffer;
    }
}
