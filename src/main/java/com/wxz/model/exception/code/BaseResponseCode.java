package com.wxz.model.exception.code;

/**
 * @ClassName: BaseResponseCode
 * @Description: 错误码接口
 * @Author: 王显政
 * @CreateDate: 2018/11/13 17:15
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
public interface BaseResponseCode {
    int getCode();
    String getMsg();
}
