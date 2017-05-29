package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyEvaActivity extends AppCompatActivity {

    private ImageView my_head_login; //用户的登录头像
    private TextView my_login_username;  //用户名
    private TextView my_member_lv;  //总共有几条评论
    private ListView list_my_eva;

    private List<Map<String, Object>> list  ;  //ListView
    private Map<String,Object> map ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eva);

        my_head_login = (ImageView)findViewById(R.id.my_head_login);
        my_login_username = (TextView)findViewById(R.id.my_login_username);
        my_member_lv= (TextView)findViewById(R.id.my_member_lv);
        list_my_eva =(ListView)findViewById(R.id.list_my_eva);

        list =this.getMenuList();

        MyEvaListAdapter me_adapter = new MyEvaListAdapter(MyEvaActivity.this,list);
        list_my_eva.setAdapter(me_adapter);


    }

    public List<Map<String, Object>> getMenuList(){

        list = new ArrayList<Map<String, Object>>();
        //从数据库读
        String[] names={"旺德福","啦啦啦","啦啦啦","啦啦啦","啦啦啦"};
        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("store_name", names[i]);
            list.add(map);
        }
        return list;
    }
}

class MyEvaListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> list;


    public MyEvaListAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        HolderViewMyEva holderViewMyEva = null;

        if (view == null) {
            holderViewMyEva = new HolderViewMyEva();
            view = LayoutInflater.from(context).inflate(R.layout.match_my_eva_item, parent, false);
            ViewUtils.inject(holderViewMyEva, view);
            view.setTag(holderViewMyEva);
        } else {
            holderViewMyEva = (HolderViewMyEva) view.getTag();
        }

        holderViewMyEva.store_name.setText((String) list.get(position).get("store_name"));

        return view;
    }
}

class HolderViewMyEva{
    @ViewInject(R.id.store_name)    //店铺名
            TextView store_name;


    @ViewInject(R.id.is_anonymity)    //是否匿名评价
            TextView is_anonymity;


    @ViewInject(R.id.left_img_shop_head)    //左边的店铺头像
            ImageView left_img_shop_head;

    @ViewInject(R.id.my_name)    //我的名字
            TextView my_name;

    @ViewInject(R.id.eva_date)    //评价日期
            TextView eva_date;

    @ViewInject(R.id.ratBarSum)    //店铺总评
            RatingBar ratBarSum;

    @ViewInject(R.id.eva_rating_menu)    //c菜品
            TextView eva_rating_menu;

    @ViewInject(R.id.eva_rating_speed)    //上菜速度
            TextView eva_rating_speed;

    @ViewInject(R.id.eva_rating_server)    //服务质量
            TextView eva_rating_server;

    @ViewInject(R.id.et_feedback)    //用户评价
            TextView et_feedback;

    @ViewInject(R.id.tv_price)    //消费金额
            TextView tv_price;
}
