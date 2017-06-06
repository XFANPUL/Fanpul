package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.DB.OneObjectCallBack;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;


public class MenuInDetailActivity extends BaseSwipeBackActivity {
    public static final String MENU_TAG = "com.example.administrator.Fanpul.ui.activity.MenuInDetailActivity.Menu";

    public TextView detail_menu_name;
    public ImageView detail_menu_img;
    public TextView detail_product_sale;
    public TextView detail_product_price;
    public TextView detail_menu_introduction;
    public FloatingActionButton fab;
    private com.example.administrator.Fanpul.model.entity.bmobEntity.Menu menu;

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_in_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        detail_menu_name = (TextView) findViewById(R.id.product_name);
        detail_menu_img = (ImageView) findViewById(R.id.detail_menu_img);
        detail_product_sale = (TextView) findViewById(R.id.detail_product_sale);
        detail_product_price = (TextView) findViewById(R.id.detail_product_price);
        detail_menu_introduction = (TextView) findViewById(R.id.detail_menu_introduction);

        GlideUtil glideUtil = new GlideUtil();
        Intent intent = getIntent();
        menu = (com.example.administrator.Fanpul.model.entity.bmobEntity.Menu) intent.getSerializableExtra(MENU_TAG);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(menu.getMenuName());
        detail_menu_name.setText(menu.getMenuName());
        glideUtil.attach(detail_menu_img).injectImageWithNull(menu.getImgUrl());
        detail_product_price.setText(menu.getPrice() + "元/份");
        detail_product_sale.setText("月售" + menu.getSaleNum() + "份");
        detail_menu_introduction.setText(menu.getDescription());
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //收藏取消收藏可以用换图片实现 ，收藏是timg，取消收藏是uncollect
        DBConnection.judgeIsCollectionMenu("张三", menu, new OneObjectCallBack<Boolean>() {
            @Override
            public void Success(Boolean result) {
                updateUI(result);
            }

            @Override
            public void Failed() {

            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#eaeaea")));//设置背景颜色的代码，你们写后台的时候自己写 我就先不改了

    }

    public void updateUI(boolean flag) {
        if (flag) {
            fab.setImageResource(R.drawable.timg);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    DBConnection.deleteCollectionMenu("张三", menu, new OneObjectCallBack() {
                        @Override
                        public void Success(Object result) {
                            updateUI(false);
                        }

                        @Override
                        public void Failed() {

                        }
                    });
                }
            });
        } else {
            fab.setImageResource(R.drawable.uncollect);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBConnection.addCollectionMenu("张三", menu, new OneObjectCallBack() {
                        @Override
                        public void Success(Object result) {
                            updateUI(true);
                        }

                        @Override
                        public void Failed() {

                        }
                    });

                    Snackbar.make(view, "已添加到收藏夹", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_in_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void MenuInDetailActivityStart(Context context, com.example.administrator.Fanpul.model.entity.bmobEntity.Menu menu) {
        Intent intent = new Intent(context, MenuInDetailActivity.class);
        intent.putExtra(MENU_TAG, menu);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        boolean success = getSupportFragmentManager().popBackStackImmediate();
        if (!success)
            super.onBackPressed();
    }
}
