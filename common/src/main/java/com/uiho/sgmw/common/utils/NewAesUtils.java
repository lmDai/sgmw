package com.uiho.sgmw.common.utils;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者：uiho_mac
 * 时间：2018/7/18
 * 描述：
 * 版本：1.0
 * 修订历史：
 */
public class NewAesUtils {
    public static String encrypt(String content, String authPWD) {
        try {
            if (StringUtils.isEmpty(content))
                return "";
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(authPWD.getBytes());
//            kgen.init(128, random);
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
            byte[] enCodeFormat = Base64.getDecoder().decode("/JpCtKDmFMW1TpiD/MF4Bg==");
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            String str = Base64.getEncoder().encodeToString(result);
            return str;
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // e.printStackTrace();
        } catch (InvalidKeyException e) {
            // e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // e.printStackTrace();
        } catch (BadPaddingException e) {
            // e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String str, String authPWD) {
        try {
            byte[] content = Base64.getDecoder().decode(str);
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//            secureRandom.setSeed(authPWD.getBytes());
//            kgen.init(128, secureRandom);
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
            byte[] enCodeFormat = Base64.getDecoder().decode("/JpCtKDmFMW1TpiD/MF4Bg==");
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return new String(result, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            // e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // e.printStackTrace();
        } catch (InvalidKeyException e) {
            // e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // e.printStackTrace();
        } catch (BadPaddingException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) {
        System.out.print(NewAesUtils.decrypt("JY1naHJM7cplthDpa6GbG4xLnz3suvLhRcmizzKDVd8I4qBmOAmhG9YYxdPlNdyl", "E5F43AFDA3B748ABB1D0EBC9F986E7E0"));
    }
}
