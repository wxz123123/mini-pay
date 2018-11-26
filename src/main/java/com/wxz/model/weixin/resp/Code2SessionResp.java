package com.wxz.model.weixin.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Code2SessionResp
 * @Description: code2Session接口返回封装类（该接口用于获取openid）
 * @Author: 王显政
 * @CreateDate: 2018/11/12 10:23
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
@JsonFormat
public class Code2SessionResp implements Serializable{
    private static final long serialVersionUID = 8107128141563158301L;
    /**
     *用户唯一标识
     */
    private String openid;

    /**
     *会话密钥
     */
    private String session_key;

    /**
     *用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回,详见https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html
     */
    private String unionid;


    /**
     * 错误码
     * -1	系统繁忙，此时请开发者稍候再试
     * 0	请求成功
     * 40029	code 无效
     * 45011	频率限制，每个用户每分钟100次
     */
    private Integer errcode;

    /**
     *错误信息
     */
    private String errMsg;
}
