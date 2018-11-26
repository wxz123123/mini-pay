package com.wxz.model.weixin.resp;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @ClassName: UnifiedOrderResp
 * @Description: 此类为微信小程序统一下单返回接收类
 * 类字段和官方文档保持一致
 * 官方文档地址：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
 * @Author: 王显政
 * @CreateDate: 2018/11/12 19:46
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
@XmlRootElement(name = "xml")
public class UnifiedOrderResp implements Serializable{
    private static final long serialVersionUID = -9026848117718458832L;
    /**
     * 返回状态码，必，SUCCESS/FAIL，长度16
     */
    private String return_code;
    /**
     * 返回信息，非必，为错误的原因，长度128
     */
    private String return_msg;

    //***协议返回的具体数据（以下字段在return_code 为SUCCESS 的时候有返回***/
    /**
     * 公众账号Id，必，长度32
     */
    private String appid;
    /**
     * 商户Id，必，长度32
     */
    private String mch_id;
    /**
     * 设备号，非必，长度32
     */
    private String device_info;
    /**
     * 随机字符串，必，长度32
     */
    private String nonce_str;
    /**
     * 签名，必，长度32
     */
    private String sign;
    /**
     * 业务结果，必，长度16
     */
    private String result_code;
    /**
     * 错误代码，非必，长度32
     */
    private String err_code;
    /**
     * 错误代码描述，非必，长度128
     */
    private String err_code_des;

    //***业务返回的具体数据（以下字段在return_code 和result_code 都为SUCCESS 的时候有返回)**//
    /**
     * 交易类型，必，长度16
     */
    private String trade_type;
    /**
     * 预支付交易会话标识，必，长度64
     */
    private String prepay_id;
    /**
     * 二维码链接地址，非必，长度64
     */
    private String code_url;
}
