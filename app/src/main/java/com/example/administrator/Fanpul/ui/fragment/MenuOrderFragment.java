package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.CookChannelActivity;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.Fanpul.ui.component.magicindicator.MagicIndicator;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import com.example.administrator.Fanpul.ui.component.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.example.administrator.Fanpul.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/2/17.
 */

public class MenuOrderFragment extends BaseFragment implements
        ViewPager.OnPageChangeListener
{
    //private List<TB_CustomCategory> customCategoryDatas;
     private List<MenuCategory> menuCategoryList;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.magic_indicator)
    public MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    public ViewPager viewPager;

    @Bind(R.id.shop_name)
    public TextView shopName;

    @Bind(R.id.table_number)
    public TextView tableNumber;

    private CommonNavigator commonNavigator;
    private MainPageViewPageAdapter mainPageViewPageAdapter;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    private  String restaurantName;

    /********************************************************************************************/
    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_main_page;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        StatusBarUtil.setImmersiveStatusBar(getActivity());
        StatusBarUtil.setImmersiveStatusBarToolbar(toolbar, getActivity());

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        menuCategoryList = new ArrayList<>();
        if(getActivity().getIntent().getStringExtra(ZxingFragment.TAG_ZXING)!=null) {
            String[] str = getActivity().getIntent().getStringExtra(ZxingFragment.TAG_ZXING).split(" ");
            restaurantName = str[0];
            shopName.setText(str[0]);
            if(str.length==2) {
                //Pattern pattern = Pattern.compile("^[ABCD]+\\d");
                //Matcher matcher = pattern.matcher(str[1]);
                //if(matcher.matches()) {
                    tableNumber.setText("桌号:"+str[1]);
                    tableNumber.setVisibility(View.VISIBLE);
              //  }
            }
        }
        if(getActivity().getIntent().getStringExtra(OrderMenuActivity.SHOP_NAME)!=null){
            shopName.setText(getActivity().getIntent().getStringExtra(OrderMenuActivity.SHOP_NAME));
            restaurantName = getActivity().getIntent().getStringExtra(OrderMenuActivity.SHOP_NAME);
        }
        BmobUtil.queryRestaurantCategory(restaurantName, new BmobQueryCallback<MenuCategory>() {
            @Override
            public void Success(List<MenuCategory> bmobObjectList) {
                     menuCategoryList = bmobObjectList;
                     initIndicatorView();
            }

            @Override
            public void Failed() {

            }
        });


    }

    /********************************************************************************************/

    /********************************************************************************************/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        magicIndicator.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        magicIndicator.onPageScrollStateChanged(state);
    }
    /********************************************************************************************/

    /********************************************************************************************/
    @OnClick(R.id.imgv_search)
    public void onClickImgvSearch(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Search);

        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SearchFragment(), "fragment_search")
                .addToBackStack("fragment:reveal")
                .commit();
    }

   /* @OnClick(R.id.imgv_add)
    public void onClickChannelAManager(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Channel);

        CookChannelActivity.startActivity(getActivity());
    }*/
    /********************************************************************************************/

    private void initIndicatorView(){
       //customCategoryDatas = CustomCategoryManager.getInstance().getDatas();

        commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return menuCategoryList == null ? 0 : menuCategoryList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                //simplePagerTitleView.setText(customCategoryDatas.get(index).getName());
                simplePagerTitleView.setText(menuCategoryList.get(index).getCategoryName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#de9816"));//ebe4e3
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        mainPageViewPageAdapter = new MainPageViewPageAdapter(getFragmentManager(), menuCategoryList);

        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mainPageViewPageAdapter);
    }

   /* public boolean onBackPressed() {
        SearchFragment fragment = (SearchFragment)getFragmentManager().findFragmentByTag("fragment_search");
        if(fragment != null) {
            return fragment.onBackPressed();
        }
        return false;

    }*/

    public void updateChannel(){

        initIndicatorView();

    }

}
