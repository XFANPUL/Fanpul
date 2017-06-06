package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class RestaurantCollectionAdapter extends CommonAdapter<Restaurant> {
    public RestaurantCollectionAdapter(Context context, int layoutId, List<Restaurant> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(ViewHolder holder, Restaurant restaurant) {
        holder.setImageByUrl(R.id.shop_icon,restaurant.getIconfile().getUrl());
        holder.setText(R.id.shop_name,restaurant.getRestaurantName());
        holder.setText(R.id.shop_sale_infor,getmContext().getString(R.string.shop_info,restaurant.getOrder(),
                restaurant.getScore(),restaurant.getDistance()));

    }

}
