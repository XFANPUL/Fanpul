package com.example.administrator.cookman.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.fragment.MainPageFragment;
import com.example.administrator.cookman.utils.ToastUtil;

public class OrderActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private MainPageFragment mainPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**********************************************************/
    @Override
    protected int getLayoutId(){
        return R.layout.activity_order;
    }

    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        mainPageFragment = new MainPageFragment();

        transaction
                .add(R.id.fragment_main_content, mainPageFragment)
                .show(mainPageFragment)
                .commit();
    }
    /**********************************************************/

    /**********************************************************/
    //private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

       super.onBackPressed();

        /*if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        ToastUtil.showToast(this, R.string.toast_msg_oncemore_exit);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CookChannelActivity.Request_Code_Channel && resultCode == CookChannelActivity.Result_Code_Channel_NoChanged){
            if(mainPageFragment != null)
                mainPageFragment.updateChannel();
        }
    }

    /**********************************************************/

    public static void startActivity(Context context){
        Intent intent = new Intent(context, OrderActivity.class);
        context.startActivity(intent);
    }
    public static final String SHOP_NAME = "com.example.administrator.cookman.ui.activity.OrderActivity.Shope_Name";
    public static Intent getIntent(Context context,String name){
        Intent intent = new Intent(context,OrderActivity.class);
        intent.putExtra(SHOP_NAME,name);
        return intent;
    }
}
