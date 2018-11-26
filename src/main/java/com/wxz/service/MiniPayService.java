package com.wxz.service;

import com.wxz.model.vo.UnifiedOrderRespVO;
import com.wxz.model.vo.UnifiedorderReqVO;


/**
 * @ClassName: MiniPayService
 * @Description:
 * @Author: 王显政
 * @CreateDate: 2018/11/12 15:57
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
public interface MiniPayService {
    /**
     * @Description: 小程序支付统一下单接口
     * @Author: 王显政
     * @CreateDate: 2018/11/12 18:00
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param unifiedorderReqVO
     * @return
     */
    UnifiedOrderRespVO unifiedOrder(UnifiedorderReqVO unifiedorderReqVO) throws Exception;
    /**
     * @Description: 微信支付结果通知
     * 官方文档地址：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7
     * @Author: 王显政
     * @CreateDate: 2018/11/15 11:44
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param resultXml 微信支付通知回调xml
     * @return
     */
    String notify(String resultXml) throws Exception;
}
