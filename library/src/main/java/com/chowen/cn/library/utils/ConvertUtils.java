package com.chowen.cn.library.utils;

/**
 * Created by zhouwen on 16/8/14.
 */
public class ConvertUtils {

    static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 每1个byte转为2个hex字符
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param src byte数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] src) {
        char[] res = new char[src.length << 1];
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }
        return new String(res);
    }

    /**
     * 每2个hex字符转为1个byte
     * <p>例如：</p>
     * bytes2HexString("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return byte数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] res = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            res[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return res;
    }

    /**
     * 单个hex字符转为10进制
     *
     * @param hexChar hex单个字节
     * @return 0..15
     */
    private static int hex2Dec(char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'E') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
