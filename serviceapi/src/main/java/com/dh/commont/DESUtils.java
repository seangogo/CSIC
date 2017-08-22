package com.dh.commont;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;

public class DESUtils {

//	public static void main(String[] args) {
////		String a = DESUtils.encrypt("123456");
////		String a = DESUtils.encrypt("123456");
////		System.out.println("加密:" + a);
//
////		String b = DESUtils.decrypt(a);
//		String b = DESUtils.decrypt("14171CA123B1A61D");
//		System.out.println("解密" + b);
//	}

    private final static String KEY = "WHDHXXKJ";

    /**
     * 加密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message) {
        try {
            String jiami = java.net.URLEncoder.encode(message, "utf-8").toLowerCase();
            return toHexString(encrypt1(jiami, KEY)).toUpperCase();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) {
        try {
            return java.net.URLDecoder.decode(decrypt1(message, KEY), "utf-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }


    /**
     * 加密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String message, String key) {
        try {
            String jiami = java.net.URLEncoder.encode(message, "utf-8").toLowerCase();
            return toHexString(encrypt1(jiami, key)).toUpperCase();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key) {
        try {
            return java.net.URLDecoder.decode(decrypt1(message, key), "utf-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    // 解密数据
    private static String decrypt1(String message, String key) throws Exception {

        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    private static byte[] encrypt1(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }

        return hexString.toString();
    }

}
