package com.example.content.decode;

import com.example.content.data.PackerCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    public static final int LENGTN_FIELD_OFFEST = 7;
    public static final int LENGTN_FIELD_LENGTH = 4;

    public Spliter(){
        super(Integer.MAX_VALUE,LENGTN_FIELD_OFFEST,LENGTN_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != PackerCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
