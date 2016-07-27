package com.chowen.cn.library.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * @author zhouwen
 * @date 2015/4/21.
 */
public class ViewHolderHelper {

    private SparseArray<View> mSparseArray;

    private View mConvertView;

    private Context mContext;

    private int mPosition;

    public ViewHolderHelper(Context context, int itemViewRes, View convertView, ViewGroup parent, int position) {
        mContext = context;
        mPosition = position;
        mSparseArray = new SparseArray<>();
        mConvertView = LayoutInflater.from(mContext).inflate(itemViewRes, null);
        mConvertView.setTag(this);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public static ViewHolderHelper getViewHolder(Context context, int position, int itemViewRes, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolderHelper(context, itemViewRes, convertView, parent, position);
        }
        return (ViewHolderHelper) convertView.getTag();
    }

    public <T extends View> T getItemView(int id) {
        View view = mSparseArray.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            mSparseArray.put(id, view);
        }
        return (T) view;
    }

    public ViewHolderHelper setText(int viewIdRes, CharSequence text) {
        TextView textView = getItemView(viewIdRes);
        textView.setText(text);
        return this;
    }

    public ViewHolderHelper setImageView(int viewIdRes, int resId) {
        ImageView imageView = getItemView(viewIdRes);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolderHelper setImageViewResUrl(int viewIdRes, String imageUrl) {
        ImageView imageView = getItemView(viewIdRes);
//        ImageLoader.getInstance().displayImage(imageUrl, imageView, UIUtils.getDefaultIconOptions());
        return this;
    }

    public ViewHolderHelper setImageViewDrawable(int viewIdRes, Drawable drawableRes) {
        ImageView imageView = getItemView(viewIdRes);
        imageView.setImageDrawable(drawableRes);
        return this;
    }

    public ViewHolderHelper setCheckable(int viewIdRes, boolean enable){
        CheckBox checkable = getItemView(viewIdRes);
        checkable.setChecked(enable);
        return this;
    }

    public ViewHolderHelper setBackgroundColor(int viewId, int color) {
        View view = getItemView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolderHelper setTextColor(int viewId, int color) {
        TextView view = getItemView(viewId);
        view.setTextColor(mContext.getResources().getColor(color));
        return this;
    }

    public ViewHolderHelper setVisiby(int viewIdRes, boolean isVisbility) {
        View view = getItemView(viewIdRes);
        view.setVisibility(isVisbility ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolderHelper setOnClickListener(int viewIdRes, View.OnClickListener listener) {
        View view = getItemView(viewIdRes);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolderHelper setOnLongClickListener(int viewIdRes, View.OnLongClickListener listener) {
        View view = getItemView(viewIdRes);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolderHelper setOnLongClickListener(int viewIdRes, View.OnTouchListener listener) {
        View view = getItemView(viewIdRes);
        view.setOnTouchListener(listener);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }
}
