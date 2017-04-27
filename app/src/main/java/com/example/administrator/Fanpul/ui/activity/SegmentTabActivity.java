package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.fragment.FragmentManager;
import com.example.administrator.Fanpul.ui.view.ImageCycleView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;


public class SegmentTabActivity extends BaseSwipeBackActivity {
    //图片轮播
    private ImageCycleView mAdView;
    private ArrayList<String> mImageUrl = new ArrayList<>();
    private ArrayList<String> mImageTitle = new ArrayList<>();
    private  int stype =1;  //1 横线 0 圆点
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"排队", "菜谱", "店铺", "更多"};
    private SegmentTabLayout mShopinner_tab;


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override

    protected Presenter getPresenter() {
        return null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_segment_tab;
    }
    private Restaurant restaurant;
    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        restaurant= (Restaurant) intent.getSerializableExtra(SEGMENTTAB_RESTAURANT);
        for(BmobFile bmobfile:restaurant.getBmobFileList()){
            if(!bmobfile.getFilename().startsWith("shop_more")) {
                mImageUrl.add(bmobfile.getUrl());
                mImageTitle.add(bmobfile.getFilename());
            }
        }
        //getActionBar().setTitle(restaurant.getRestaurantName());
        mAdView = (ImageCycleView) findViewById(R.id.shopinner_image_cycle_view);
        mAdView.setImageResources(mImageUrl ,mImageTitle, mAdCycleViewListener,stype);

        for (String title : mTitles) {
           // mFragments.add(FragmentManager.getInstance(title));
            mFragments.add(FragmentManager.getFragment(title));
        }
        mShopinner_tab = (SegmentTabLayout) findViewById(R.id.shopinner_tab);    //装入fragement
        init_tab_data();  //加载数据
    }

    private void init_tab_data(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(restaurant.getRestaurantName());
        final ViewPager vp = (ViewPager) findViewById(R.id.shopinner_tab_vp);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mShopinner_tab.setTabData(mTitles);
        mShopinner_tab.setOnTabSelectListener(new OnTabSelectListener() {
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
                mShopinner_tab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
    }
    @Override
    public void onBackPressed() {
        boolean success = getSupportFragmentManager().popBackStackImmediate();
        if (!success)
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

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

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            //Toast.makeText(SegmentTabActivity.this, mImageUrl.get(position)+position, Toast.LENGTH_SHORT).show();
        }
    };

 public static final String SEGMENTTAB_RESTAURANT = "com.example.administrator.Fanpul.ui.activity.SegmentTab_restaurant";
    public static void SegmentTabActivityStart(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context,SegmentTabActivity.class);
        intent.putExtra(SEGMENTTAB_RESTAURANT,restaurant);
        context.startActivity(intent);
    }
}
