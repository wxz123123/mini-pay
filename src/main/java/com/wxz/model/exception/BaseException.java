package com.wxz.model.exception;

import lombok.Data;

/**
 * @ClassName: BaseException
 * @Description: 基础自定义异常
 * @Author: 王显政
 * @CreateDate: 2018/11/13 16:35
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 2993465624592299179L;
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
    public BaseException(int code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
