# mini-pay
### 一 小程序支付步骤（此demo只是介绍了小程序支付的后台系统部分，整个小程序支付还需要小程序端前台的配合）
    官方业务流程：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_4&index=3
    官方交互时序图：![Image text]( 这里是你的图片链接)
    
    商户系统和微信支付系统主要交互：
    
    1、小程序内调用登录接口，获取到用户的openid,api参见公共api【小程序登录API：https://developers.weixin.qq.com/miniprogram/dev/api/api-login.html】
    
    2、商户server调用支付统一下单，api参见公共api【统一下单API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1】
    
    3、商户server调用再次签名，api参见公共api【再次签名：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3】
    
    4、商户server接收支付通知，api参见公共api【支付结果通知API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_7】
    
    5、商户server查询支付结果，api参见公共api【查询订单API：https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_2】
### 二 注意点

    * 签名加密都是按照参数值不为空的字段加密，sign不参与加密。
    

