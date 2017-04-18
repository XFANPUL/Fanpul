package com.example.administrator.Fanpul.ui.activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;

import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.ui.fragment.ShopcartFragment;

import butterknife.Bind;


public class ShoppingCartActivity extends AppCompatActivity {
    private TextView totalmoneyText;
    private TextView totalItemNumber;
    private TextView totalMoneyText1;
    private EditText editText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        totalItemNumber= (TextView) findViewById(R.id.textview_total_item);
        totalmoneyText = (TextView)findViewById(R.id.total_money_item);
        totalMoneyText1 = (TextView)findViewById(R.id.total) ;
        editText = (EditText)findViewById(R.id.editText) ;

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.shopcart_container);
        if(fragment == null){
            fragment = new ShopcartFragment();
            fm.beginTransaction().add(R.id.shopcart_container,fragment).commit();
        }

        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setGravity(Gravity.TOP);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);
        editText.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            temp = s;
        }
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
        }

        public void afterTextChanged(Editable s) {
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
            if (temp.length() > 30) {
                Toast.makeText(ShoppingCartActivity.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                editText.setText(s);
                editText.setSelection(tempSelection);
            }
        }
    };
    public void updateUI(float totalMoney,int size){
        totalItemNumber.setText("共"+size+"件商品");
        totalmoneyText.setText("金额:"+totalMoney);
        totalMoneyText1.setText("合计:"+totalMoney);
    }

}
