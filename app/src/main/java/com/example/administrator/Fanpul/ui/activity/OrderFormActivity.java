package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.Fanpul.R;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class OrderFormActivity extends AppCompatActivity {
    private Button btnOrderEvaluate ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complete);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnOrderEvaluate = (Button) findViewById(R.id.btnOrderEvaluate);
        btnOrderEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersTabActivity.startActivity(OrderFormActivity.this);
            }
        });
    }

    public static void startOrderFormActivity(Context context){
        Intent intent = new Intent(context,OrderFormActivity.class);
        context.startActivity(intent);
    }
}
