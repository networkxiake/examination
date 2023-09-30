package com.xiaoyao.examination.user.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtil {
    /**
     * 随机生成一个盐值。
     */
    public static String generate() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 将明文字符串加密成一个长度为32的密文字符串。
     */
    public static String encrypt(String rowStr, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(rowStr.getBytes());
            BigInteger no = new BigInteger(1, hashedPassword);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
