package com.wxz.model.weixin.resp;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @ClassName: NotifyResp
 * @Description:成功接收到微信支付回调后返回
 * @Author: 王显政
 * @CreateDate: 2018/11/19 20:05
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Data
@XmlRootElement(name = "xml")
public class NotifyResp implements Serializable{
    /**
     * SUCCESS/FAIL
     * SUCCESS表示商户接收通知成功并校验成功
     */
    private String return_code;
    /**
     * 返回信息，如非空，为错误原因：
     *签名失败
     *参数格式校验错误
     */
    private String return_msg;
}
