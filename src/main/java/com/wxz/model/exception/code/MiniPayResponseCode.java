package com.wxz.model.exception.code;

/**
 * @ClassName: MiniPayResponseCode
 * @Description:
 * @Author: 王显政
 * @CreateDate: 2018/11/13 11:52
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
public enum MiniPayResponseCode implements BaseResponseCode{
    ERROR_1000(7009, "获取用户唯一标识失败"),
    ERROR_1001(-1, "微信服务器系统繁忙,请稍后再试。"),
    ERROR_1002(40029, "code 无效。"),
    ERROR_1003(45011, "操作过于频繁，请稍后再试。"),
    ;
    /**
     * 错误码
     */
    private final int code;
    /**
     * 错误消息(中文)
     */
    private final String msg;
    /**
     * 错误消息（英文）
     */
    private final String enMsg;


    MiniPayResponseCode(int code,String msg){
        this.code=code;
        this.msg=msg;
        this.enMsg="";
    }
    MiniPayResponseCode(int code,String msg,String enMsg){
        this.code=code;
        this.msg=msg;
        this.enMsg=enMsg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
