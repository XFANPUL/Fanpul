package com.example.administrator.Fanpul.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.Fanpul.IView.ISplashView;
import com.example.administrator.Fanpul.presenter.SplashPresenter;

public class SplashActivity extends Activity implements ISplashView {

    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        splashPresenter = new SplashPresenter(this, this);
        splashPresenter.initData();
    }

    @Override
    public void onSplashInitData(){
        startMainActivity();
    }

    private void startMainActivity(){
        new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message arg0) {
                 MainActivity.startActivity(SplashActivity.this);
                 finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1500);
    }

}
