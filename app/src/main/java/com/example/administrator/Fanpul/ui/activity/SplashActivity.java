package com.example.administrator.Fanpul.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.example.administrator.Fanpul.manager.StartActivityManager;

public class SplashActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startMainActivity();
    }


    private void startMainActivity(){
        new Handler(new Handler.Callback(){
            @Override
            public boolean handleMessage(Message arg0) {
                 StartActivityManager.startActivity(SplashActivity.this,MainActivity.class);
                 finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1500);
    }

}
