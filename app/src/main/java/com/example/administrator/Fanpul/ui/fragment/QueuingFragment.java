package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.RestaurantService;
import com.example.administrator.Fanpul.ui.adapter.AdapterManager;
import com.example.administrator.Fanpul.ui.adapter.QueuingListAdapter;

import java.util.List;

import butterknife.Bind;
/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class QueuingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,RestaurantService.ServiceCallback{  //排队中的fragment
    @Bind(R.id.recy_order_view)
    public RecyclerView orders_recy_View;  //订单的通用ListView
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    QueuingListAdapter queuingAdapter;

    @Override
    public void onPause() {
        if(RestaurantService.getRestaurantService()!=null)
        RestaurantService.getRestaurantService().resetServiceCallback();
        super.onPause();
    }

    @Override
    public void onResume() {
        if(queuingAdapter!=null)
            updateUI();

        if(RestaurantService.getRestaurantService()!=null)
        RestaurantService.getRestaurantService().setServiceCallback(this);
        super.onResume();
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
        mSwipeLayout.setOnRefreshListener(this);
        orders_recy_View.setLayoutManager(new LinearLayoutManager(getActivity()));
        queuingAdapter = (QueuingListAdapter)AdapterManager.getQueuingAdapter(getActivity(), null);//新建适配器
        orders_recy_View.setAdapter(queuingAdapter);//绑定适配器
        updateUI();

    }
    public void updateUI(){
        DBProxy.proxy .queryQueueByUserName("张三", new DBQueryCallback<Queue>() {
            @Override
            public void Success(List<Queue> bmobObjectList) {
                queuingAdapter.setmDatas(bmobObjectList);//新建适配器
                mSwipeLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        updateUI();
    }

    @Override
    public void UpdateUI() {
        updateUI();
    }
}
