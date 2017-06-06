package com.example.administrator.Fanpul.manager;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.Fanpul.constants.Constants;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class StartActivityManager {
    public static void startActivity(Context context , Class c){
        context.startActivity(getIntent(context,c));
    }

    public static void startActivity(Context context , Class c , int flag){
        Intent i = getIntent(context,c);
        i.addFlags(flag);
        context.startActivity(i);
    }

    public static void  startActivity(Context context , Class c , BmobObject o){
        Intent i = getIntent(context , c , o);
        context.startActivity(i);
    }


    public static Intent getIntent(Context context , Class c){
        Intent intent = new Intent(context,c);
        return intent;
    }
    public static Intent getIntent(Context context , Class c ,BmobObject o){
        Intent intent = new Intent(context,c);
        intent.putExtra(Constants.INTENT_START_ACTIVITY_MANAGER_OBJECT,o);
        return intent;
    }

}
