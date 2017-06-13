package com.example.administrator.Fanpul.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.Fanpul.ui.fragment.MenuOrderFragment;

public class OrderMenuActivity extends BaseSwipeBackActivity {


    private FragmentManager fragmentManager;
    private MenuOrderFragment menuOrderFragment;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected Presenter getPresenter(){
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        menuOrderFragment = new MenuOrderFragment();

        transaction
                .add(R.id.fragment_main_content, menuOrderFragment)
                .show(menuOrderFragment)
                .commit();
    }
    /**********************************************************/

    /**********************************************************/
    //private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        MainPageViewPageAdapter.buyMap.clear();
       super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        MainPageViewPageAdapter.buyMap.clear();
        super.onDestroy();
    }

    /**********************************************************/

    public static void startActivity(Context context){
        Intent intent = new Intent(context, OrderMenuActivity.class);
        context.startActivity(intent);
    }

}
