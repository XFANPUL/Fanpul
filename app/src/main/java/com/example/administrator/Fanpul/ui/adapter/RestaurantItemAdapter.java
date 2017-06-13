package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class RestaurantItemAdapter extends CommonAdapter<Restaurant> {
    public RestaurantItemAdapter(Context context, int layoutId, List<Restaurant> datas) {
        super(context, layoutId, datas);
    }
    @Override
    public void bindDatas(ViewHolder holder, final Restaurant restaurant) {
        ImageItemAdapter imageItemAdapter = new ImageItemAdapter(getmContext(),R.layout.match_league_round_item,null);
           holder.setText(R.id.shop_name , restaurant.getRestaurantName())
                   .setImageByUrl(R.id.shop_icon , restaurant.getIconfile().getUrl())
                   .setRecyclerView(R.id.listViewStore , new LinearLayoutManager(getmContext(), LinearLayoutManager.HORIZONTAL,false))
                   .setRecyclerViewAdapter(R.id.listViewStore , imageItemAdapter)
                   .setText(R.id.shop_sale_infor , "月售" + restaurant.getOrder() + "单 |" + restaurant.getScore()
                           + "分 |离我" + restaurant.getDistance() + "米")
                   .setText(R.id.shop_line_up_infor , "当前排队: " + restaurant.getBigtableNum() + "大桌-" + restaurant.getMiddletableNum()
                           + "中桌-" + restaurant.getSmalltableNum() + "小桌  ");
        imageItemAdapter.setmDatas(getUrlList(restaurant.getBmobFileList()));
        holder.getmItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SegmentTabActivity.SegmentTabActivityStart(getmContext(), restaurant);
            }
        });
    }

    public List<String> getUrlList(List<BmobFile> bmobFiles){
        List<String> imgUrlList = new ArrayList<>();
        BmobFile b = null;//存储shop_more bmobFile
        if (bmobFiles.size() > 0) {
            for (int i = 0; i < bmobFiles.size(); i++) {
                if (bmobFiles.get(i).getFilename().equals("shop_more.jpg") || bmobFiles.get(i).getFilename().equals("shop_more.png")) {
                    b = bmobFiles.get(i);
                } else {
                    imgUrlList.add(bmobFiles.get(i).getUrl());
                }
            }
            if (b != null) {
                imgUrlList.add(b.getUrl());
            }
        }
        return imgUrlList;
    }
}