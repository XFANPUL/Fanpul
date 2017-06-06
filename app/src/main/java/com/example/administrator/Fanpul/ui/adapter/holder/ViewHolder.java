package com.example.administrator.Fanpul.ui.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.Fanpul.utils.GlideUtil;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mItemView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(Context context, View itemView) {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public View getmItemView() {
        return mItemView;
    }

    /*public void setText(int viewId,String text){
        TextView textView = getView(viewId);
        textView.setText(text);
    }*/

    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return ViewHolder.this;
    }

    public String getText(int viewId) {
        TextView textView = getView(viewId);
        return textView.getText().toString();
    }

    public void setImageResource(int viewId, int drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawable);
    }

    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        GlideUtil.getGlideUtil().attach(imageView).injectImageWithNull(url);
        return ViewHolder.this;
    }

    public void setmItemView(View mItemView) {
        this.mItemView = mItemView;
    }

    public ViewHolder setOnCheckedChangeListener(int viewId, RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        RadioGroup radioGroup = getView(viewId);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        return ViewHolder.this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
        return this;

    }

    public ViewHolder setVisibility(int viewId, int flag) {
        View view = getView(viewId);
        view.setVisibility(flag);
        return ViewHolder.this;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


}
