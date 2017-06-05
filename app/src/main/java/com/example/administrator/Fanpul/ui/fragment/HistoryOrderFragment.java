package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.AdapterManager;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class HistoryOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    RecyclerView.Adapter adapter;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    @Override
    public void onResume() {

        if(adapter!=null)
            updateUI();

        super.onResume();
    }

    public static HistoryOrderFragment CreateFragment() {
        return new HistoryOrderFragment();
    }

    @Bind(R.id.recy_order_view)
    public RecyclerView orders_listView;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.orders_list_card;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeLayout.setOnRefreshListener(this);
        orders_listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }
    public void updateUI(){
        DBConnection.queryOrderByUserName("张三",1, new DBQueryCallback<Order>() {
            @Override
            public void Success(List<Order> bmobObjectList) {
                adapter = AdapterManager.getHistoryAdapter(getActivity(), bmobObjectList);//新建适配器
                orders_listView.setAdapter(adapter);//绑定适配器
                mSwipeLayout.setRefreshing(false);
            }

            @Override
            public void Failed() {

            }
        });

    }

    @Override
    public void onRefresh() {
        updateUI();
    }
}
