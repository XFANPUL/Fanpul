package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.AdapterManager;
import java.util.List;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class PreCommentFragment extends BaseFragment { //带评论fragment

    public static PreCommentFragment CreateFragment() {
        return new PreCommentFragment();
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
        orders_listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BmobUtil.queryOrderByUserName("张三",0, new BmobQueryCallback<Order>() {
            @Override
            public void Success(List<Order> bmobObjectList) {
                RecyclerView.Adapter adapter = AdapterManager.getPreCommentAdapter(getActivity(), bmobObjectList);//新建适配器
                orders_listView.setAdapter(adapter);//绑定适配器
            }

            @Override
            public void Failed() {

            }
        });

    }
}
