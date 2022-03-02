package com.example.content.decode;

import com.example.content.data.PackerCodeC;
import com.example.content.data.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PackerCodeC.getInstance().encode(byteBuf,packet);
    }
}
