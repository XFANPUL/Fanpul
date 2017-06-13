package com.example.administrator.Fanpul.ui.adapter.baseadapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;
import com.example.administrator.Fanpul.utils.WrapperUtil;


/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 10000;
    private static final int BASE_ITEM_TYPE_FOOTER = 20000;

    private SparseArrayCompat<View> mHeaderView = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterView = new SparseArrayCompat<>();

    public RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView.get(viewType)!=null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(),mHeaderView.get(viewType));
            return holder;
        }else if(mFooterView.get(viewType)!=null){
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(),mFooterView.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderView.keyAt(position);
        }else if(isFooterView(position)){
            return mFooterView.keyAt(position-mHeaderView.size()-mInnerAdapter.getItemCount());
        }
        return mInnerAdapter.getItemViewType(position-mHeaderView.size());
    }

    public boolean isHeaderViewPos(int position){
        return position<mHeaderView.size();
    }

    public boolean isFooterView(int position){
        return position >= mHeaderView.size()+mInnerAdapter.getItemCount();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isHeaderViewPos(position)){
            return;
        }
        if(isFooterView(position)){
            return;
        }
        mInnerAdapter.onBindViewHolder(holder,position);

    }

    @Override
    public int getItemCount() {
        return mFooterView.size()+mInnerAdapter.getItemCount()+mHeaderView.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtil.onAttachToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtil.SpanSizeCallBack() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldlookup, int position) {
               int viewType = getItemViewType(position);
                if(mHeaderView.get(viewType)!=null){
                    return layoutManager.getSpanCount();
                }else if(mFooterView.get(viewType)!=null){
                    return layoutManager.getSpanCount();
                }
                if(oldlookup!=null){
                    return oldlookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    public void addHeaderView(View view){
        mHeaderView.put(mHeaderView.size()+BASE_ITEM_TYPE_HEADER,view);
    }

    public void addFooterView(View view){
        mFooterView.put(mHeaderView.size()+BASE_ITEM_TYPE_FOOTER,view);
    }
}
