package com.chowen.cn.library.toast;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    public static void showToast(@NonNull Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Context context, @StringRes int msgId){
        String msg = context.getResources().getString(msgId);
        showToast(context, msg);
    }

    public static void showToast(@NonNull Context context, String msg, int duration){
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showToast(@NonNull Context context, @StringRes int msgId, int duration){
        String msg = context.getResources().getString(msgId);
        showToast(context, msg, duration);
    }

    public static void showToast(@NonNull Context context, View view, String msg, int duration){
        Toast toast = new Toast(context);
        toast.setText(msg);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    public static void showToast(@NonNull Context context, @LayoutRes int viewRes, String msg, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewRes, null);
        showToast(context, view, msg, duration);
    }

    public static void showToast(@NonNull Context context, @LayoutRes int viewRes, @StringRes int msgId, int duration){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(viewRes, null);
        String msg = LibApplication.getInstance().getResources().getString(msgId);
        showToast(context, view, msg, duration);
    }

    public static void showToast(@NonNull Context context, @StringRes int msgId, int gravity,int xOffset, int yOffset){
        String msg = context.getResources().getString(msgId);
        showToast(context, msg, gravity, xOffset, yOffset);
    }

    public static void showToast(@NonNull Context context, String msg, int gravity, int xOffset, int yOffset){
        Toast toast = new Toast(context);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setText(msg);
        toast.show();
    }
}
