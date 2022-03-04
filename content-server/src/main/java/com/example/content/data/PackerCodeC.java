package com.example.content.data;

import com.example.content.serializ.Serializer;
import com.example.content.serializ.impl.HessianSerializer;
import com.example.content.serializ.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.content.enmus.Command.*;

public class PackerCodeC {
    //将该类实现成单列模式

    private static class Singleton{
        private static  final PackerCodeC INSTANCE = new PackerCodeC();
    }
    private PackerCodeC(){

    }

    public static final PackerCodeC getInstance(){
        return Singleton.INSTANCE;
    }



    public static final int MAGIC_NUMBER = 0x12345678;
    
    private static final Map<Byte,Class<? extends Packet>> packetTypeMap ;
    public static final Map<Byte,Serializer> serializerMap ;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST,LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE,LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST,MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE,MessageResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST,CreatGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE,CreatGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST,JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE,JoinGroupResponsetPacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST,QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE,QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST,ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE,ListGroupMembersResponsePacket.class);
        packetTypeMap.put(SEND_TO_GROUP_REQUEST,SendToGroupRequestPacket.class);
        packetTypeMap.put(SEND_TO_GROUP_RESPONSE,SendToGroupResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(),serializer);


    }

    public ByteBuf encode(ByteBuf byteBuf , Packet packet) throws IOException {
        //序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) throws IOException {
        //跳过 magic number
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //序列化标识
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType,bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm){
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
