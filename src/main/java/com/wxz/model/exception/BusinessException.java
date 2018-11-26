package com.wxz.model.exception;

import com.wxz.model.exception.code.BaseResponseCode;

/**
 * @ClassName: BusinessException
 * @Description:
 * @Author: 王显政
 * @CreateDate: 2018/11/13 16:35
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
public class BusinessException extends BaseException{

    public BusinessException(int code, String message) {
        super(code, message);
    }
    public BusinessException(BaseResponseCode code){
        super(code.getCode(),code.getMsg());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
