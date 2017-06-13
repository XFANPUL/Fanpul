package com.example.administrator.Fanpul.ui.activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.manager.StartActivityManager;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public class OrderFormActivity extends AppCompatActivity {
    private Button btnOrderEvaluate;
    private Button btnSeeOrder;
    private Button btnReturn;
    private Order order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complete);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnOrderEvaluate = (Button) findViewById(R.id.btnOrderEvaluate);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT);
        btnOrderEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersTabActivity.startActivity(OrderFormActivity.this, 1);
            }
        });
        btnSeeOrder = (Button) findViewById(R.id.all_order_but);
        btnSeeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeOrderDetailActivity.startActivity(OrderFormActivity.this, order);
            }
        });

        btnReturn = (Button) findViewById(R.id.btorder_complete_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              StartActivityManager.startActivity(OrderFormActivity.this,MainActivity.class,Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });


        if (order.getEvaluateState() == 2) {
            btnOrderEvaluate.setVisibility(View.GONE);
        }
    }

}
