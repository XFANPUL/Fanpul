package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.OrderedAdapter;
import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/18 0018.
 */

public class SeeOrderDetailActivity extends BaseSwipeBackActivity {
    @Bind(R.id.recy_see_order_detail)
    public RecyclerView recyOrderDetail;
    @Bind(R.id.see_order_restaurant_name_text)
    public TextView seeOrderRestaurantNameText;
    @Bind(R.id.see_cook_detail_total_money)
    public TextView seeCookDetailTotalMoneyText;
    @Bind(R.id.see_order_custom_name)
    public TextView seeOrderCustomNameText;
    @Bind(R.id.cook_order_finished_time)
    public TextView cookOrderFinishedTimeText;
    @Bind(R.id.see_order_number)
    public TextView seeOrderNumber;

    private OrderedAdapter orderedAdapter;

    private Order order;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.see_order_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.fanpul));
        orderedAdapter = new OrderedAdapter(this, R.layout.shopcart_item, null, null);
        recyOrderDetail.setAdapter(orderedAdapter);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT);
        recyOrderDetail.setLayoutManager(new LinearLayoutManager(SeeOrderDetailActivity.this));
        orderedAdapter.setOrder(order);
        orderedAdapter.setmDatas(order.getMenuList());
        initUI();
    }

    public void initUI() {
        seeOrderRestaurantNameText.setText(order.getRestaurantName());
        seeCookDetailTotalMoneyText.setText(getString(R.string.money_number, order.getTotalPrice()));
        seeOrderCustomNameText.setText(order.getUserName());
        cookOrderFinishedTimeText.setText(order.getOrderDate());
        seeOrderNumber.setText(getString(R.string.product_num, order.getMenuNumber()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startActivity(Context context, Order order) {
        Intent intent = new Intent(context, SeeOrderDetailActivity.class);
        intent.putExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT, order);
        context.startActivity(intent);
    }
}