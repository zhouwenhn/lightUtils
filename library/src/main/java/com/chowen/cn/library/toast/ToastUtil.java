package com.chowen.cn.library.toast;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chowen.cn.library.LibApplication;

/**
 * toast util
 * @author zhouwen
 * Email:chowen0927@gmail.com
 * Created by 2016/6/19.
 */
public class ToastUtil {

    public static void showToast(String msg){
        Toast.makeText(LibApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@StringRes int msgId){
        String msg = LibApplication.getInstance().getResources().getString(msgId);
        showToast(msg);
    }

    public static void showToast(String msg, int duration){
        Toast toast = new Toast(LibApplication.getInstance().getApplicationContext());
        toast.setDuration(duration);
        toast.show();
    }

    public static void showToast(@StringRes int msgId, int duration){
        String msg = LibApplication.getInstance().getResources().getString(msgId);
        showToast(msg, duration);
    }

    public static void showToast(View view, String msg, int duration){
        Toast toast = new Toast(LibApplication.getInstance().getApplicationContext());
        toast.setText(msg);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void showToast(@LayoutRes int viewRes, String msg, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(LibApplication.getInstance().getApplicationContext());
        View view = layoutInflater.inflate(viewRes, null);
        showToast(view, msg, duration);
    }

    public static void showToast(@LayoutRes int viewRes, @StringRes int msgId, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(LibApplication.getInstance().getApplicationContext());
        View view = layoutInflater.inflate(viewRes, null);
        String msg = LibApplication.getInstance().getResources().getString(msgId);
        showToast(view, msg, duration);
    }

    public static void showToast(@StringRes int msgId, int gravity,int xOffset, int yOffset){
        String msg = LibApplication.getInstance().getResources().getString(msgId);
        showToast(msg, gravity, xOffset, yOffset);
    }

    public static void showToast(String msg, int gravity, int xOffset, int yOffset){
        Toast toast = new Toast(LibApplication.getInstance().getApplicationContext());
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setText(msg);
        toast.show();
    }
}
