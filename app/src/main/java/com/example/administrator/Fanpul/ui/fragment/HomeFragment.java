package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.adapter.RestaurantItemAdapter;
import com.example.administrator.Fanpul.ui.view.ImageCycleView;
import com.example.administrator.Fanpul.ui.view.WrapContentHeightViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zaaach.citypicker.CityPickerActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements DBQueryCallback<Restaurant> ,SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.index_home_viewpager)
    WrapContentHeightViewPager viewPager;
    @Bind(R.id.index_home_rb1)
    RadioButton rb1;
    @Bind(R.id.index_home_rb2)
    RadioButton rb2;
    @Bind(R.id.index_home_rb3)
    RadioButton rb3;
    @Bind(R.id.storelist)
    RecyclerView recyclerView;//店铺的recycleList
    @Bind(R.id.ad_view)
    ImageCycleView imagecycleview;
    @Bind(R.id.home_top_city)
    TextView cityView;
    @Bind(R.id.id_swipe_ly)
    SwipeRefreshLayout swipeRefreshLayout;

    private GridView gridView1;
    private GridView gridView2;
    private GridView gridView3;
    private RestaurantItemAdapter restaurantItemAdapter;
    private ArrayList<String> mImageUrl;
    private ArrayList<String> mImageTitle;

    @Override
    public void onStart() {
        super.onStart();
        initViewPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_home, container, false);
        ViewUtils.inject(this, view);
        ButterKnife.bind(this, view);
        initCity();
        initGridView();
        initStoreList();
        initImageCycleView();
        updateUI();
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    private void initImageCycleView() {
        mImageUrl = new ArrayList<>();
        mImageUrl.add(Constants.imageUrl1);
        mImageUrl.add(Constants.imageUrl2);
        mImageUrl.add(Constants.imageUrl3);
        int stype = 1;
        mImageTitle = new ArrayList<>();
        mImageTitle.add(Constants.imageTitle1);
        mImageTitle.add(Constants.imageTitle2);
        mImageTitle.add(Constants.imageTitle3);
        imagecycleview.setImageResources(mImageUrl, mImageTitle, mAdCycleViewListener, stype);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            //Toast.makeText(getActivity(), mImageUrl.get(position)+position, Toast.LENGTH_SHORT).show();
        }
    };

    private void initStoreList() {
        restaurantItemAdapter = new RestaurantItemAdapter(getActivity(),R.layout.match_storelist_item , null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(restaurantItemAdapter);
    }

    @Override
    public void Success(List<Restaurant> bmobObjectList) {
        restaurantItemAdapter.setmDatas(bmobObjectList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void Failed() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.tx_search_restaurant)
    public void onClickImgvSearch() {
        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SearchFragment(), "fragment_search")
                .addToBackStack("fragment:reveal")
                .commit();
    }

    public void updateUI(){
        String bql = "select * from Restaurant";
        DBProxy.proxy.queryRestaurantByBql(bql, this);
    }
    @Override
    public void onRefresh() {
        updateUI();
    }

    public class GridViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int page;

        public GridViewAdapter(Context context, int page) {
            super();
            this.inflater = LayoutInflater.from(context);
            this.page = page;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (page != -1) {
                return 8;
            } else {
                return Constants.navSort.length;
            }
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.index_home_grid_item, null);
                ViewUtils.inject(vh, convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.iv_navsort.setImageResource(Constants.navSortImages[position + 8 * page]);
            vh.tv_navsort.setText(Constants.navSort[position + 8 * page]);
            if (position == 8 - 1 && page == 2) {
                vh.iv_navsort.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                    }
                });
            } else {
                vh.iv_navsort.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                    }
                });
            }
            return convertView;
        }
    }

    private static final int REQUEST_CODE_PICK_CITY = 233;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                cityView.setText(city);
            }
        }
    }

    private void initCity() {
        cityView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });
    }

    private class ViewHolder {
        @ViewInject(R.id.index_home_iv_navsort)
        ImageView iv_navsort;
        @ViewInject(R.id.index_home_tv_navsort)
        TextView tv_navsort;
    }

    private void initGridView() {
        gridView1 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
        gridView2 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
        gridView3 = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
        gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
    }

    private void initViewPager() {
        List<View> list = new ArrayList<View>();
        list.add(gridView1);
        list.add(gridView2);
        list.add(gridView3);
        viewPager.setAdapter(new MyViewPagerAdapter(list));
        rb1.setChecked(true);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rb1.setChecked(true);
                } else if (position == 1) {
                    rb2.setChecked(true);
                } else if (position == 2) {
                    rb3.setChecked(true);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    private class MyViewPagerAdapter extends PagerAdapter {
        List<View> list;

        public MyViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "1";
        }
    }

}
