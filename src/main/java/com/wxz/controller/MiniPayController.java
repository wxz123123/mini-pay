package com.wxz.controller;

import com.wxz.model.vo.UnifiedOrderRespVO;
import com.wxz.model.vo.UnifiedorderReqVO;
import com.wxz.service.MiniPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName: MiniPayController
 * @Description:
 * @Author: 王显政
 * @CreateDate: 2018/11/12 15:58
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/miniPay")
public class MiniPayController {
    @Autowired
    private MiniPayService miniPayService;
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
    @PostMapping("/unifiedOrder")
    public UnifiedOrderRespVO unifiedOrder(@Valid @RequestBody UnifiedorderReqVO unifiedorderReqVO) throws Exception {
        return  miniPayService.unifiedOrder(unifiedorderReqVO);
    }
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
    @PostMapping(value = "/notify" ,consumes = "text/xml",produces = "application/json")
    public String notify(@RequestBody String resultXml) throws Exception {
        return miniPayService.notify(resultXml);
    }

}
