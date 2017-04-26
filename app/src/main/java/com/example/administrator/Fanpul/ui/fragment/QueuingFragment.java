package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class QueuingFragment extends BaseFragment {  //排队中的fragment
    @Bind(R.id.orders_list)
    ListView orders_listView;  //订单的通用ListView
    private List<Map<String, Object>> list ;  //ListView 装数据用的
    private Map<String,Object> map ;
    private String mTitle;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
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
        list = this.get_orders_inline_data();
        OrdersListAdapter od_adapter = new OrdersListAdapter(getActivity(), list, mTitle);//新建适配器
        orders_listView.setAdapter(od_adapter);//绑定适配器
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


}
