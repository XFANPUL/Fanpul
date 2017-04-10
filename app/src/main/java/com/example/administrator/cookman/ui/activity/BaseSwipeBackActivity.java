package com.example.administrator.cookman.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.administrator.cookman.presenter.Presenter;
import com.example.administrator.cookman.ui.component.swipebacklayout.app.SwipeBackActivity;
import com.example.administrator.cookman.ui.component.swipebacklayout.app.SwipeBackActivityBase;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class BaseSwipeBackActivity extends SwipeBackActivity implements SwipeBackActivityBase {
    protected Context context;
    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutId());
        context = this;
        ButterKnife.bind(this);

        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        if(presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        if(presenter != null){
            presenter.destroy();
        }
    }

    public Context getContext(){
        return context;
    }

    protected abstract Presenter getPresenter();
    protected abstract int getLayoutId();
    protected abstract void init(Bundle savedInstanceState);

}
