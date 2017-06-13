package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.IEatingDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Eating;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class EatingDaoImpl implements IEatingDao {
    private Context context;
    public EatingDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getEatingByIndex(final int index, String tableSize, final Restaurant restaurant, final OneObjectCallBack callBack) {
        String sql = "Select * from Eating where tableSize = '" + tableSize + "' Order by createdAt";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Eating>().doSQLQuery(sql, new SQLQueryListener<Eating>() {
                @Override
                public void done(BmobQueryResult<Eating> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        long time = getPreWaitTime(bmobQueryResult.getResults().get(index), restaurant);
                        callBack.Success(time);
                    } else {
                        callBack.Failed();
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    public void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName) {
        String sql = "Select * from Eating where restaurantName = '" + restaurantName + "' " +
                "and tableSize = '" + tableSize + "' and tableNumber = " + number;
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Eating>().doSQLQuery(sql, new SQLQueryListener<Eating>() {
                @Override
                public void done(BmobQueryResult<Eating> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            Eating eating = bmobQueryResult.getResults().get(0);
                            eating.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }

                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }


    public static long getPreWaitTime(Eating eating, Restaurant restaurant) {
        String date = eating.getCreatedAt();//正在吃饭的最早的人开始吃饭的时间
        int avgTime = restaurant.getAvgTime();//餐馆平均吃饭时间,单位是分钟
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = formatter.format(currentTime);
        //String theEarliest = formatter.format(date);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            Date d1 = df.parse(nowDate);
            Date d2 = df.parse(date);
            long diff = d1.getTime() - d2.getTime();

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long diffTime = hours * 60 + minutes;
            time = avgTime - diffTime;

        } catch (Exception e) {
        }
        return time;
    }

}
