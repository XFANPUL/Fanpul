package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.CommentAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class ShopMoreFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.shop_comment_list)
    RecyclerView shopCommentRy;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    private Restaurant restaurant;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    public static Fragment CreateFragment() {
        return new ShopMoreFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_more_comment;
    }

    private CommentAdapter commentAdapter;

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shopCommentRy.setLayoutManager(new LinearLayoutManager(getActivity()));
        restaurant = ((SegmentTabActivity) getActivity()).getRestaurant();
        commentAdapter = new CommentAdapter(getActivity(), R.layout.shop_more_comment_content, null);
        shopCommentRy.setAdapter(commentAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        updateUI();
    }

    public void updateUI() {
        DBProxy.proxy.getEvaluateRestaurant(restaurant, new DBQueryCallback<EvaluateRestaurant>() {
            @Override
            public void Success(List<EvaluateRestaurant> bmobObjectList) {
                commentAdapter.setmDatas(bmobObjectList);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void Failed() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRefresh() {
      updateUI();
    }

}
