package com.wxz.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * @ClassName: MD5Util
 * @Description: md5加密工具类
 * @Author: 王显政
 * @CreateDate: 2018/11/13 19:37
 * @UpdateUser:
 * @UpdateDate:
 * @Version: 0.0.1
 */
@Slf4j
public class MD5Util {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};


    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }


    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
        } catch (Exception e) {
            log.error("",e);
        }
        return resultString;
    }

    public static String sign(String text, String key) {
        text = text + key;
        return MD5Encode(text);
    }

    public static boolean verify(String text, String sign, String key) {
        String mysign = sign(text, key);
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param plainText 需要加密的字符串
     * @return
     * @Description 字符串加密为MD5
     * 中文加密一致通用,必须转码处理：
     * plainText.getBytes("UTF-8")
     */
    public static String toMD5(String plainText) {
        StringBuffer rlt = new StringBuffer();
        try {
            rlt.append(md5String(plainText.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            log.error(" MD5加密出错:", e.toString());
        }
        return rlt.toString();
    }

    /**
     * md5加密字节数组
     *
     * @param data
     * @return
     */
    public static String md5String(byte[] data) {
        String md5Str = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5Str = "";
            byte[] buf = md5.digest(data);
            for (int i = 0; i < buf.length; i++) {
                md5Str += byte2Hex(buf[i]);
            }
        } catch (Exception e) {
            md5Str = null;
            e.printStackTrace(System.err);
        }
        return md5Str;
    }

    /**
     * 把字节变为Hash
     *
     * @param b
     * @return
     */
    public static String byte2Hex(byte b) {
        String hex = Integer.toHexString(b);
        if (hex.length() > 2) {
            hex = hex.substring(hex.length() - 2);
        }
        while (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }
}
