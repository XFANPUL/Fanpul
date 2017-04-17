package com.example.administrator.Fanpul.ui.activity;


import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.ui.fragment.HomeFragment;
import com.example.administrator.Fanpul.ui.fragment.MyFragment;
import com.example.administrator.Fanpul.ui.fragment.ZxingFragment;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import java.util.List;

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
    private MyFragment my;
    private ZxingFragment zxingFragment;
    private long exitTime=0;
    private RadioButton main_tuan_radio;

    @Override
    protected void onResume() {
        if(group.getCheckedRadioButtonId()==R.id.main_tuan){
            if(main_home!=null) {
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

        Bmob.initialize(this, MainActivity.APPLICATIONID);

        group=(RadioGroup)findViewById(R.id.main_bottom_tabs);
        main_home = (RadioButton)findViewById(R.id.main_home);
        main_tuan_radio = (RadioButton)findViewById(R.id.main_tuan);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ViewUtils.inject(this);
        fragmentManager=getSupportFragmentManager();
        if(savedInstanceState!=null){
            home=(HomeFragment)fragmentManager.findFragmentByTag("0");
            zxingFragment = (ZxingFragment)fragmentManager.findFragmentByTag("1");
            my=(MyFragment)fragmentManager.findFragmentByTag("3");
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
                //changeFragment(1);
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ZxingFragment.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    // mTextView.setText(result);
                    final Intent intent = new Intent(MainActivity.this, OrderMenuActivity.class);
                    if(result.split(" ").length==2) {
                        String sql = "select * from Restaurant where restaurantName = '" + result.split(" ")[0] + "'";
                        BmobUtil.queryBmobObject(sql, new BmobQueryCallback() {
                            @Override
                            public void Success(List bmobObjectList) {
                                intent.putExtra(ZxingFragment.TAG_ZXING, result);
                                startActivity(intent);
                            }

                            @Override
                            public void Failed() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("扫码失败");
                                builder.setMessage("无法识别该二维码,请重新扫描1");
                                builder.create().show();
                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("扫码失败");
                        builder.setMessage("无法识别该二维码,请重新扫描");
                        builder.create().show();
                    }
                    // Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT);
                    //用默认浏览器打开扫描得到的地址
                    //  Intent intent = new Intent();
                    // intent.setAction("android.intent.action.VIEW");
                    //  Uri content_url = Uri.parse(result.toString());
                    // intent.setData(content_url);
                    // startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
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
                if(my==null){
                    my=new MyFragment();
                    beginTransaction.add(R.id.main_content,my,"3");
                }else{
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

       if(zxingFragment!=null)
            transaction.hide(zxingFragment);
        if (my != null)
            transaction.hide(my);

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
