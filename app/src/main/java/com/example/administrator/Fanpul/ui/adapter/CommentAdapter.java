package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class CommentAdapter extends CommonAdapter<EvaluateRestaurant> {
    public CommentAdapter(Context context, int layoutId, List<EvaluateRestaurant> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(ViewHolder holder, EvaluateRestaurant evaluateRestaurant) {
        holder.setText(R.id.shop_comment_username, evaluateRestaurant.getUserName())
                .setText(R.id.shop_comment_time, evaluateRestaurant.getCreatedAt().substring(0, 10))
                .setText(R.id.shop_comment_content, evaluateRestaurant.getTextEvaluate())
                .setText(R.id.shop_comment_rating_menu, getmContext().getString(R.string.star,
                        getmContext().getString(R.string.menu_taste), evaluateRestaurant.getMenusScore()))
                .setText(R.id.shop_comment_rating_speed, getmContext().getString(R.string.star,
                        getmContext().getString(R.string.speed), evaluateRestaurant.getSpeedScore()))
                .setText(R.id.shop_comment_rating_server, getmContext().getString(R.string.star,
                        getmContext().getString(R.string.server), evaluateRestaurant.getServiceScore()));

    }

}

