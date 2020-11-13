package com.ponking.pblog.common.util;

import com.ponking.pblog.common.exception.GlobalException;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * （反）序列工具
 * @author peng
 * @date 2020/11/12--16:57
 * @Des
 **/
public class SerializableUtil {


    private static final Logger logger = LoggerFactory.getLogger(SerializableUtil.class);

    private SerializableUtil() {

    }

    /**
     * 序列化
     *
     * @param object
     * @return byte[]
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new GlobalException("SerializableUtil工具类序列化出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
            }
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return java.lang.Object
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("SerializableUtil工具类反序列化出现ClassNotFoundException异常:" + e.getMessage());
        } catch (IOException e) {
            throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
            }
        }
    }
}