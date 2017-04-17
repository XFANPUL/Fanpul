package com.example.administrator.Fanpul.ui.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 * 生成二维码示例
 */
public class MadeCodeActivity extends Activity implements View.OnClickListener {
    private EditText code_edt;
    private Button made_code;
    private ImageView result_iv;
    private CheckBox logo_cb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.code_edt = (EditText) findViewById(R.id.code_edt);
        this.made_code = (Button) findViewById(R.id.made_code);
        this.result_iv = (ImageView) findViewById(R.id.result_iv);
        logo_cb = (CheckBox) findViewById(R.id.logo_cb);
        this.made_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.made_code) {
            String content = code_edt.getText().toString().trim();
            boolean isAddLogo = logo_cb.isChecked();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_LONG).show();
                return;
            }
            madeCode(content, isAddLogo);
        }
    }

    /**
     * 生成二维码
     *
     * @param content   要生成图片的文字内容
     * @param isAddLogo 是否在二维码中添加LOGO图片
     */
    private void madeCode(String content, boolean isAddLogo) {
        Bitmap bitmapPath;
        if (!isAddLogo) {//不带LOGO
            bitmapPath = CodeUtils.createImage(content, 300, 300,null);
        } else {//带Logo，R.mipmap.ic_launcher就是LOGO对应图片，自行添加
            bitmapPath = CodeUtils.createImage(content, 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        }

            result_iv.setImageBitmap(bitmapPath);

    }
}
