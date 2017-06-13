package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ShopFragment extends BaseFragment {

    @Bind(R.id.shopinner_shopname)
    public TextView shopinner_shopnameText;

    @Bind(R.id.shopinner_sale)
    public TextView shopinner_sale;

    @Bind(R.id.shopinner_score)
    public TextView shopinner_score;

    @Bind(R.id.shopinner_description)
    public TextView shopinner_description;

    @Bind(R.id.shopinner_ratBarShop)
    public RatingBar shopinner_ratBarShop;  //店铺的菜品评分

    @Bind(R.id.shopinner_ratBarSpeed)
    public RatingBar shopinner_ratBarSpeed;  //店铺的上菜速度评分

    @Bind(R.id.shopinner_ratBarServer)
    public RatingBar shopinner_ratBarServer;  //店铺的服务评分

    @Bind(R.id.shop_collect)
    public ImageView shop_collect;

    private Restaurant restaurant;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shopinfo_simple_card;
    }

    public static ShopFragment CreateFragment() {
        return new ShopFragment();
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         restaurant = ((SegmentTabActivity) getActivity()).getRestaurant();
        shopinner_shopnameText.setText(restaurant.getRestaurantName());
        shopinner_sale.setText("月售" + restaurant.getOrder());
        shopinner_score.setText("评分" + restaurant.getScore());
        shopinner_description.setText(restaurant.getDescription());

        DBProxy.proxy .judgeIsCollectionRestaurant("张三", restaurant, new OneObjectCallBack<Boolean>() {
            @Override
            public void Success(Boolean result) {
                updateUI(result);
            }

            @Override
            public void Failed() {

            }
        });

        //收藏动作只用更换图片就行
        /*shop_collect.setImageResource(R.drawable.uncollect);// 没收藏
        shop_collect.setImageResource(R.drawable.timg);// 收藏了*/


        //显示店铺的评分星级
        /*float i = (float)4.5;
        shopinner_ratBarShop.setRating(i);*/

    }

    public void updateUI(boolean result) {
        if (result) {
            shop_collect.setImageResource(R.drawable.timg);
            shop_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBProxy.proxy .operationCollectionRestaurant("delete", "张三", restaurant, new OneObjectCallBack() {
                        @Override
                        public void Success(Object result) {
                            updateUI(false);
                        }

                        @Override
                        public void Failed() {

                        }
                    });
                }
            });
        }else{
            shop_collect.setImageResource(R.drawable.uncollect);
            shop_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBProxy.proxy .operationCollectionRestaurant("add", "张三", restaurant, new OneObjectCallBack() {
                        @Override
                        public void Success(Object result) {
                            updateUI(true);
                        }

                        @Override
                        public void Failed() {

                        }
                    });
                }
            });
        }
    }
}
