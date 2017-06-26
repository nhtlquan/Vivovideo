package com.smile.studio.libsmilestudio.security;

import java.security.MessageDigest;

public class MD5 {

    public static String encrypt(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String encrypt(String string, int nLoop) {
        if (nLoop <= 1)
            return encrypt(string);
        return encrypt(encrypt(string), nLoop -= 1);
    }
}
