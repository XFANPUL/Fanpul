package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.manager.StartActivityManager;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.CollectionRestaurantActivity;
import com.example.administrator.Fanpul.ui.activity.MyCollectionMenuActivity;
import com.example.administrator.Fanpul.ui.activity.MyEvaActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.w3c.dom.Text;

import butterknife.Bind;

public class MyFragment extends BaseFragment {
    @Bind(R.id.menu_collection)
    public TextView menu_collection;
    @Bind(R.id.restaurant_collection)
    public TextView restaurant_collection;
    @Bind(R.id.textView2)
    TextView tx;

    private static MyFragment myFragment;
    public static MyFragment getMyFragment(){
          if(myFragment == null ){
              myFragment = new MyFragment();
          }
          return myFragment;
    }
    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_my;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        menu_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollectionMenuActivity.starActivity(getActivity());
            }
        });
        restaurant_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityManager.startActivity(getActivity(),CollectionRestaurantActivity.class);
            }
        });
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyEvaActivity.startActivity(getActivity());
            }
        });
    }


}
