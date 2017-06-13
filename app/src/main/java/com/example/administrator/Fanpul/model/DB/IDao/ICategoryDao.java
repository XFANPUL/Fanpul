package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface ICategoryDao {
    void queryRestaurantCategory(String restaurantName, DBQueryCallback callback); //通过店铺名查找菜单类别
}
