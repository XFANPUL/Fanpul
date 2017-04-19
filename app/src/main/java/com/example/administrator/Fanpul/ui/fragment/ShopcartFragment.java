package com.example.administrator.Fanpul.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.ui.activity.OrderFormActivity;
import com.example.administrator.Fanpul.ui.activity.ShoppingCartActivity;
import com.example.administrator.Fanpul.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class ShopcartFragment extends Fragment {

    private RecyclerView recyclerView;
    private GlideUtil glideUtil;
    private TextView payWaysText;
    private TextView okText;
    private CheckBox showNameCheckBox;
    private TextView restaurantNameText;
    private TextView payHint;

    private String payWays;
    private String restaurantName;
    private Integer showName;
    private String strCurDate;
    private String userName;
    private Integer evaluateState;
    private float totalmoney;
    private List<Integer> menuNumberList;

    private List<Menu> menuList = new ArrayList<>();
    private List<MainPageViewPageAdapter.Buy> lists = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showName = 0;
        evaluateState=0;
        glideUtil = new GlideUtil();
        userName = "张三";
        menuNumberList = new ArrayList<>();
        updateData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopcart_recycler, container, false);
        if(savedInstanceState!=null){
            savedInstanceState=null;
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.shopcart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new shopcartAdapter(lists));
        payWaysText = (TextView)getActivity().findViewById(R.id.pay_ways);
       /* payWaysText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });*/
        okText = (TextView)getActivity().findViewById(R.id.tv_buy_ok);
        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAgainOkDialog();
            }
        });

        showNameCheckBox = (CheckBox)getActivity().findViewById(R.id.shop_cart_checkbox);
        showNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    showName=0;
                }else{
                    showName=1;
                }
            }
        });

        restaurantName = getActivity().getIntent().getStringExtra(CookListFragment.restaurantNameIntent);
        restaurantNameText =(TextView) getActivity().findViewById(R.id.shop_cart_name);
        restaurantNameText.setText(restaurantName);
        payHint = (TextView)getActivity().findViewById(R.id.pay_ways_hint);
        payHint.setVisibility(View.INVISIBLE);
       /* payHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });*/
        LinearLayout linearLayout = (LinearLayout)getActivity().findViewById(R.id.linear_pay_ways);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });
        updateUI();
        return view;
    }

    private void showAgainOkDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确认支付");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(ShoppingCartActivity.this,"确认",Toast.LENGTH_SHORT).show();
                if (payWays == null || payWays.equals(" ")) {
                    payHint.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"支付失败",Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    strCurDate = formatter.format(curDate);
                    Order order = new Order();
                    order.setRestaurantName(restaurantName);
                    order.setTotalPrice(totalmoney);
                    order.setMenuList(menuList);
                    order.setPayMeasure(payWays);
                    order.setEvaluateState(evaluateState);
                    order.setMenuNumber(menuList.size());
                    order.setOrderDate(strCurDate);
                    order.setShowName(showName);
                    order.setUserName(userName);
                    order.setMenuNumberList(menuNumberList);
                    order.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Log.i("Success", s);
                            } else {
                                Log.i("Error", e.getMessage());
                            }
                        }
                    });
                    MainPageViewPageAdapter.buyMap.clear();
                    OrderFormActivity.startOrderFormActivity(getActivity(), order);
                    getActivity().finish();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(ShoppingCartActivity.this,"取消",Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void showPayDialog(){
        final String items[] = {"支付宝支付","微信支付","网银支付"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("支付方式");
        builder.setTitle("请选择支付方式");
        builder.setIcon(R.drawable.nav_discount);
        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getActivity(),items[which],Toast.LENGTH_SHORT).show();
                payWaysText.setText(items[which]);
                payWays = items[which];
                payHint.setVisibility(View.INVISIBLE);
            }
        });
        builder.create().show();
    }

    public void updateUI(){
        ((ShoppingCartActivity)getActivity()).updateUI(totalmoney,lists.size());
    }

    public void updateData(){
             totalmoney=0;
            Iterator<String> iterator = MainPageViewPageAdapter.buyMap.keySet().iterator();
            while (iterator.hasNext()) {
                String menuId = iterator.next();
                MainPageViewPageAdapter.Buy buy = MainPageViewPageAdapter.buyMap.get(menuId);
                lists.add(buy);
                menuList.add(buy.getMenu());
                menuNumberList.add(buy.getNumber());
                totalmoney+= buy.getNumber()*buy.getMenu().getPrice();
            }
    }


    private class ShopCartHolder extends RecyclerView.ViewHolder {
        private ImageView menuImageView;
        private TextView menuName;
        private TextView perprice;
        private TextView number;

        public ShopCartHolder(View itemView) {
            super(itemView);
            menuImageView = (ImageView) itemView.findViewById(R.id.shop_cart_imageview1);
            menuName = (TextView) itemView.findViewById(R.id.shop_cart_textview1);
            perprice = (TextView) itemView.findViewById(R.id.shop_cart_textview3);
            number = (TextView) itemView.findViewById(R.id.shop_cart_textview4);
        }

        public void bindHolder(String url, String name, int perPrice, int num) {
            if(url!=null&&url!="")
            glideUtil.attach(menuImageView).injectImageWithNull(url);

            menuName.setText(name);
            perprice.setText("￥"+perPrice+".00");
            number.setText("×"+num);
        }
    }

    private class shopcartAdapter extends RecyclerView.Adapter<ShopCartHolder> {
        private List<MainPageViewPageAdapter.Buy> mbuyList = new ArrayList<>();

        public shopcartAdapter(List<MainPageViewPageAdapter.Buy> buyList) {
            mbuyList = buyList;
        }

        @Override
        public ShopCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.shopcart_item, parent, false);
            return new ShopCartHolder(view);
        }

        @Override
        public void onBindViewHolder(ShopCartHolder holder, int position) {
            MainPageViewPageAdapter.Buy buy = mbuyList.get(position);
            holder.bindHolder(buy.getMenu().getImgUrl(), buy.getMenu().getMenuName(),
                    buy.getMenu().getPrice(), buy.getNumber());
        }

        @Override
        public int getItemCount() {
            return mbuyList.size();
        }
    }
}
