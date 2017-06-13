package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
//已点菜品的adapter
public class OrderedAdapter extends CommonAdapter<Menu> {
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private Order order;

    public OrderedAdapter(Context context, int layoutId, List<Menu> datas, Order order) {
        super(context, layoutId, datas);
        this.order = order;
    }

    @Override
    public void bindDatas(ViewHolder holder, Menu menu) {
        float price = menu.getPrice();
        holder.setImageByUrl(R.id.shop_cart_imageview1, menu.getImgUrl())
                .setText(R.id.shop_cart_textview1, menu.getMenuName())
                .setText(R.id.shop_cart_textview3, getmContext().getString(R.string.price, price))
                .setText(R.id.shop_cart_textview4, getmContext().getString(R.string.num, order.getMenuNumberList().get(holder.getAdapterPosition())));
    }
}

