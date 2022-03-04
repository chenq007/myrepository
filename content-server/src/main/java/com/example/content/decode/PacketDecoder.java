package com.example.content.decode;

import com.example.content.data.PackerCodeC;
import com.example.content.data.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class PacketDecoder extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketDecoder INSTANCE = new PacketDecoder();

    private PacketDecoder() {

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        ByteBuf byteBuf=channelHandlerContext.alloc().ioBuffer();
          PackerCodeC.getInstance().encode(byteBuf, packet);
          list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PackerCodeC.getInstance().decode(byteBuf));
    }
}
