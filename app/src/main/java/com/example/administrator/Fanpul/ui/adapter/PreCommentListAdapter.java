package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.EvaluateOrderActivity;
import com.example.administrator.Fanpul.ui.activity.SeeOrderDetailActivity;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class PreCommentListAdapter extends CommonAdapter<Order> {
    public PreCommentListAdapter(Context context, int layoutId, List<Order> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(final ViewHolder holder, final Order order) {
        holder.setText(R.id.ordered_toeva_name, order.getRestaurantName())
                .setText(R.id.order_toeva_price, getmContext().getString(R.string.price, order.getTotalPrice()))
                .setText(R.id.ordered_toeva_date, order.getOrderDate());

        DBProxy.proxy .queryRestaurantByName(order.getRestaurantName(), new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                holder.setImageByUrl(R.id.orderdshop_img, result.getIconfile().getUrl());
            }

            @Override
            public void Failed() {

            }
        });

        holder.setOnClickListener(R.id.btn_order_toeva_mybook, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeOrderDetailActivity.startActivity(getmContext(), order);
            }
        });

        holder.setOnClickListener(R.id.btn_order_toeva_eva, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateOrderActivity.startActivity(v.getContext(), order);
            }
        });
    }
}