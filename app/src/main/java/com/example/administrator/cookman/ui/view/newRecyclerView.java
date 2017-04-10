package com.example.administrator.cookman.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/4/3 0003.
 */

public class newRecyclerView extends RecyclerView {
    public newRecyclerView(Context context) {
        super(context);
    }

    public newRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public newRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
