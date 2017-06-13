package com.example.administrator.Fanpul.ui.adapter.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.example.administrator.Fanpul.manager.AdapterSingletonManager;
import com.example.administrator.Fanpul.ui.adapter.holder.ItemViewDelegate;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected LayoutInflater layoutInflater;

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        addItemViewDelegate(new ItemViewDelegate<T>(){
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item) {
                return true;
            }

            @Override
            public void bindData(ViewHolder holder, T t) {
              CommonAdapter.this.bindDatas(holder,t);
            }
        });
    }
    public abstract void bindDatas(ViewHolder holder, T t);

}
