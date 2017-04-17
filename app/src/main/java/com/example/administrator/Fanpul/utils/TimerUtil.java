package com.example.administrator.Fanpul.utils;

import android.os.Handler;

/**
 * Created by Administrator on 2017/3/2.
 */

public class TimerUtil {
    public static void startTimer(int time, final TimerCallBackListener callBack){
        if(callBack != null){
            callBack.onStart();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(callBack != null){
                    callBack.onEnd();
                }
            }
        }, time);
    }

    public interface TimerCallBackListener{
        void onStart();
        void onEnd();
    }
}
