package com.chowen.cn.library.encryption.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhouwen
 * @version 0.1
 *  @since 2016/4/8
 */
public class FileNameGenerator {

    public static String generator(String value) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            try {
                mDigest.update(value.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(value.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
