package com.chowen.cn.library.screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.chowen.cn.library.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author chowen
 * @version 0.1
 * @since 16/8/25
 */
public class ScreenShot {

    public static int SNAP_WIDTH;

    public static int SNAP_HEIGHT;

    private static int SNAP_FORMAT;
    //截屏保持目录
    private static String mSaveDir = "Util.getScreenShotImageDir(LibApplication.getInstance()).getAbsolutePath()";

    public static final String COMPRESS_PATH_NAME = mSaveDir + File.separator
            + "compress_screen_shot.jpg";

    private static final boolean DEBUG = true;

    /**
     * 全屏截图
     * @return true or false
     */
    public static boolean screenShotBitmapFromSnapShot() {

        String SCREENCAP_PATH = "/system/bin/screencap";
        String SNAP_FILE_NAME = "/snapcache";

        if (!ShellUtil.execShell(SCREENCAP_PATH + " " + mSaveDir + SNAP_FILE_NAME)) {
            return false;
        }
        return true;

    }

    /**
     * root授权
     * @return true or false
     */
    public static boolean tryGetRoot() {
        long rawTime = System.currentTimeMillis();
        boolean hasServU = ShellUtil.execShellServU("pwd");
        long currentTime = System.currentTimeMillis() - rawTime;
        if (currentTime > 2000) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        if (!hasServU) {
            return false;
        }
        return true;

    }

    /**
     * 返回位图对象
     * @param context context
     * @return 位图对象
     */
    public static Bitmap getBitmapSnapShot(Context context) {

        String SNAP_FILE_NAME = "/snapcache";
        Bitmap creatArgbBmp = null;

        byte rgbData[] = getCacheFileForBytes(mSaveDir + SNAP_FILE_NAME);

        if (rgbData == null) {
            return null;
        }
        // 根据该ARGB数组生成位图
        creatArgbBmp = createBitmapByARGB(rgbData, SNAP_WIDTH, SNAP_HEIGHT);

        if (isNeedToRotate(context) && creatArgbBmp != null) {

            // 定义矩阵对象
            Matrix matrix = new Matrix();
            // 缩放原图
            matrix.postScale(1f, 1f);
            // 向左旋转45度，参数为正则向右旋转
            matrix.postRotate(-90);
            // bmp.getWidth(), bmp.getHeight()分别表示重绘后的位图宽高
            Bitmap rotateBmp = Bitmap.createBitmap(creatArgbBmp, 0, 0,
                    creatArgbBmp.getWidth(), creatArgbBmp.getHeight(), matrix, true);

            creatArgbBmp.recycle();
            creatArgbBmp = null;

            return rotateBmp;
        }

        return creatArgbBmp;

    }

    /**
     * 缓存文件转字节数组
     *
     * @param filePath 文件路径
     * @return 字节数组
     */
    public static byte[] getCacheFileForBytes(String filePath) {

        byte[] buffer = null;

        try {

            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            int length = (int) file.length();
            int rgb_data_length = length - 12;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(
                    rgb_data_length);

            byte[] bmp_info_t = new byte[4];
            fis.read(bmp_info_t);
            SNAP_WIDTH = Integer.parseInt(byteToBit(bmp_info_t[1])
                    + byteToBit(bmp_info_t[0]), 2);
            fis.read(bmp_info_t);
            SNAP_HEIGHT = Integer.parseInt(byteToBit(bmp_info_t[1])
                    + byteToBit(bmp_info_t[0]), 2);
            fis.read(bmp_info_t);
            SNAP_FORMAT = Integer.parseInt(byteToBit(bmp_info_t[1])
                    + byteToBit(bmp_info_t[0]), 2);

            fis.skip(12);
            byte[] rgb_data = new byte[rgb_data_length];
            fis.read(rgb_data, 0, rgb_data_length);
            bos.write(rgb_data, 0, rgb_data_length);

            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            file.delete();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return buffer;

    }

    /**
     * ARGB字节数组构造Bitmap
     *
     * @param data data
     * @param width width
     * @param height height
     * @return bitmap
     */
    public static Bitmap createBitmapByARGB(byte[] data, int width, int height) {

        int[] colors = convertByteToColor(data);
        if (colors == null) {
            return null;
        }

        Bitmap bmp = null;

        try {

            bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                    Bitmap.Config.ARGB_8888);

            if (DEBUG) {
                Logger.e("CropperActivity", "SnapShotbitmapwh=" + bmp.getWidth()
                        + "*" + bmp.getHeight());
            }

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }

        return bmp;
    }

    /**
     * 将ARGB数组转像素数组
     *
     * @param data ARGB数组值
     * @return
     */
    private static int[] convertByteToColor(byte[] data) {

        int size = data.length;
        if (size == 0) {
            return null;
        }

        int[] color = new int[size / 4];

        for (int m = 0; m < color.length; m++) {
            int r = (data[m * 4] & 0xFF);
            int g = (data[m * 4 + 1] & 0xFF);
            int b = (data[m * 4 + 2] & 0xFF);
            int a = (data[m * 4 + 3] & 0xFF);
            color[m] = (a << 24) + (r << 16) + (g << 8) + b;
        }

        return color;
    }

    /**
     * Byte转Bit
     */
    private static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * 是否需要旋转
     *
     * @param context
     * @return 是否需要旋转
     */
    private static boolean isNeedToRotate(Context context) {

        if (context.getResources().getDisplayMetrics().widthPixels == SNAP_WIDTH) {
            return false;

        } else {
            return true;

        }
    }
}
