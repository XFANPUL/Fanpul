package com.example.administrator.cookman.model.bmob;

import android.util.Log;

import com.example.administrator.cookman.model.entity.bmobEntity.Restaurant;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class BmobUtil {
    public  static void queryBmobObject(String bql, final BmobQueryCallback callback) {
        Log.i("BQL", bql);
        new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
            @Override
            public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                if(e==null){
                   // Log.i("SucSSSS",bmobQueryResult.getResults().toString());
                    callback.Success(bmobQueryResult.getResults());
                }else{
                    Log.i("Failed","Fai");
                }
            }
        });
       /* new BmobQuery().doSQLQuery(bql,new SQLQueryListener() {
            @Override
            public void done(BmobQueryResult bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<BmobObject> list = bmobQueryResult.getResults();
                    callback.Success(list);
                    Log.i("queryObjS", "查询数据成功");
                } else {
                    Log.i("queryObjF", "查询数据失败");
                }

            }
        });*/
    }
}
