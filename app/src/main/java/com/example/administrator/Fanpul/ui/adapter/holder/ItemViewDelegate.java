package com.example.administrator.Fanpul.ui.adapter.holder;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item);

    void bindData(ViewHolder holder, T t);
}
