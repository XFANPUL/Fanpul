package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class MyEvaluateAdapter extends CommonAdapter<EvaluateRestaurant> {
    public MyEvaluateAdapter(Context context, int layoutId, List<EvaluateRestaurant> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(ViewHolder holder, EvaluateRestaurant evaluateRestaurant) {

         holder.setText(R.id.store_name , evaluateRestaurant.getRestaurant().getRestaurantName())
                 .setImageByUrl(R.id.left_img_shop_head ,evaluateRestaurant.getRestaurant().getIconfile().getUrl() )
                 .setText(R.id.my_name , evaluateRestaurant.getUserName())
                 .setText(R.id.eva_date , evaluateRestaurant.getCreatedAt().substring(0, 10))
                 .setRatingBar(R.id.ratBarSum , (evaluateRestaurant.getServiceScore() +
                         evaluateRestaurant.getSpeedScore() + evaluateRestaurant.getMenusScore())/3)
                 .setText(R.id.eva_rating_menu , getmContext().getString(R.string.star , getmContext().getString(R.string.menu_taste), evaluateRestaurant.getMenusScore()))
                 .setText(R.id.eva_rating_speed , getmContext().getString(R.string.star , getmContext().getString(R.string.speed) , evaluateRestaurant.getSpeedScore()))
                 .setText(R.id.eva_rating_server , getmContext().getString(R.string.star , getmContext().getString(R.string.server) , evaluateRestaurant.getServiceScore()))
                 .setText(R.id.et_feedback , evaluateRestaurant.getTextEvaluate())
                 .setText(R.id.tv_price , getmContext().getString(R.string.price , evaluateRestaurant.getPrice()));


    }
}

