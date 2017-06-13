package com.example.administrator.Fanpul.ui.activity;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.RestaurantService;
import com.example.administrator.Fanpul.ui.fragment.HomeFragment;
import com.example.administrator.Fanpul.ui.fragment.MyFragment;
import com.example.administrator.Fanpul.ui.fragment.OrderDetailFragment;
import com.example.administrator.Fanpul.ui.fragment.SearchFragment;
import com.example.administrator.Fanpul.ui.fragment.ZxingFragment;
import com.example.administrator.Fanpul.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {
    @Bind(R.id.main_bottom_tabs)
    RadioGroup group;
    @Bind(R.id.main_home)
    RadioButton main_home;
    @Bind(R.id.main_tuan)
    RadioButton main_tuan_radio;
    private FragmentManager fragmentManager;
    private HomeFragment home;
    private MyFragment my;
    private OrderDetailFragment orderDetailFragment;
    private ZxingFragment zxingFragment;
    private long exitTime = 0;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.showToast(MainActivity.this,getString(R.string.toast_msg_connect));
            RestaurantService.RestaurantBinder binder = (RestaurantService.RestaurantBinder)service;
            RestaurantService restaurantService=binder.getService();
            RestaurantService.setRestaurantService(restaurantService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            RestaurantService.setRestaurantService(null);
            ToastUtil.showToast(MainActivity.this,getString(R.string.toast_msg_disconnect));
        }
    };


    @Override
    protected void onResume() {
        if (group.getCheckedRadioButtonId() == R.id.main_tuan) {
            if (main_home != null) {
                main_home.setChecked(true);
                main_tuan_radio.setChecked(false);
                changeFragment(0);
            }
        }
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fanpul);
        Bmob.initialize(this, getString(R.string.appid));
        ButterKnife.bind(this);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
               != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
         //启动后台服务，一直监听数据库桌号变化
        bindService(RestaurantService.newIntent(this),serviceConnection, Context.BIND_AUTO_CREATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            home = (HomeFragment) fragmentManager.findFragmentByTag("0");
            zxingFragment = (ZxingFragment) fragmentManager.findFragmentByTag("1");
            orderDetailFragment = (OrderDetailFragment) fragmentManager.findFragmentByTag("2");
            my = (MyFragment) fragmentManager.findFragmentByTag("3");
        }
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        changeFragment(0);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                changeFragment(0);
                break;
            case R.id.main_tuan:
                Intent intent = new Intent(MainActivity.this, ZxingActivity.class);
                startActivityForResult(intent, ZxingFragment.REQUEST_CODE);
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

    public static final String RES_NAME = "com.example.administrator.Fanpul.ui.activity.MainActivity.RES_NAME";
    public static final String TABLE_SIZE = "com.example.administrator.Fanpul.ui.activity.MainActivity.TABLE_SIZE";
    public static final String TABLE_NUMBER = "com.example.administrator.Fanpul.ui.activity.MainActivity.TABLE_NUMBER";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == ZxingFragment.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    final Intent intent = new Intent(MainActivity.this, OrderMenuActivity.class);
                    if (result.split(" ").length == 2) {
                        String tableSizeAndNumber = result.split(" ")[1];
                        if (tableSizeAndNumber.matches("^[ABC]\\d*$")) {
                            final String tableSize = tableSizeAndNumber.substring(0, 1);
                            final Integer tableNumber = Integer.parseInt(tableSizeAndNumber.substring(1, tableSizeAndNumber.length()));
                            final String restaurantName = result.split(" ")[0];
                            DBProxy.proxy.RemoveRestaurantTableNumber(restaurantName, tableSize, tableNumber, new DBQueryCallback<Restaurant>() {
                                @Override
                                public void Success(List<Restaurant> bmobObjectList) {
                                    intent.putExtra(RES_NAME, restaurantName);
                                    intent.putExtra(TABLE_SIZE,tableSize);
                                    intent.putExtra(TABLE_NUMBER,tableNumber);
                                    startActivity(intent);
                                }

                                @Override
                                public void Failed() {
                                    showDialog();
                                }
                            });
                        }
                    } else {
                        showDialog();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast(MainActivity.this,getString(R.string.toast_msg_fail_QRCode));
                }
            }
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("扫码失败");
        builder.setMessage("无法识别该二维码,请重新扫描");
        builder.create().show();
    }

    public void changeFragment(int index) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 0:
                if (home == null) {
                    home = new HomeFragment();
                    beginTransaction.add(R.id.main_content, home, "0");
                } else {
                    beginTransaction.show(home);
                }
                break;
            case 1:
                if (zxingFragment == null) {
                    zxingFragment = new ZxingFragment();
                    beginTransaction.add(R.id.main_content, zxingFragment, "1");
                } else {
                    beginTransaction.show(zxingFragment);
                }
                break;
            case 2:
                if (orderDetailFragment == null) {
                    orderDetailFragment = new OrderDetailFragment();
                    beginTransaction.add(R.id.main_content, orderDetailFragment, "2");
                } else {
                    beginTransaction.show(orderDetailFragment);
                }
                break;
            case 3:
                if (my == null) {
                    my = new MyFragment();
                    beginTransaction.add(R.id.main_content, my, "3");
                } else {
                    beginTransaction.show(my);
                }
                break;

            default:
                break;
        }
        beginTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (home != null)
            transaction.hide(home);
        if (zxingFragment != null)
            transaction.hide(zxingFragment);
        if (orderDetailFragment != null)
            transaction.hide(orderDetailFragment);
        if (my != null)
            transaction.hide(my);
    }

    @Override
    public void onBackPressed() {
        SearchFragment fragment = (SearchFragment)getSupportFragmentManager().findFragmentByTag("fragment_search");
        if(fragment != null) {
            fragment.onBackPressed();
        }
       else
         exit();
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToast(getApplicationContext(),getString(R.string.toast_exit));

            exitTime = System.currentTimeMillis();
        } else {
            finish();
            MainActivity.this.unbindService(serviceConnection);
        }
    }
    @Override
    public void finish() {
        super.finish();
    }

}
