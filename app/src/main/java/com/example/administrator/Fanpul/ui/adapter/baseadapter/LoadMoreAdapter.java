package com.example.administrator.Fanpul.ui.adapter.baseadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;
import com.example.administrator.Fanpul.utils.WrapperUtil;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class LoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE-2;
    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    public View getmLoadMoreView() {
        return mLoadMoreView;
    }

    public LoadMoreAdapter setmLoadMoreView(View mLoadMoreView) {
        this.mLoadMoreView = mLoadMoreView;
        return this;
    }

    public int getmLoadMoreLayoutId() {
        return mLoadMoreLayoutId;
    }

    public LoadMoreAdapter setmLoadMoreLayoutId(int mLoadMoreLayoutId) {
        this.mLoadMoreLayoutId = mLoadMoreLayoutId;
        return this;
    }

    public LoadMoreAdapter(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }
    private boolean hasMore(){
        return mLoadMoreView!=null||mLoadMoreLayoutId!=0;
    }

    @Override
    public int getItemViewType(int position) {
        if(isShowMore(position)){
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    public boolean isShowMore(int position){
        return hasMore()&&(position>=mInnerAdapter.getItemCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_LOAD_MORE){
            ViewHolder holder;
            if(mLoadMoreView!=null)
             holder = ViewHolder.createViewHolder(parent.getContext(),mLoadMoreView);
            else{
                holder = ViewHolder.createViewHolder(parent.getContext(),parent,mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isShowMore(position)){
           if(mOnLoadMoreListener!=null){
               mOnLoadMoreListener.onLoadMoreRequest();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder,position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtil.onAttachToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtil.SpanSizeCallBack() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldlookup, int position) {
                if(isShowMore(position)){
                    return layoutManager.getSpanCount();
                }
                if(oldlookup!=null){
                    oldlookup.getSpanSize(position);
                }
                return 1;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount()+(hasMore()?1:0);
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public LoadMoreAdapter setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        if(onLoadMoreListener!=null){
            this.mOnLoadMoreListener = onLoadMoreListener;
        }
        return this;
    }

    public interface OnLoadMoreListener{
        void onLoadMoreRequest();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

}
