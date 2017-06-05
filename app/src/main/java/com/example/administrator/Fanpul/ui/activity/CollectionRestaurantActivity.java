package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class CollectionRestaurantActivity extends BaseActivity {
    @Bind(R.id.storelist)
    public RecyclerView recyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.collection_shop;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DBConnection.getCollectionRestaurant("张三", new DBQueryCallback<Restaurant>() {
            @Override
            public void Success(List<Restaurant> bmobObjectList) {
               recyclerView.setAdapter(new RestaurantCollectionAdapter(bmobObjectList));
            }

            @Override
            public void Failed() {

            }
        });
    }


    public static void starActivity(Context context){
        Intent i = new Intent(context,CollectionRestaurantActivity.class);
        context.startActivity(i);
    }

    private class RestaurantCollectionHolder extends RecyclerView.ViewHolder{
        private ImageView shop_icon;
        private TextView shop_name;
        private TextView shop_sale_infor;
        public RestaurantCollectionHolder(View itemView) {
            super(itemView);
            shop_icon = (ImageView)itemView.findViewById(R.id.shop_icon);
            shop_name = (TextView)itemView.findViewById(R.id.shop_name);
            shop_sale_infor = (TextView)itemView.findViewById(R.id.shop_sale_infor);
        }

        public void bindData(Restaurant restaurant){
            new GlideUtil().attach(shop_icon).injectImageWithNull(restaurant.getIconfile().getUrl());
            shop_name.setText(restaurant.getRestaurantName());
            shop_sale_infor.setText("月售"+restaurant.getOrder()+"+单  | "+restaurant.getScore()+"分 | "
            + "离我"+restaurant.getDistance()+"米");
        }
    }

    private class RestaurantCollectionAdapter extends  RecyclerView.Adapter<RestaurantCollectionHolder>{
        List<Restaurant> restaurantList;
        public RestaurantCollectionAdapter(List<Restaurant> restaurants){
            restaurantList = restaurants;
        }
        @Override
        public RestaurantCollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(CollectionRestaurantActivity.this);
            View v = layoutInflater.inflate(R.layout.collection_shop_item,parent,false);
            return new RestaurantCollectionHolder(v);
        }

        @Override
        public void onBindViewHolder(RestaurantCollectionHolder holder, int position) {
            holder.bindData(restaurantList.get(position));
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }
    }
}
