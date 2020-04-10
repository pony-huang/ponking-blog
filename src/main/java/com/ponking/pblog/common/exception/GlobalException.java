package com.ponking.pblog.common.exception;

/**
 * @author Ponking
 * @ClassName GlobalException
 * @date 2020/3/15--11:21
 * @Des 全局异常
 **/
public class GlobalException extends RuntimeException {


    public GlobalException(String message){
        super(message);
    }

    public static void main(String[] args) {
        throw new GlobalException("测试异常");
    }
}
