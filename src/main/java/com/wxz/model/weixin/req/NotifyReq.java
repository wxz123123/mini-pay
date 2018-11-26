package com.wxz.model.weixin.req;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: NotifyReq
 * @Description: 接受微信支付通知回调请求封装类，由微信的xml格转换成javaBean
 * 微信官方文档地址：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7
 * @Author: 王显政
 * @CreateDate: 2018/11/15 16:12
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
@XmlRootElement(name = "xml")
public class NotifyReq {
    //协议层
    private String return_code;
    private String return_msg;

    //以下字段在return_code为SUCCESS的时候有返回
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String openid;
    private String is_subscribe;
    private String trade_type;
    private String bank_type;
    private String total_fee;
    private String settlement_total_fee;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type;
    private String coupon_fee;
    private String coupon_type_$n;
    private String coupon_id_$n;
    private String coupon_fee_$n;
    private String transaction_id;
    private String out_trade_no;
    private String attach;
    private String time_end;

}
