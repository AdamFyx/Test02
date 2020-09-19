package com.adam.collection.test.locationUtils;

/**
 * @author xys
 * @date 2019/8/26 0026.
 */

import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密方法，是对称的密码算法(加密与解密的密钥一致)，这里使用128位(16字节)的密钥
 */
public class AESUtil {

    private static String SECRET_KEY="gamesSportci1234";

    public static String encrypt(String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(SECRET_KEY, cleartext);
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleartext;
    }
    public static String decrypt(String content) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        try {
            byte[] result = decrypt(Base64.decode(content, Base64.DEFAULT), SECRET_KEY);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private static byte[] encrypt(String password, String clear) throws Exception {
        // 创建AES秘钥
        SecretKeySpec secretKeySpec = getKey(password);
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 加密
        return cipher.doFinal(clear.getBytes("UTF-8"));
    }

    private static byte[] decrypt(byte[] content, String password) throws Exception {
        // 创建AES秘钥
        SecretKeySpec key = getKey(password);
        // 创建密码器
        Cipher cipher = Cipher.getInstance("AES");
        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 解密
        return cipher.doFinal(content);
    }

    private static SecretKeySpec getKey(String password){
        return new SecretKeySpec(password.getBytes(), "AES/ECB/PKCS5PADDING");
    }
}
