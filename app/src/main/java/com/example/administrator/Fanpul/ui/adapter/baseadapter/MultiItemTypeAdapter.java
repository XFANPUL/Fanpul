package com.example.administrator.Fanpul.ui.adapter.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.example.administrator.Fanpul.ui.adapter.holder.ItemViewDelegate;
import com.example.administrator.Fanpul.ui.adapter.holder.ItemViewdelegateManager;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    private List<T> mDatas = new ArrayList<>();

    protected ItemViewdelegateManager mItemViewdelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    @Override
    public int getItemViewType(int position) {
        if(!useItemViewDelegateManager())
            return super.getItemViewType(position);
      return mItemViewdelegateManager.getItemViewType(mDatas.get(position));
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    protected  boolean useItemViewDelegateManager(){
        return mItemViewdelegateManager.getItemViewdelegateSize()>0;

    }

    public MultiItemTypeAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        if(mDatas!=null)
        this.mDatas.addAll(mDatas);
        mItemViewdelegateManager = new ItemViewdelegateManager();
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewdelegateManager.getItemViewDelegate(viewType);
        int layoutId= itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(),parent,layoutId);
        setListener(holder);
        return holder;
    }

    protected void setListener(final ViewHolder holder){
       holder.getmItemView().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(mOnItemClickListener!= null){
                   int position = holder.getAdapterPosition();
                   mOnItemClickListener.onItemClick(view,holder,position);
               }
           }
       });

        holder.getmItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
              if(mOnItemClickListener!=null){
                  int position = holder.getAdapterPosition();
                  return mOnItemClickListener.onItemLongClick(view,holder,position);
              }
              return false;
            }
        });
    }

    public void bindData(ViewHolder holder,T t){
         mItemViewdelegateManager.bindData(holder,t);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          bindData(holder,mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewdelegateManager.addItemViewdelegate(itemViewDelegate);
        return this;
    }

    public void deleteItem(T item){
        if(null == item) return;
        mDatas.remove(item);
        notifyDataSetChanged();
    }

    public void addItem(T item){
        if(null == item) return;
        mDatas.add(item);
        notifyDataSetChanged();
    }

    public void deleteItemList(List<T> items){
        if(null == items) return;
       mDatas.removeAll(items);
        notifyDataSetChanged();
    }

    public void addItemList(List<T> items){
        if(null == items) return;
        mDatas.addAll(items);
        notifyDataSetChanged();
    }
}
