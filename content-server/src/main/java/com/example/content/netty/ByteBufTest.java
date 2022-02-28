package com.example.content.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9,100);

       print("allocate ByteBuf(9,100)",buffer);


       buffer.writeBytes(new byte[]{1,2,3,4});
       print("writeBytes(1,2,3,4)",buffer);


       buffer.writeInt(12);
       print("writeINT(12)",buffer);

       buffer.writeBytes(new byte[]{5});
       print("writeBytes(5)",buffer);

       buffer.writeBytes(new byte[]{6});
       print("writeBytes(6)",buffer);


        System.out.println("getByte(3): " +buffer.getByte(3));
        System.out.println("getShort(3):"+buffer.getShort(3));
        System.out.println("getInt(3): "+ buffer.getInt(3));

        buffer.setByte(buffer.readableBytes()+1,0);
        print("setByte()",buffer);

        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes("+dst.length +") :",buffer);





    }


    private static void print(String action, ByteBuf buffer){
        System.out.println("after ========="+ action + "=========");
        System.out.println("capacity(): "+ buffer.capacity());
        System.out.println("maxCapacity(): "+ buffer.maxCapacity());
        System.out.println("readerIndex(): "+ buffer.readerIndex());
        System.out.println("readableBytes(): "+ buffer.readableBytes());
        System.out.println("isReadable(): "+ buffer.isReadable());
        System.out.println("writerIndex(): " +buffer.writerIndex());
        System.out.println("writableBytes(): "+ buffer.writableBytes());
        System.out.println("isWritable(): "+ buffer.isWritable());
        System.out.println("maxWritableBytes():"+ buffer.maxWritableBytes());
        System.out.println();
    }
}
