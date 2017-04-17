package com.example.administrator.Fanpul.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("ValidFragment")

public class SimpleCardFragment extends Fragment {
    private String mTitle;

    private List<Map<String, Object>> list ;  //ListView 装数据用的
    private Map<String,Object> map ;

    private Restaurant restaurant; //餐馆

    public SimpleCardFragment() {
    }

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment(); //取得和标题相符合的SimpleCardFragments
        sf.mTitle = title;
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurant = (Restaurant) getActivity().getIntent().getSerializableExtra(SegmentTabActivity.SEGMENTTAB_RESTAURANT);

        if (mTitle.equals("排队")) {
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.line_simple_card, container,false);
            TextView card_title_tv = (TextView) v.findViewById(R.id.test_line);
            Button button = (Button)v.findViewById(R.id.order_menu_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getActivity(), OrderMenuActivity.class);
                    intent.putExtra(ZxingFragment.TAG_ZXING,restaurant.getRestaurantName());
                    startActivity(intent);
                }
            });
            card_title_tv.setText(mTitle);
            return v;
        } else if (mTitle.equals("店铺")) {
            View v = inflater.inflate(R.layout.shopinfo_simple_card,container,false);
            TextView shopinner_shopnameText = (TextView)v.findViewById(R.id.shopinner_shopname);
            shopinner_shopnameText.setText(restaurant.getRestaurantName());
            TextView shopinner_sale = (TextView)v.findViewById(R.id.shopinner_sale);
            shopinner_sale.setText("月售"+restaurant.getOrder());
            TextView  shopinner_score =(TextView) v.findViewById(R.id.shopinner_score);
            shopinner_score.setText("评分"+restaurant.getScore());
            TextView shopinner_description = (TextView)v.findViewById(R.id.shopinner_description) ;
            shopinner_description.setText(restaurant.getDescription());
            return v;
        }else if(mTitle.equals("排队中")){
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.orders_list_card, null);
             ListView orders_listView = (ListView) v.findViewById(R.id.orders_list);  //订单的通用ListView
            list = this.get_orders_inline_data();
            OrdersListAdapter od_adapter = new OrdersListAdapter(getActivity(), list, mTitle);//新建适配器
            orders_listView.setAdapter(od_adapter);//绑定适配器

            return v;
        }else if (mTitle.equals("待评论")){
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.orders_list_card, null);
            ListView orders_listView = (ListView) v.findViewById(R.id.orders_list);
            list = this.get_orders_to_eva_data();
            OrdersListAdapter od_adapter = new OrdersListAdapter(getActivity(), list, mTitle);//新建适配器
            orders_listView.setAdapter(od_adapter);//绑定适配器
            return v;
        }else{
            View v = inflater.inflate(R.layout.line_simple_card, null);
            TextView card_title_tv = (TextView) v.findViewById(R.id.test_line);
            card_title_tv.setText(mTitle);
            return v;
        }
     }

    public List<Map<String, Object>> get_orders_inline_data(){

        String[] names={"红烧狮子头","宫保鸡丁","水煮肉片","嫩牛五方","鱼香肉丝"};

        list = new ArrayList<Map<String, Object>>();


        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("shop_name", names[i]);
            list.add(map);
        }
        return list;
    }

    public List<Map<String, Object>> get_orders_to_eva_data(){

        String[] names={"红烧狮子头","宫保鸡丁","水煮肉片","嫩牛五方","鱼香肉丝"};

        list = new ArrayList<Map<String, Object>>();


        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("shop_name", names[i]);
            list.add(map);
        }
        return list;
    }
}


