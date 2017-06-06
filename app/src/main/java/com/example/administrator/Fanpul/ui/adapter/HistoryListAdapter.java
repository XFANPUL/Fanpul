package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.DB.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class HistoryListAdapter extends CommonAdapter<Order> {
    public HistoryListAdapter(Context context, int layoutId, List<Order> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(final ViewHolder holder, Order order) {
        holder.setText(R.id.ordered_toeva_name, order.getRestaurantName())
                .setText(R.id.order_toeva_price, getmContext().getString(R.string.price, order.getTotalPrice()))
                .setText(R.id.ordered_toeva_date, order.getOrderDate());
        DBConnection.queryRestaurantByName(order.getRestaurantName(), new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                holder.setImageByUrl(R.id.orderdshop_img, result.getIconfile().getUrl());
            }

            @Override
            public void Failed() {

            }
        });
        holder.setVisibility(R.id.btn_order_toeva_mybook, View.GONE);
        holder.setVisibility(R.id.btn_order_toeva_eva, View.GONE);
    }
}
