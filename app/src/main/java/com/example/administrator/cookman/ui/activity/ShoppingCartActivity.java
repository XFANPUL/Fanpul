package com.example.administrator.cookman.ui.activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cookman.R;

import com.example.administrator.cookman.ui.fragment.ShopcartFragment;

import org.w3c.dom.Text;


public class ShoppingCartActivity extends AppCompatActivity {
    private TextView totalmoneyText;
    private TextView totalItemNumber;
    private TextView totalMoneyText1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        totalItemNumber= (TextView) findViewById(R.id.textview_total_item);
        totalmoneyText = (TextView)findViewById(R.id.total_money_item);
        totalMoneyText1 = (TextView)findViewById(R.id.total) ;

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.shopcart_container);
        if(fragment == null){
            fragment = new ShopcartFragment();
            fm.beginTransaction().add(R.id.shopcart_container,fragment).commit();
        }

    }

    public void updateUI(float totalMoney,int size){
        totalItemNumber.setText("共"+size+"件商品");
        totalmoneyText.setText("金额:"+totalMoney);
        totalMoneyText1.setText("合计:"+totalMoney);
    }



}
