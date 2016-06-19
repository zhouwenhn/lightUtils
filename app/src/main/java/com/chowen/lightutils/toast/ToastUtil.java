package com.chowen.lightutils.toast;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.chowen.lightutils.LightUtilsApplication;

/**
 * toast util
 * @author zhouwen
 * Email:chowen0927@gmail.com
 * Created by 2016/6/19.
 */
public class ToastUtil {

    public static void showToast(String msg){
        Toast.makeText(LightUtilsApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int msgId){
        String msg = LightUtilsApplication.getInstance().getString(msgId);
        showToast(msg);
    }

    public static void showToast(String msg, int duration){
        Toast toast = new Toast(LightUtilsApplication.getInstance());
        toast.setDuration(duration);
        toast.show();
    }

    public static void showToast(@StringRes int msgId, int duration){
        String msg = LightUtilsApplication.getInstance().getString(msgId);
        showToast(msg, duration);
    }

    public static void showToast(View view, String msg, int duration){
        Toast toast = new Toast(LightUtilsApplication.getInstance());
        toast.setText(msg);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void showToast(@LayoutRes int viewRes, String msg, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(LightUtilsApplication.getInstance());
        View view = layoutInflater.inflate(viewRes, null);
        showToast(view, msg, duration);
    }

    public static void showToast(@LayoutRes int viewRes, @StringRes int msgId, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(LightUtilsApplication.getInstance());
        View view = layoutInflater.inflate(viewRes, null);
        String msg = LightUtilsApplication.getInstance().getString(msgId);
        showToast(view, msg, duration);
    }

    public static void showToast(@StringRes int msgId, int gravity,int xOffset, int yOffset){
        String msg = LightUtilsApplication.getInstance().getString(msgId);
        showToast(msg, gravity, xOffset, yOffset);
    }

    public static void showToast(String msg, int gravity, int xOffset, int yOffset){
        Toast toast = new Toast(LightUtilsApplication.getInstance());
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setText(msg);
        toast.show();
    }
}
