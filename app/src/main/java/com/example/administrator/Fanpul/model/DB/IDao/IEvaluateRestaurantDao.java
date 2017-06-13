package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IEvaluateRestaurantDao {

    void getEvaluateRestaurant(Restaurant restaurant, DBQueryCallback callback);//通过店铺得到用户对店铺的评价
    void getEvaluateRestaurantByUserName(String userName, DBQueryCallback callback);//通过用户名得到用户的评价
}
