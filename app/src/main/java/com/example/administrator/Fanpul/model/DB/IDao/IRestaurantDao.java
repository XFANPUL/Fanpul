package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IRestaurantDao {
    void queryRestaurantByBql(String bql, DBQueryCallback callback);//通过sql语句查询
    void queryRestaurantByName(String restaurantName, OneObjectCallBack callback);//通过店铺名查询
    void RemoveRestaurantTableNumber(String restaurantName, String tableSize,
                                     Integer tableNum, DBQueryCallback DBQueryCallback);//通过固定桌号（扫描二维码形式)获得桌号并锁定桌号
    void RemoveRestaurantTableIndex(String restaurantName, String tableSize,
                                    OneObjectCallBack oneObjectCallBack); //获得随机桌号并锁定桌号
}
