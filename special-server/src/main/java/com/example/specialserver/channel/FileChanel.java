package com.example.specialserver.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChanel {


    public static  void test(){
        File srcFile=new File("D:/1.txt");
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            FileChannel inChannel= fis.getChannel();

            ByteBuffer byteBuffer=ByteBuffer.allocate(20);
            int lenth= -1;
            while ((lenth=inChannel.read(byteBuffer)) != -1){
                System.out.println(byteBuffer.position());
            }

            File outFile=new File("D:/3.txt");
            FileOutputStream fos=new FileOutputStream(outFile);
            FileChannel outChannel= fos.getChannel();
            byteBuffer.flip();
            int outLength=0;
            while ((outLength = outChannel.write(byteBuffer)) != 0){
                System.out.println("写入的字节数:"+outLength);
            }
            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }
}
