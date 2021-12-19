package com.example.specialserver.pipeline;

import com.example.specialserver.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class PipelineHotOperateTester {

    static class  SimpleInHandlerA1 extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器A： 被调用");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }
    static class  SimpleInHandlerB2 extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器B： 被调用1111");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }
    static class  SimpleInHandlerC3 extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("入站处理器C： 被调用111");
            super.channelRead(ctx, msg);
            ctx.pipeline().remove(this);
        }
    }
    @Test
    public void testPipelineHotOperating(){
        ChannelInitializer i=new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new SimpleInHandlerA1());
                channel.pipeline().addLast(new SimpleInHandlerB2());
                channel.pipeline().addLast(new SimpleInHandlerC3());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1111);
        channel.writeInbound(buf);

        channel.writeInbound(buf);

        channel.writeInbound(buf);


        EmbeddedChannel embeddedChannel=new EmbeddedChannel(i);
        ByteBuf buf1 = Unpooled.buffer();
        buf.writeInt(1111);
        embeddedChannel.writeInbound(buf1);
        embeddedChannel.writeInbound(buf1);
        embeddedChannel.writeInbound(buf1);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        }catch (Exception e){

        }
    }
}
