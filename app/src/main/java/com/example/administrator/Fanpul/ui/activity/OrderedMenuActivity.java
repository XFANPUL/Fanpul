package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class OrderedMenuActivity extends BaseSwipeBackActivity {//已点订单的Activity
    @Bind(R.id.recy_order_view)
    RecyclerView recyclerView;

    private Queue queue;
    private Order order;

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
        queue = (Queue)getIntent().getSerializableExtra(QUEUE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(queue.getRestaurantName());
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderedMenuActivity.this));

        new BmobQuery<Order>().getObject(queue.getMyOrder().getObjectId(), new QueryListener<Order>() {
                    @Override
                    public void done(Order orders, BmobException e) {
                        if(e==null){
                            order = orders;
                            recyclerView.setAdapter(new OrderedAdapter(order.getMenuList()));
                        }
                    }
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
          finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startOrderedMenuActivity(Context context,Queue queue){
        Intent intent = new Intent(context,OrderedMenuActivity.class);
        intent.putExtra(QUEUE,queue);
        context.startActivity(intent);
    }

    private class OrderedHolder extends  RecyclerView.ViewHolder{
        private ImageView seeOrderImg;
        private TextView seeMenuName;
        private TextView seePerprice;
        private TextView seeNumber;
        public OrderedHolder(View itemView) {
            super(itemView);
            seeOrderImg = (ImageView)itemView.findViewById(R.id.shop_cart_imageview1);
            seeMenuName = (TextView)itemView.findViewById(R.id.shop_cart_textview1);
            seePerprice = (TextView)itemView.findViewById(R.id.shop_cart_textview3);
            seeNumber = (TextView)itemView.findViewById(R.id.shop_cart_textview4);
        }

        public void bindHolder(String url, String name, int perPrice, int num) {
            if(url!=null&&url!="")
                new GlideUtil().attach(seeOrderImg).injectImageWithNull(url);
            seeMenuName.setText(name);
            seePerprice.setText("￥"+perPrice+".00");
            seeNumber.setText("×"+num);
        }
    }

    private class  OrderedAdapter extends RecyclerView.Adapter<OrderedHolder>{
        private List<Menu> menulist;
        public OrderedAdapter(List<Menu> menus){
            menulist = menus;
        }

        @Override
        public OrderedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layout = LayoutInflater.from(OrderedMenuActivity.this);
            View v = layout.inflate(R.layout.shopcart_item,parent,false);
            return new OrderedHolder(v);
        }

        @Override
        public void onBindViewHolder(OrderedHolder holder, int position) {
            holder.bindHolder(menulist.get(position).getImgUrl(),menulist.get(position).getMenuName()
                    ,menulist.get(position).getPrice(),order.getMenuNumberList().get(position));
        }

        @Override
        public int getItemCount() {
            return menulist.size();
        }
    }
}
