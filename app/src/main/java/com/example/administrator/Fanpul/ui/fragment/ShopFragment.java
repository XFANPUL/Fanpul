package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ShopFragment extends BaseFragment {

    @Bind(R.id.shopinner_shopname)
    public TextView shopinner_shopnameText ;

    @Bind(R.id.shopinner_sale)
    public TextView shopinner_sale;

    @Bind(R.id.shopinner_score)
    public TextView  shopinner_score ;

    @Bind(R.id.shopinner_description)
    public TextView shopinner_description ;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shopinfo_simple_card;
    }
    public static ShopFragment CreateFragment(){
        return  new ShopFragment();
    }
    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Restaurant restaurant = ((SegmentTabActivity)getActivity()).getRestaurant();
        shopinner_shopnameText.setText(restaurant.getRestaurantName());
        shopinner_sale.setText("月售"+restaurant.getOrder());
        shopinner_score.setText("评分"+restaurant.getScore());
        shopinner_description.setText(restaurant.getDescription());
    }
}
