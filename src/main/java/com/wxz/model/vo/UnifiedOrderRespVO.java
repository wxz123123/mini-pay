package com.wxz.model.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @ClassName: CreateMiniProgramPaymentOrderRespVO
 * @Description: 创建支付订单结果返回VO
 * @Author: 王显政
 * @CreateDate: 2018/10/24 11:55
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
public class UnifiedOrderRespVO implements Serializable {
    private static final long serialVersionUID = 7931719978298800674L;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     *随机串
     */
    private String nonceStr;
    /**
     *数据包
     */
    private String prepayId;
    /**
     *二次签名获得的签名字符串
     */
    private String paySign;
    /**
     *支付订单号
     */
    private String paymentNo;
    /**
     *业务订单号
     */
    private String tradeNo;
}
