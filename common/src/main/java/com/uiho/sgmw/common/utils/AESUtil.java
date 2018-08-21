package com.uiho.sgmw.common.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：uiho_mac
 * 时间：2018/5/16
 * 描述：aes 加密解密工具类
 * 版本：1.0
 * 修订历史：
 */

public class AESUtil {
    private static final String CipherMode = "AES/ECB/PKCS5Padding";//使用ECB加密，不需要设置IV

    /**
     * 对字符串加密
     *
     * @param key  密钥
     * @param data 源字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keyspec = new SecretKeySpec(key.substring(0, 16).getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串解密
     *
     * @param key  密钥
     * @param data 已被加密的字符串
     * @return 解密得到的字符串
     */
    public static String decrypt(String data, String key) {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keyspec = new SecretKeySpec(key.substring(0, 16).getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(
                    new byte[cipher.getBlockSize()]);
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
