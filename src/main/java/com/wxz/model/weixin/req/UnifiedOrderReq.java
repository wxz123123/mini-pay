package com.wxz.model.weixin.req;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @ClassName: UnifiedOrderReq
 * @Description: 统一下单请求参数封装类
 * 这个类要转成xml格式的数据
 * 这个类包含所有必须和非必需字段，字段名称严格按照微信官方文档保持一致,非必须字段可以根据自己的情况决定要不传
 * 微信小程序下单官方文档详见：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
 * @Author: 王显政
 * @CreateDate: 2018/11/12 19:30
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
public class UnifiedOrderReq implements Serializable{
    private static final long serialVersionUID = -5732396038708247371L;
    /**
     * 公众号id，必填，限制长度32
     */
    private String appid;
    /**
     * 商户号，必填，限制长度32
     */
    private String mch_id;
    /**
     * 设备号，非必填，限制长度32,PC网页或公众号内支付请传"WEB"
     */
    private String device_info;
    /**
     * 随机字符串,必填，限制长度32
     */
    private String nonce_str;
    /**
     * 签名，必填，限制长度32
     */
    private String sign;
    /**
     * 签名类型，非必填，限制长度32
     */
    private String sign_type;
    /**
     * 商品描述，必填，限制长度128
     */
    private String body;
    /**
     *  商品详情，非必填，限制长度6000
     */
    private String detail;
    /**
     * 附加数据，非必填，限制长度127
     */
    private String attach;
    /**
     * 商户订单号，必填，限制长度32
     */
    private String out_trade_no;
    /**
     * 标价币种，非必填，限制长度16，符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String fee_type;
    /**
     * 标价金额,必填，限制长度88，单位为分，无小数点
     */
    private Integer total_fee;
    /**
     * 终端ip，必填，限制长度16，标准备外部ip格式
     */
    private String spbill_create_ip;
    /**
     * 交易起始时间，非必填，限制14位，格式为yyyyMMddHHmmss
     */
    private String time_start;
    /**
     * 交易结束时间,非必填，限制长度14，格式为yyyyMMddHHmmss
     */
    private String time_expire;
    /**
     * 订单优惠标记，非必填，限制长度32
     */
    private String goods_tag;
    /**
     * 通知地址(支付成功回调)，必须为外网微信可访问的Url，必填，限制长度256
     */
    private String notify_url;
    /**
     * 交易类型，必填，限制长度16，ef:JSAPI，NATIVE，APP,etc...
     */
    private String trade_type;
    /**
     * 商品id，非必填，限制长度32
     */
    private String product_id;
    /**
     * 指定支付方式，非必填，限制长度32
     */
    private String limit_pay;
    /**
     * 用户标识符，非必填，限制长度128
     */
    private String openid;
}
