package com.example.administrator.Fanpul.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.ui.activity.MadeCodeActivity;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.utils.ToastUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/3 0003.
 */

public class ZxingFragment extends Fragment {
    public static final  String TAG_ZXING ="com.example.administrator.cookman.ui.fragment.ZxingFragment.Result";
    public static final int REQUEST_CODE = 111;
    @Bind(R.id.btn)
    public Button btn;
    @Bind(R.id.textView)
    public TextView mTextView;
    @Bind(R.id.activity_main_button)
    public Button mButton;

    protected int getLayoutId() {
        return R.layout.zxing_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                   // mTextView.setText(result);
                    Intent intent = new Intent(getActivity(), OrderMenuActivity.class);
                    intent.putExtra(TAG_ZXING,result);
                    startActivity(intent);
                    // Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT);
                    //用默认浏览器打开扫描得到的地址
                    //  Intent intent = new Intent();
                    // intent.setAction("android.intent.action.VIEW");
                    //  Uri content_url = Uri.parse(result.toString());
                    // intent.setData(content_url);
                    // startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {

                    ToastUtil.showToast(getActivity(),getString(R.string.toast_msg_fail_QRCode));

                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MadeCodeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
