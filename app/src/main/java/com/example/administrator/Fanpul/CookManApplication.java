package com.example.administrator.Fanpul;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.utils.Crash;
import com.example.administrator.Fanpul.utils.Logger.LogLevel;
import com.example.administrator.Fanpul.utils.Logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.DisplayUtil;

import org.litepal.LitePalApplication;

public class CookManApplication extends LitePalApplication   {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initDisplayOpinion();
        Crash crashHandler = Crash.getInstance();
        crashHandler.init(getApplicationContext());
        mContext = getApplicationContext();

        initDB(Constants.BMOB_CONNECTION);

        //Logger设置
        if(BuildConfig.DEBUG) {
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.FULL);
        }
        else{
            Logger.init(Constants.Common_Tag).logLevel(LogLevel.NONE);
        }

        //腾讯Bugly
        CrashReport.initCrashReport(getApplicationContext());

    }

    public void initDB(String connection){
        DBProxy.getProxy(connection , getContext());
    }

    public static Context getContext() {
        return mContext;
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }

}
