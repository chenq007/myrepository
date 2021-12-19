package com.example.specialserver.pipeline;

import com.example.specialserver.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class Inpipeline {

    static  class SimpleInhandlerA extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 ：A 被回调 ");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }

    static  class SimpleInhandlerB extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 ：B 被回调 ");
            super.channelRead(ctx, msg);
        }
    }
    static  class SimpleInhandlerC extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器 ：C 被回调 ");
            super.channelRead(ctx, msg);
        }
    }

    @Test
    public void testPipelineInBound(){
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>(){

            @Override
            protected void initChannel(EmbeddedChannel embeddedChannel) throws Exception {
                embeddedChannel.pipeline().addLast(new SimpleInhandlerA());
                embeddedChannel.pipeline().addLast(new SimpleInhandlerB());
                embeddedChannel.pipeline().addLast(new SimpleInhandlerC());
            }
        };

        EmbeddedChannel embeddedChannel=new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1111);
        embeddedChannel.writeInbound(buf);
        embeddedChannel.writeInbound(buf);
        embeddedChannel.writeInbound(buf);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e){

        }
    }

}
