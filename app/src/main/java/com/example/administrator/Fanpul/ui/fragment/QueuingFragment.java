package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.AdapterManager;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class QueuingFragment extends BaseFragment {  //排队中的fragment
    @Bind(R.id.recy_order_view)
    public RecyclerView orders_recy_View;  //订单的通用ListView
    RecyclerView.Adapter queuingAdapter;
    @Override
    public void onResume() {
        if(queuingAdapter!=null)
        BmobUtil.queryQueueByUserName("张三", new BmobQueryCallback<Queue>() {
            @Override
            public void Success(List<Queue> bmobObjectList) {
                queuingAdapter = AdapterManager.getQueuingAdapter(getActivity(), bmobObjectList);//新建适配器
                orders_recy_View.setAdapter(queuingAdapter);//绑定适配器
                //queuingAdapter.notifyDataSetChanged();
            }
            @Override
            public void Failed() {

            }
        });

        super.onResume();
    }

    public void updateUI(){

    }
    public static QueuingFragment CreateFragment(){
        return  new QueuingFragment();
    }
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
            orders_recy_View.setLayoutManager(new LinearLayoutManager(getActivity()));
            // initDatabase();
            BmobUtil.queryQueueByUserName("张三", new BmobQueryCallback<Queue>() {
            @Override
            public void Success(List<Queue> bmobObjectList) {
                 queuingAdapter = AdapterManager.getQueuingAdapter(getActivity(), bmobObjectList);//新建适配器
                 orders_recy_View.setAdapter(queuingAdapter);//绑定适配器
            }
            @Override
            public void Failed() {

            }
        });

    }

    public static void cancel(Context context){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(0);
    }

}
