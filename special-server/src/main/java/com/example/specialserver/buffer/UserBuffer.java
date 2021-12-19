package com.example.specialserver.buffer;

import java.nio.IntBuffer;

public class UserBuffer {

    static IntBuffer intBuffer=null;

    public  static  void allocatest(){
        intBuffer=IntBuffer.allocate(20);
        for (int i = 0; i <5 ; i++) {
            intBuffer.put(i);
        }
        System.out.println("before filp()");
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
        System.out.println(intBuffer.capacity());

        intBuffer.flip();
        System.out.println("after  filp()");
        System.out.println(intBuffer.position());
        System.out.println(intBuffer.limit());
        System.out.println(intBuffer.capacity());
    }

    public static void main(String[] args) {
        allocatest();
    }
}
