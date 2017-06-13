package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.CategoryAdapter;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class CookBookFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {//菜单fragemnt

    @Bind(R.id.recyview_cook_book_category)
    public RecyclerView recyclerViewCategory;//菜品类别列表
    @Bind(R.id.recyview_cook_book_menu)
    public RecyclerView recyclerViewCookBook;//菜品列表
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;

    private CategoryAdapter categoryAdapter;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.menu_cook_book_fragment;
    }

    public static CookBookFragment CreateFragment() {
        return new CookBookFragment();
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCookBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryAdapter = new CategoryAdapter(getActivity(), R.layout.cook_book_category_item, null, recyclerViewCookBook);
        recyclerViewCategory.setAdapter(categoryAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        updateUI();
    }

    public void updateUI() {
        DBProxy.proxy.queryRestaurantCategory(((SegmentTabActivity) getActivity()).getRestaurant().getRestaurantName(), new DBQueryCallback<MenuCategory>() {
            @Override
            public void Success(List<MenuCategory> bmobObjectList) {
                categoryAdapter.setmDatas(bmobObjectList);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void Failed() {
                Log.i("Failed", "Failed");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        updateUI();
    }
}
