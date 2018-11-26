package com.wxz.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: SignUtil
 * @Description: 支付签名工具类
 * @Author: 王显政
 * @CreateDate: 2018/11/13 18:11
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Slf4j
public class PaySignUtil {
    /**
     * @Description:
     * @Author: 王显政
     * @CreateDate: 2018/11/15 11:23
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param obj 请求的参数对象
     * @param privateKey 支付密钥
     * @return
     */
    public static String createSign(Object obj, String privateKey) throws Exception {
        Map<String, String> params=new HashMap<>();

        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            params.put(field.getName(), (String) field.get(obj));
        }
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<String, String>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value =  entry.getValue().trim();
            //sign不参与加密
            if(null != value && !StringUtils.isEmpty(value) && !key.equals("sign")){
                    sb.append("&").append(key).append("=").append(value);
            }
        }
        String stringA = sb.toString().replaceFirst("&","");
        String stringSignTemp = stringA + privateKey;
        String signValue = MD5Util.MD5Encode(stringSignTemp).toUpperCase();
        return signValue;
    }
    /**
     * @Description: 小程序支付下单成功返回后进行二次签名paySign
     * @Author: 王显政
     * @CreateDate: 2018/11/15 11:22
     * @UpdateUser:
     * @UpdateDate:
     * @Version: 0.0.1
     * @param appid 小程序appid
     * @param key 商户号密钥
     * @param timeStamp 时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
     * @param nonceStr 随机字符串，长度为32个字符以下。
     * @param prepayId 统一下单接口返回的 prepay_id 参数值
     * @return
     */
    public static String getPaySign(String appid,String key,String timeStamp,String nonceStr,String prepayId){
        /**
         * 官方举例https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3
         * 举例如下:
         *paySign = MD5(appId=wxd678efh567hg6787&nonceStr=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&package=prepay_id=wx2017033010242291fcfe0db70013231072&signType=MD5&timeStamp=1490840662&key=qazwsxedcrfvtgbyhnujmikolp111111) = 22D9B4E54AB1950F51E0649E8810ACD6
         */
        StringBuilder sb = new StringBuilder();
        sb.append("appId="+appid);
        sb.append("&nonceStr="+nonceStr);
        sb.append("&package=prepay_id="+prepayId);
        sb.append("&signType=MD5");
        sb.append("&timeStamp="+timeStamp);
        sb.append("&key="+key);
        log.info("paySign="+sb.toString());
        String paySign = MD5Util.MD5Encode(sb.toString()).toUpperCase();
        return paySign;
    }
}
