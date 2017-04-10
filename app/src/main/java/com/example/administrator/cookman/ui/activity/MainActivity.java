package com.example.administrator.cookman.ui.activity;


import com.example.administrator.cookman.R;
import com.example.administrator.cookman.ui.fragment.HomeFragment;
import com.example.administrator.cookman.ui.fragment.ZxingFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{
    //@ViewInject(R.id.main_bottom_tabs)
    public static final String APPLICATIONID = "3b60c6eb37caced9f0023a6515b2d8a5";
    private RadioGroup group;
    //@ViewInject(R.id.main_home)
    private RadioButton main_home;
    private FragmentManager fragmentManager;
    private HomeFragment home;
    private ZxingFragment zxingFragment;
    private long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fanpul);

        Bmob.initialize(this, MainActivity.APPLICATIONID);

        group=(RadioGroup)findViewById(R.id.main_bottom_tabs);
        main_home = (RadioButton)findViewById(R.id.main_home);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ViewUtils.inject(this);
        fragmentManager=getSupportFragmentManager();
        if(savedInstanceState!=null){
            home=(HomeFragment)fragmentManager.findFragmentByTag("0");
            zxingFragment = (ZxingFragment)fragmentManager.findFragmentByTag("1");
        }
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        changeFragment(0);
    }

    @Override
    protected void onDestroy() {
        Log.i("MMss","Destory");
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                changeFragment(0);
                break;
            case R.id.main_tuan:
                changeFragment(1);
                break;
            case R.id.main_search:
                changeFragment(2);
                break;
            case R.id.main_my:
                changeFragment(3);
                break;
            default:
                break;
        }
    }
    public void changeFragment(int index)
    {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 0:
                if(home==null){
                    home=new HomeFragment();
                    beginTransaction.add(R.id.main_content,	home,"0");
                }else{
                    beginTransaction.show(home);
                }
                break;
            case 1:
                if(zxingFragment==null) {
                    zxingFragment = new ZxingFragment();
                    beginTransaction.add(R.id.main_content, zxingFragment,"1");
                }
                else{
                    beginTransaction.show(zxingFragment);
                }
                break;
            case 2:
                break;
            case 3:
                break;

            default:
                break;
        }
        beginTransaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (home != null)
            transaction.hide(home);

       if(zxingFragment!=null)
            transaction.hide(zxingFragment);

    }
    @Override
    public void onBackPressed() {
        exit();
    }
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
    @Override
    public void finish() {
        super.finish();
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
