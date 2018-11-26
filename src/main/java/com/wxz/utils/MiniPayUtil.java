package com.wxz.utils;

import com.alibaba.fastjson.JSONObject;
import com.wxz.model.exception.BusinessException;
import com.wxz.model.exception.code.MiniPayResponseCode;
import com.wxz.model.weixin.req.UnifiedOrderReq;
import com.wxz.model.weixin.resp.Code2SessionResp;
import com.wxz.model.weixin.resp.UnifiedOrderResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * @ClassName: MiniPayUtil
 * @Description: 小程序支付工具类
 * @Author: 王显政
 * @CreateDate: 2018/11/12 18:11
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Component
@Slf4j
public class MiniPayUtil {
    @Autowired
    private RestTemplate restTemplate;
    /**
     *统一下单接口url
     */
    @Value("${pay.mini.unifiedorder}")
    private String unifiedorder;
    /**
     *获取openid接口url
     */
    @Value("${pay.mini.code2Session}")
    private String code2Session;
    /**
     *系统繁忙，稍候再试
     */
    private static final  Integer SYSTEM_BUSY=-1;
    /**
     *code 无效
     */
    private static final  Integer CODE_INVALID=40029;
    /**
     *频率限制，每个用户每分钟100次
     */
    private static final  Integer REQUEST_RESTRICTION=45011;
    /**
     *请求成功
     */
    private static final  Integer REQUEST_SUCCESS=0;

    /**
     * @Description: 微信统一下单接口(接口是xml格式，请求和返回都是xml)
     * @Author: 王显政
     * @CreateDate: 2018/11/12 20:16
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param unifiedOrderReq
     * @return
     */
    public UnifiedOrderResp unifiedorder(UnifiedOrderReq unifiedOrderReq) throws Exception {
        //1 将请求参数java对象转成xml
        String param=XmlUtil.javaBeanToXml(unifiedOrderReq);
        //2 调用微信统一下单接口
        ResponseEntity<String> responseEntity= restTemplate.postForEntity(unifiedorder,param,String.class);
        //3 判断接口调用是否成功
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new Exception("微信统一下单接口调用异常");
        }
        //4 取出结果
        String response=responseEntity.getBody();
        UnifiedOrderResp unifiedOrderResp=XmlUtil.xmlToJavaBean(response,UnifiedOrderResp.class);
        // 5 返回结果
        return  unifiedOrderResp;
    }
    /**
     * @Description: 获取openid，腾讯接口说明文档地址https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
     * @Author: 王显政
     * @CreateDate: 2018/11/12 10:01
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param code 临时登录凭证code
     * @return
     */
    public Code2SessionResp getOpenId(String appid,String appsecret,String code) {
        /**
         * 1 封装接口调用参数
         * 获取openid接口
         * GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
         * 参数
         *string appid
         *小程序 appId
         *
         *string secret
         *小程序 appSecret
         *
         *string js_code
         *登录时获取的 code
         *
         *string grant_type
         *授权类型，此处只需填写 authorization_code
         */
        String getOpenIdUrl= MessageFormat.format(code2Session,appid,appsecret,code);
        log.info("----code2Session rul---:"+getOpenIdUrl);
        //2 调用微信小程序code2Session接口
        ResponseEntity<String> responseEntity =restTemplate.getForEntity(getOpenIdUrl,String.class);
        log.info("----code2Session result---:"+responseEntity);
        //ResponseEntity<Code2SessionResp> responseEntity =restTemplate.getForEntity(getOpenIdUrl,Code2SessionResp.class);
        //3 判断是否正常调取
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException(MiniPayResponseCode.ERROR_1000);
        }
        //4 取出调取内容
        String body=responseEntity.getBody();
        log.info("----code2Session result body---:"+body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        Code2SessionResp code2SessionResp=JSONObject.toJavaObject(jsonObject,Code2SessionResp.class);
        //5 判断是否请求成功
        if(null == code2SessionResp){
            throw new BusinessException(MiniPayResponseCode.ERROR_1000);
        }
        //如果错误码有值表示请求错误，解析错误码
        if(null != code2SessionResp.getErrcode()){
            log.info("---code2Session result errmsg---:"+code2SessionResp.getErrMsg());
            /**
             * 错误码
             * -1	系统繁忙，此时请开发者稍候再试
             * 0	请求成功
             * 40029	code 无效
             * 45011	频率限制，每个用户每分钟100次
             */
            if(SYSTEM_BUSY.equals(code2SessionResp.getErrcode())){
                throw new BusinessException(MiniPayResponseCode.ERROR_1001);
            }
            if(CODE_INVALID.equals(code2SessionResp.getErrcode())){
                throw new BusinessException(MiniPayResponseCode.ERROR_1002);
            }
            if(REQUEST_RESTRICTION.equals(code2SessionResp.getErrcode())){
                throw new BusinessException(MiniPayResponseCode.ERROR_1003);
            }
            //如果不是上面那几种已知错误码的错误，就返回获取用户唯一标识失败
            if(!REQUEST_SUCCESS.equals(code2SessionResp.getErrcode())){
                throw new BusinessException(MiniPayResponseCode.ERROR_1000);
            }
        }
        //判断openid是否为空
        if(null == code2SessionResp.getOpenid() || org.springframework.util.StringUtils.isEmpty(code2SessionResp.getOpenid())){
            throw new BusinessException(MiniPayResponseCode.ERROR_1000);
        }
        return code2SessionResp;
    }
}
