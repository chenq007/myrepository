package com.example.specialserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.nio.ByteBuffer;

public class InHanderlifeCircle {

    @Test
    public void testInHanderlifeCircle(){
        final InHanderDemo inHanderDemo = new InHanderDemo();
        //初始化处理器
        ChannelInitializer channelInitializer=new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHanderDemo);
                }
        };
        EmbeddedChannel channel=new EmbeddedChannel(channelInitializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);
        channel.flush();

        channel.writeInbound(buf);
        channel.flush();

        channel.close();

        try {
           Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
