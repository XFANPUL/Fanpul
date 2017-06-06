package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.util.List;

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
        getSupportActionBar().setTitle("饭谱");
        Intent intent = getIntent();
        order = (Order)intent.getSerializableExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT);
        recyOrderDetail.setLayoutManager(new LinearLayoutManager(SeeOrderDetailActivity.this));
        recyOrderDetail.setAdapter(new SeeOrderAdapter(order.getMenuList()));
        seeOrderRestaurantNameText.setText(order.getRestaurantName());
        seeCookDetailTotalMoneyText.setText("金额:￥"+order.getTotalPrice());
        seeOrderCustomNameText.setText(order.getUserName()+"");
        cookOrderFinishedTimeText.setText(order.getOrderDate()+"");
        seeOrderNumber.setText("共"+order.getMenuNumber()+"件商品");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public static void startActivity(Context context, Order order){
        Intent intent = new Intent(context,SeeOrderDetailActivity.class);
        intent.putExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT,order);
        context.startActivity(intent);
    }

    private class SeeOrderHolder extends  RecyclerView.ViewHolder{
        private ImageView seeOrderImg;
        private TextView seeMenuName;
        private TextView seePerprice;
        private TextView seeNumber;
        public SeeOrderHolder(View itemView) {
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

    private class  SeeOrderAdapter extends RecyclerView.Adapter<SeeOrderHolder>{
        private List<Menu> menulist;
        public SeeOrderAdapter(List<Menu> menus){
             menulist = menus;
        }

        @Override
        public SeeOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layout = LayoutInflater.from(SeeOrderDetailActivity.this);
            View v = layout.inflate(R.layout.shopcart_item,parent,false);
            return new SeeOrderHolder(v);
        }

        @Override
        public void onBindViewHolder(SeeOrderHolder holder, int position) {
             holder.bindHolder(menulist.get(position).getImgUrl(),menulist.get(position).getMenuName()
             ,menulist.get(position).getPrice(),order.getMenuNumberList().get(position));
        }

        @Override
        public int getItemCount() {
            return menulist.size();
        }
    }
}
