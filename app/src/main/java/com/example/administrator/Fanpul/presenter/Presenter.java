package com.example.administrator.Fanpul.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class Presenter {

    protected Context context;

    public Presenter(Context context){
        this.context = context;
    }

    public abstract void destroy();
}
