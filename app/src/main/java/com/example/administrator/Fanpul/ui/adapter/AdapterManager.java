package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;

import java.util.List;

/**
 * Created by Administrator on 2017/4/28 0028.
 */

public class AdapterManager {  //adapter管理类

    //获得排队中的适配器
    public static RecyclerView.Adapter getQueuingAdapter(Context context, List<Queue> tList){
            return new OrdersListAdapter().CreateQueuingListAdapter(context,tList);
    }
    //获得待评论的适配器
    public static RecyclerView.Adapter getPreCommentAdapter(Context context, List<Order> tList ){
        return new OrdersListAdapter().CreatePreCommentListAdapter(context,tList);
    }
    //得到订单完成的适配器
    public static RecyclerView.Adapter getCompleteOrderAdapter(Context context, List<Order> tList ){
        return  new OrdersListAdapter().CreatePreCommentListAdapter(context,tList);
    }
}
