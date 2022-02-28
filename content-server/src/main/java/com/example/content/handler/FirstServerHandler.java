package com.example.content.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = "服务端已经成功链接！".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        ctx.channel().writeAndFlush(buffer);


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端读取到客户端数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

//        //回复客户端数据
//        ByteBuf byteBuf1 = getByteBuf(ctx);
//        ctx.channel().writeAndFlush(byteBuf1);
    }




    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        byte[] bytes = "闪电侠收到了 加油 ".getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf = ctx.alloc().buffer().writeBytes(bytes);
        return byteBuf;
    }
}
