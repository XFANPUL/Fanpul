package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.OrderedAdapter;
import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class OrderedMenuActivity extends BaseSwipeBackActivity implements SwipeRefreshLayout.OnRefreshListener {//已点菜品的Activity
    @Bind(R.id.recy_order_view)
    RecyclerView recyclerView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    private Queue queue;
    private OrderedAdapter orderedAdapter;

    public static final String QUEUE = "com.example.administrator.Fanpul.ui.activity.OrderedMenuActivity.QUEUE";

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.orders_list_card;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        queue = (Queue) getIntent().getSerializableExtra(QUEUE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(queue.getRestaurantName());
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderedMenuActivity.this));
        orderedAdapter = new OrderedAdapter(this, R.layout.shopcart_item, null, null);
        recyclerView.setAdapter(orderedAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startOrderedMenuActivity(Context context, Queue queue) {
        Intent intent = new Intent(context, OrderedMenuActivity.class);
        intent.putExtra(QUEUE, queue);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        updateUI();
    }

    public void updateUI(){
        new BmobQuery<Order>().getObject(queue.getMyOrder().getObjectId(), new QueryListener<Order>() {
            @Override
            public void done(Order orders, BmobException e) {
                if (e == null) {
                    orderedAdapter.setOrder(orders);
                    orderedAdapter.setmDatas(orders.getMenuList());
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }
}
