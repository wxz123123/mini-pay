package com.wxz.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: UnifiedorderReqVO
 * @Description: 统一下单请求VO(用于接受小程序传过来的付款参数)
 * @Author: 王显政
 * @CreateDate: 2018/11/12 16:13
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
public class UnifiedorderReqVO implements Serializable{
    private static final long serialVersionUID = 6856941594497350826L;
    /**
     * 商品简单描述
     */
    @NotBlank(message = "商品描述不能为空")
    private String body;
    /**
     * 商户系统内部订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String oderNo;
    /**
     *订单金额,单位为分
     */
    @NotNull(message = "订单金额不能为空")
    private Integer totalFee;
    /**
     * 商户系统内部账户ID
     */
    @NotBlank(message = "账号ID不能为空")
    private String accountId ;
    /**
     *临时登录凭证
     */
    @NotBlank(message = "临时登录凭证不能为空")
    private String code ;
}
