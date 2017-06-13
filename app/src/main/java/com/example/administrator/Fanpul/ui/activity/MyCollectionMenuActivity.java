package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.MenuCookAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class MyCollectionMenuActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recyview_collection)
    public RecyclerView recyclerView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    private MenuCookAdapter menuCookAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.my_collection_menu_activity;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuCookAdapter = new MenuCookAdapter(this, R.layout.menu_cook_item, null);
        recyclerView.setAdapter(menuCookAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        updateUI();
    }

    public void updateUI() {
        DBProxy.proxy .queryCollectionMenubyUserName(getString(R.string.user_name), new DBQueryCallback<Menu>() {
            @Override
            public void Success(List<Menu> bmobObjectList) {
                menuCookAdapter.setmDatas(bmobObjectList);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void Failed() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static void starActivity(Context context) {
        Intent i = new Intent(context, MyCollectionMenuActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onRefresh() {
        updateUI();
    }

}
