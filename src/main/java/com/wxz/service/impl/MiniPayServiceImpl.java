package com.wxz.service.impl;

import com.wxz.model.vo.UnifiedOrderRespVO;
import com.wxz.model.vo.UnifiedorderReqVO;
import com.wxz.model.weixin.req.NotifyReq;
import com.wxz.model.weixin.req.UnifiedOrderReq;
import com.wxz.model.weixin.resp.Code2SessionResp;
import com.wxz.model.weixin.resp.NotifyResp;
import com.wxz.model.weixin.resp.UnifiedOrderResp;
import com.wxz.service.MiniPayService;
import com.wxz.utils.MiniPayUtil;
import com.wxz.utils.PaySignUtil;
import com.wxz.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * @ClassName: MiniPayServiceImpl
 * @Description:
 * @Author: 王显政
 * @CreateDate: 2018/11/12 15:58
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Slf4j
@Service
public class MiniPayServiceImpl implements MiniPayService {
    @Autowired
    private MiniPayUtil miniPayUtil;
    /**
     * 小程序ID
     */
    @Value("${pay.mini.appid}")
    private String appid;
    /**
     *小程序密钥
     */
    @Value("${pay.mini.appsecret}")
    private String appsecret;
    /**
     *商户号
     */
    @Value("${pay.weixinpay.mchid}")
    private String mchid;

    /**
     *支付密钥
     */
    @Value("${pay.weixinpay.apisign}")
    private String apisign;
    /**
     *支付结果通知地址
     */
    @Value("${pay.mini.notifyrl}")
    private String notifyUrl;

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
    @Override
    public UnifiedOrderRespVO unifiedOrder(UnifiedorderReqVO unifiedorderReqVO) throws Exception {
        //1 核对订单金额是否正确，由于此demo之模拟支付，其他操作暂时不加上

        //2 生成支付订单号（32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一）
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateTimeString=sdf.format(new Date());
        //定义变长字符串
        StringBuilder str=new StringBuilder();
        Random random=new Random();
        //随机生成数字，并添加到字符串
        for(int i=0;i<8;i++){
            str.append(random.nextInt(10));
        }
        //支付订单号格式：自定义名称-年月日时分秒-8位随机
        String payOrderNo="XZ-"+dateTimeString+"-"+str;

        //3 根据临时登录凭证获取openid。
        Code2SessionResp code2SessionResp=miniPayUtil.getOpenId(appid,appsecret,unifiedorderReqVO.getCode());
        //4 微信创建支付订单
        UnifiedOrderReq unifiedOrderReq=new UnifiedOrderReq();

        unifiedOrderReq.setOpenid(code2SessionResp.getOpenid());
        unifiedOrderReq.setAppid(appid);
        unifiedOrderReq.setMch_id(mchid);
        //随机字符串，长度要求在32位以内
        unifiedOrderReq.setNonce_str(UUID.randomUUID().toString());
        unifiedOrderReq.setBody(unifiedorderReqVO.getBody());
        unifiedOrderReq.setOut_trade_no(payOrderNo);
        unifiedOrderReq.setTotal_fee(unifiedorderReqVO.getTotalFee());
        unifiedOrderReq.setSpbill_create_ip("123.12.12.123");
        unifiedOrderReq.setTrade_type("JSAPI");
        unifiedOrderReq.setNotify_url(notifyUrl);
        //将请求参数和支付密锁一起MD5加密成签名
        unifiedOrderReq.setSign(PaySignUtil.createSign(unifiedOrderReq,apisign));
        //微信支付下单
        UnifiedOrderResp unifiedOrderResp=miniPayUtil.unifiedorder(unifiedOrderReq);
        //5 将下单返回字段进行二次签名得到paySign
        long currentTime= System.currentTimeMillis();
        String timeStamp= String.valueOf(currentTime);
        String nonceStr=unifiedOrderResp.getNonce_str();
        String prepayId=unifiedOrderResp.getPrepay_id();
        String paySign=PaySignUtil.getPaySign(appid,appsecret,timeStamp,nonceStr,prepayId);
        //6 记录交易信息,发票信息等，由于此demo之模拟支付，其他操作暂时不加上

        //7 封装数据并返回给小程序
        UnifiedOrderRespVO unifiedOrderRespVo=new UnifiedOrderRespVO();
        unifiedOrderRespVo.setPaymentNo(payOrderNo);
        unifiedOrderRespVo.setTradeNo(unifiedorderReqVO.getOderNo());
        unifiedOrderRespVo.setPaySign(paySign);
        unifiedOrderRespVo.setPrepayId(prepayId);
        unifiedOrderRespVo.setTimeStamp(timeStamp);
        unifiedOrderRespVo.setNonceStr(nonceStr);
        return unifiedOrderRespVo;
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
    @Override
    public String notify(String resultXml) throws Exception {
        NotifyReq notifyReq= XmlUtil.xmlToJavaBean(resultXml,NotifyReq.class);
        //return_code  SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        NotifyResp notifyResp=new NotifyResp();
        if("SUCCESS".equals(notifyReq.getReturn_code())){
            if("SUCCESS".equals(notifyReq.getResult_code())){
                //校验签名是否正确，防止签名被串改
                String sign=PaySignUtil.createSign(notifyReq,apisign);
                if(!sign.equals(notifyReq.getSign())){
                    throw new Exception("签名可能已被串改");
                }
                // 修改订单订单状态为支付成功，

                // 记录支付接受通知为已接受通知。避免在次接受通知

                //返回通知结果给微信，这样微信就不会再次通知
                /**
                 *
                 * SUCCESS/FAIL
                 * SUCCESS表示商户接收通知成功并校验成功
                 */
                notifyResp.setReturn_code("SUCCESS");
                /**
                 * 返回信息，如非空，为错误原因：
                 * 签名失败
                 * 参数格式校验错误
                 */
                notifyResp.setReturn_msg("OK");

            }else{
                log.info("微信支付交易失败："+notifyReq.getErr_code_des());
            }
        }else{
            log.info("微信支付结果通知失败："+notifyReq.getReturn_msg());
        }
        return XmlUtil.javaBeanToXml(notifyResp);
    }
}
