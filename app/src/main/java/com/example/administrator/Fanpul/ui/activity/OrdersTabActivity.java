package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.ui.fragment.FragmentManager;
import com.example.administrator.Fanpul.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class OrdersTabActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"排队中", "待评论", "历史的"};
    private View mDecorView;
    private SegmentTabLayout mOrders_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_tab);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //tab
        for (String title : mTitles) {
            mFragments.add(FragmentManager.getFragment(title));
        }

        mDecorView = getWindow().getDecorView();
        mOrders_tab = ViewFindUtils.find(mDecorView, R.id.orders_tab);    //装入fragement
        init_tab_data();  //加载tab标签数据
    }

    //
    private void init_tab_data(){
        final ViewPager vp = ViewFindUtils.find(mDecorView, R.id.orders_tab_vp);
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
        vp.setCurrentItem(1);
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

    public static void startActivity(Context context){
        Intent intent = new Intent(context,OrdersTabActivity.class);
        context.startActivity(intent);
    }
}
