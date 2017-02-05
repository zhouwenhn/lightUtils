package com.chowen.cn.library.encryption.securyty;


import com.chowen.cn.library.log.Logger;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SecurityUtil {

    private static final String CLASS_NAME = "SecurityUtil";

    private static final String strDefaultKey = "chowen123456";

    private static List<String> parseParam(Map<String, String> requestParam) {
        List<String> params = new ArrayList<String>(requestParam.size());
        for (Entry<String, String> entry : requestParam.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            if (key.equals("sign")) {
                continue;
            }
            params.add(entry.getKey() + "=" + value);
        }
        return params;
    }

    public static String md5(String s) {
        try {
            MessageDigest sMd5MessageDigest = MessageDigest.getInstance("MD5");
            StringBuilder sStringBuilder = new StringBuilder();
            sMd5MessageDigest.reset();
            sMd5MessageDigest.update(s.getBytes());
            byte digest[] = sMd5MessageDigest.digest();
            sStringBuilder.setLength(0);
            for (int i = 0; i < digest.length; i++) {
                final int b = digest[i] & 255;
                if (b < 16) {
                    sStringBuilder.append('0');
                }
                sStringBuilder.append(Integer.toHexString(b));
            }
            return sStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据输入流获得文件MD5摘要
     *
     * @param inputStream inputstream
     * @return string
     */
    public static String md5(InputStream inputStream) {
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                mdTemp.update(buffer, 0, numRead);
            }
            return toHexString(mdTemp.digest());
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHexString(byte[] md) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        int j = md.length;
        char str[] = new char[j * 2];
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[2 * i] = hexDigits[byte0 >>> 4 & 0xf];
            str[i * 2 + 1] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    public static String sign(String appId, String secret, String requestId,
                              HashMap<String, String> requestParam) {
        List<String> params = parseParam(requestParam);
        StringBuilder buffer = new StringBuilder();
        buffer.append(appId);
        buffer.append(secret);
        buffer.append(requestId);
        Collections.sort(params);
        for (String param : params) {
            buffer.append(param);
        }
        String sign = md5(buffer.toString());
        return sign;
    }

    /*
     * public static String decode(String str) { String nStr = str.substring(0,
     * str.length() - 1); byte[] bytes = nStr.getBytes(); byte[] nbytes = new
     * byte[bytes.length]; for (int i = 0; i < bytes.length; i++) { byte b =
     * bytes[i]; if (b >= 48 && b <= 57) { b = (byte) (b + (b <= 50 ? 7 :
     * (-3))); } else if (b >= 65 && b <= 90) { b = (byte) (b + (b <= 67 ? 23 :
     * (-3))); } nbytes[i] = b; } return new String(nbytes); }
     */

    // 简易加密
    public static String encrypt(String str, String key) {
        String sn = key;
        int[] snNum = new int[str.length()];
        String result = "";
        String temp = "";

        for (int i = 0, j = 0; i < str.length(); i++, j++) {
            if (j == sn.length())
                j = 0;
            snNum[i] = str.charAt(i) ^ sn.charAt(j);
        }

        for (int k = 0; k < str.length(); k++) {

            if (snNum[k] < 10) {
                temp = "000" + snNum[k];
            } else {
                if (snNum[k] < 100) {
                    temp = "00" + snNum[k];
                } else {
                    if (snNum[k] < 1000) {
                        temp = "0" + snNum[k];
                    }
                }
            }
            result += temp;
        }
        return result;
    }

    // 简易解密
    public static String decrypt(String str, String key) {
        String sn = key; // 密钥
        String result = "";
        try {
            char[] snNum = new char[str.length() / 4];
            for (int i = 0, j = 0; i < str.length() / 4; i++, j++) {
                if (j == sn.length())
                    j = 0;
                int n = Integer.parseInt(str.substring(i * 4, i * 4 + 4));
                snNum[i] = (char) ((char) n ^ sn.charAt(j));
            }
            for (int k = 0; k < str.length() / 4; k++) {
                result += snNum[k];
            }
        } catch (Exception e) {
            Logger.w(e.toString());
        }
        return result;
    }

    /**
     * 返回解密后的key
     *
     * @param encodedKeys
     * @return
     */
    private static int[] getDecodedKey(String[] encodedKeys) {
        int[] ret = new int[encodedKeys.length];
        for (int i = 0; i < encodedKeys.length; i++) {
            String org = "AAAA"
                    + SecurityUtil
                    .decrypt("00" + encodedKeys[i], strDefaultKey)
                    + "==";
            byte[] tmp = null;
            try {
                tmp = Base64.decode(org);
            } catch (Exception e) {
                Logger.w(CLASS_NAME, "getDecodeKey>>>" + e);
            }
            int tmpint = bytes2int(tmp);
            ret[i] = tmpint;
        }
        return ret;
    }

    private static int bytes2int(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int res = 0;
        for (int i = 0; i < 4; i++) {
            res <<= 8;
            temp = b[i] & mask;
            res |= temp;
        }
        return res;
    }

}
