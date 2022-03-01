package com.example.content.serializ;

import com.example.content.serializ.impl.HessianSerializer;
import com.example.content.serializ.impl.JSONSerializer;

import java.io.IOException;

public interface Serializer {

    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    Serializer Hessian = new HessianSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转成二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object) throws IOException;

    /**
     * 二进制转成java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz ,byte[] bytes) throws IOException;
}
