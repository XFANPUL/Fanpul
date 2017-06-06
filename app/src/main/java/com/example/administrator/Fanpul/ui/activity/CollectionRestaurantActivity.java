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
import com.example.administrator.Fanpul.ui.adapter.RestaurantCollectionAdapter;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

//收藏店铺的activity
public class CollectionRestaurantActivity extends BaseActivity {
    @Bind(R.id.storelist)
    public RecyclerView recyclerView;
    private CommonAdapter commonAdapter;

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
        commonAdapter = new RestaurantCollectionAdapter(this,R.layout.collection_shop_item,null);//初始化CommonAdapter
        recyclerView.setAdapter(commonAdapter);//设置适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //根据username从数据库取出该用户收藏的店铺
        DBConnection.getCollectionRestaurant(getString(R.string.user_name), new DBQueryCallback<Restaurant>() {
            @Override
            public void Success(List<Restaurant> bmobObjectList) {
                commonAdapter.setmDatas(bmobObjectList);
            }

            @Override
            public void Failed() {

            }
        });
    }

}
