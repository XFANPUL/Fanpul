package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.fragment.FragmentManager;
import com.example.administrator.Fanpul.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.Bind;

public class OrdersTabActivity extends BaseSwipeBackActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"排队中", "待评论", "历史的"};

    @Bind(R.id.orders_tab)
    public SegmentTabLayout mOrders_tab;
    private int startTab;
    public static final String START = "com.example.administrator.Fanpul.ui.activity.OrdersTabActivity.START";
    @Override
    protected Presenter getPresenter() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_orders_tab;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("饭谱");
        for (String title : mTitles) {
            mFragments.add(FragmentManager.getFragment(title));
        }

        init_tab_data();  //加载tab标签数据
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //
    private void init_tab_data(){
        final ViewPager vp = (ViewPager)findViewById(R.id.orders_tab_vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mOrders_tab.setTabData(mTitles);
        mOrders_tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mOrders_tab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startTab = getIntent().getIntExtra(START,0);
        vp.setCurrentItem(startTab);
    }

    //tab的fragment 适配器
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    public static void startActivity(Context context,int start){
        Intent intent = new Intent(context,OrdersTabActivity.class);
        intent.putExtra(START,start);
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context,int start){
        Intent intent = new Intent(context,OrdersTabActivity.class);
        intent.putExtra(START,start);
        return  intent;
    }
}
