package com.chowen.cn.library.screenshot;

import java.io.DataOutputStream;
import java.util.List;

/**
 * @author chowen
 * @version 0.1
 * @since 16/8/25
 */
public class ShellUtil {
    /**
     * 执行单句shell命令
     *
     * @param shell
     */
    public static synchronized boolean execShellServU(String shell) {

        try {
            Process	process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(shell + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();

            int processValue = process.getInputStream().read();
            if ( processValue == -1) {
                return false;
            }else  {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 执行单句shell命令
     *
     * @param shell
     */
    public static synchronized boolean execShell(String shell) {

        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(shell + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();

            if (process.waitFor() == 0) {
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 执行单句shell命令(不等待执行响应)
     *
     * @param shell
     */
    public static boolean execShellNotWaitFor(String shell) {
        try {

            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(shell + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 执行多句shell命令
     *
     * @param shells
     */
    public static boolean execShells(List<String> shells) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            for (String tmpCmd : shells) {
                os.writeBytes(tmpCmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
            os.close();

            if (process.waitFor() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取su权限
     *
     * @param shells
     */
    public static boolean checkRoot() {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            // 改变驱动文件权限
            for (int i = 0; i < 10; i++) {
                os.writeBytes("chmod 666 /dev/input/event" + i + "\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();

            if (process.waitFor() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
