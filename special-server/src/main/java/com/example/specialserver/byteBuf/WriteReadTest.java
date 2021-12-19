package com.example.specialserver.byteBuf;

import com.example.specialserver.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

public class WriteReadTest {

    public void ReadByeBuf( ByteBuf byteBuf){
        while (byteBuf.isReadable()){
            Logger.info("取一个字节："+byteBuf.readByte());
        }
    }

    public void getByeBuf( ByteBuf byteBuf){
        for (int i = 0; i <byteBuf.readableBytes() ; i++) {
            Logger.info("读一个字节："+byteBuf.getByte(i));
        }


    }

    @Test
    public void testWriteRead(){
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9,100);
        Logger.info("动作： 分配ByteBuf（9,100）",buffer);
            System.out.println(buffer.writableBytes());
            buffer.writeBytes(new byte[]{1,2,3,4});
            System.out.println("动作：写入4个字节");
            Logger.info("============get==========");
            getByeBuf(buffer);
            Logger.info("============read==========");
            ReadByeBuf(buffer);

    }
}
