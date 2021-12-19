package com.example.specialserver.pipeline;

import com.example.specialserver.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class OutPipeline {
    static  class  SimpleOutHandlerA extends ChannelOutboundHandlerAdapter{
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("出站处理器A 被调用");
            super.write(ctx, msg, promise);
        }
    }
    static  class  SimpleOutHandlerB extends ChannelOutboundHandlerAdapter{
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("出站处理器B 被调用");
//            super.write(ctx, msg, promise);
        }
    }
    static  class  SimpleOutHandlerC extends ChannelOutboundHandlerAdapter{
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("出站处理器C 被调用");
            super.write(ctx, msg, promise);
//            ctx.fire
        }
    }

    @Test
    public  void  testPipelineOutBound(){
        ChannelInitializer i =new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
                channel.pipeline().addLast(new SimpleOutHandlerA());
                channel.pipeline().addLast(new SimpleOutHandlerB());
                channel.pipeline().addLast(new SimpleOutHandlerC());
            }
        };

        EmbeddedChannel channel=new EmbeddedChannel(i);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1111);
        channel.writeOutbound(buf);

    }
}
