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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MyEvaActivity extends BaseActivity {
    @Bind(R.id.my_head_login)
    ImageView my_head_login; //用户的登录头像
    @Bind(R.id.my_login_username)
    TextView my_login_username;  //用户名
    @Bind(R.id.my_member_lv)
    TextView my_member_lv;  //总共有几条评论
    @Bind(R.id.list_my_eva)
    RecyclerView rcy_my_eva;

    public static void startActivity(Context context){
        Intent i = new Intent(context,MyEvaActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_eva;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        my_login_username.setText("张三");
        rcy_my_eva.setLayoutManager(new LinearLayoutManager(this));
        DBConnection.getEvaluateRestaurantByUserName("张三", new DBQueryCallback<EvaluateRestaurant>() {
            @Override
            public void Success(List<EvaluateRestaurant> bmobObjectList) {
                my_member_lv.setText("已贡献"+bmobObjectList.size()+"条评价");
                rcy_my_eva.setAdapter(new AdapterMyEva(bmobObjectList));
            }

            @Override
            public void Failed() {

            }
        });
    }

    class AdapterMyEva extends RecyclerView.Adapter<HolderViewMyEva> {

        private List<EvaluateRestaurant> evaluateRestaurants = new ArrayList<>();

        public AdapterMyEva(List<EvaluateRestaurant> evaluateRestaurantList) {
            evaluateRestaurants = evaluateRestaurantList;
        }

        @Override
        public HolderViewMyEva onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.match_my_eva_item, parent, false);
            return new HolderViewMyEva(view);
        }

        @Override
        public void onBindViewHolder(HolderViewMyEva holder, int position) {
            holder.BindData(evaluateRestaurants.get(position));
        }

        @Override
        public int getItemCount() {
            return evaluateRestaurants.size();
        }
    }

   public class HolderViewMyEva extends RecyclerView.ViewHolder {
                TextView store_name;
                ImageView left_img_shop_head;
                TextView my_name;
                TextView eva_date;
                RatingBar ratBarSum;
                TextView eva_rating_menu;
                TextView eva_rating_speed;
                TextView eva_rating_server;
                TextView et_feedback;
                TextView tv_price;

        public HolderViewMyEva(View itemView) {
            super(itemView);
            store_name = (TextView)itemView.findViewById(R.id.store_name);
            left_img_shop_head = (ImageView)itemView.findViewById(R.id.left_img_shop_head);
            my_name = (TextView)itemView.findViewById(R.id.my_name);
            eva_date = (TextView)itemView.findViewById(R.id.eva_date);
            ratBarSum = (RatingBar)itemView.findViewById(R.id.ratBarSum);
            eva_rating_menu = (TextView)itemView.findViewById(R.id.eva_rating_menu);
            eva_rating_speed = (TextView)itemView.findViewById(R.id.eva_rating_speed);
            eva_rating_server = (TextView)itemView.findViewById(R.id.eva_rating_server);
            et_feedback = (TextView)itemView.findViewById(R.id.et_feedback);
            tv_price = (TextView)itemView.findViewById(R.id.tv_price);
        }

        public void BindData(EvaluateRestaurant evaluateRestaurant) {
            Restaurant restaurant = evaluateRestaurant.getRestaurant();
            store_name.setText(restaurant.getRestaurantName());
            new GlideUtil().attach(left_img_shop_head).injectImageWithNull(evaluateRestaurant.getRestaurant().getIconfile().getUrl());
            my_name.setText(evaluateRestaurant.getUserName());
            eva_date.setText(evaluateRestaurant.getCreatedAt().substring(0, 10));
            ratBarSum.setRating((evaluateRestaurant.getServiceScore() + evaluateRestaurant.getSpeedScore() + evaluateRestaurant.getMenusScore()) / 3);
            eva_rating_menu.setText(evaluateRestaurant.getMenusScore() + "星");
            eva_rating_server.setText(evaluateRestaurant.getServiceScore() + "星");
            eva_rating_speed.setText(evaluateRestaurant.getSpeedScore() + "星");
            et_feedback.setText(evaluateRestaurant.getTextEvaluate());
            tv_price.setText("￥:"+evaluateRestaurant.getPrice());

        }
    }


}



