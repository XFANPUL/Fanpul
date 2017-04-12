package com.example.administrator.cookman.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.cookman.R;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class OrderFormActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complete);
    }

    public static void startOrderFormActivity(Context context){
        Intent intent = new Intent(context,OrderFormActivity.class);
        context.startActivity(intent);
    }
}
