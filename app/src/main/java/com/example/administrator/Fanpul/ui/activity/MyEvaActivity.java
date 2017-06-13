package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.MyEvaluateAdapter;
import java.util.List;

import butterknife.Bind;


public class MyEvaActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.my_head_login)
    ImageView my_head_login; //用户的登录头像
    @Bind(R.id.my_login_username)
    TextView my_login_username;  //用户名
    @Bind(R.id.my_member_lv)
    TextView my_member_lv;  //总共有几条评论
    @Bind(R.id.list_my_eva)
    RecyclerView rcy_my_eva;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyEvaluateAdapter myEvaluateAdapter;

    public static void startActivity(Context context){
        Intent i = new Intent(context,MyEvaActivity.class);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_eva;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        my_login_username.setText(getString(R.string.user_name));
        rcy_my_eva.setLayoutManager(new LinearLayoutManager(this));
        myEvaluateAdapter = new MyEvaluateAdapter(this , R.layout.match_my_eva_item , null);
        rcy_my_eva.setAdapter(myEvaluateAdapter);
        my_member_lv.setText(getString(R.string.total_comment,0));
        swipeRefreshLayout.setOnRefreshListener(this);
        updateUI();
    }

    public void updateUI(){
        DBProxy.proxy .getEvaluateRestaurantByUserName(getString(R.string.user_name), new DBQueryCallback<EvaluateRestaurant>() {
            @Override
            public void Success(List<EvaluateRestaurant> bmobObjectList) {
                my_member_lv.setText(getString(R.string.total_comment,bmobObjectList.size()));
                myEvaluateAdapter.setmDatas(bmobObjectList);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void Failed() {

            }
        });
    }

    @Override
    public void onRefresh() {
         updateUI();
    }

}



