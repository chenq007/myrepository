package com.example.content.serializ.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.example.content.serializ.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.content.enmus.SerializerAlgorithm.HESSIAN;

public class HessianSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return HESSIAN;
    }

    @Override
    public byte[] serialize(Object object) throws IOException {
        // 1、创建字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 2、对字节数组流进行再次封装
        Hessian2Output output = new Hessian2Output(byteArrayOutputStream);

        output.writeObject(object);

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        Hessian2Input input = new Hessian2Input(inputStream);

        return (T) input.readObject();

    }
}
