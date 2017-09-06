package com.laonie.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-08-08 10:11
 * @DESCRIPTION:
 *          MD5加密帮助类
 */

public class MD5Util {
    private static final String PREFIX_CODE = "^&^$%^$^FTFY233fs";

    /**
     * 商户登录密码、支付密码 加密方式 md5(PREFIX_CODE+密码)
     * @param s
     * @return
     */
    public static String generatedStorePass(String s) {
        String secret = PREFIX_CODE + s;
        return MD5(secret);
    }

    public static String MD5(String s) {
        if (StringUtils.isEmpty(s)) return "";
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(s.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String str = "cunpiao123";
        System.out.println("8205232cbac50af8487e1d15973ff3b9-----" + generatedStorePass(str));
    }
}
