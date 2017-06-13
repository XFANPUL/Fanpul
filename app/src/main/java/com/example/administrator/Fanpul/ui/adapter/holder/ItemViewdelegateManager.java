package com.example.administrator.Fanpul.ui.adapter.holder;

import android.support.v4.util.SparseArrayCompat;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class ItemViewdelegateManager <T>{
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<>();

    public int getItemViewdelegateSize(){
        return  delegates.size();
    }

    public ItemViewdelegateManager<T> addItemViewdelegate(ItemViewDelegate<T> delegate){
        int viewType = delegates.size();
        if(delegates.get(viewType)==null){
            delegates.put(viewType,delegate);
        }
        return  this;
    }

    public ItemViewdelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate){
        if(delegate == null){
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int index = delegates.indexOfValue(delegate);
        if(index>=0){
            delegates.removeAt(index);
        }
        return this;
    }

    public ItemViewdelegateManager<T> removeDelegate(int viewType){
        int index = delegates.indexOfKey(viewType);
        if( index >=0 ){
            delegates.removeAt(index);
        }
        return  this;
    }

    public int getItemViewType(T item){
        int delegateCount = delegates.size();
        for(int i=0;i<delegateCount;i++){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if(delegate.isForViewType(item)){
                return delegates.keyAt(i);
            }
        }
        throw  new IllegalArgumentException("No ItemViewDelegate");
    }

    public void bindData(ViewHolder holder,T item){
        for(int i =0;i<delegates.size();i++){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if(delegate.isForViewType(item)){
                delegate.bindData(holder,item);
                return;
            }
        }
        throw  new IllegalArgumentException("No ItemViewDelegate");
    }

    public ItemViewDelegate getItemViewDelegate(int viewType){
        return delegates.get(viewType);
    }
}
