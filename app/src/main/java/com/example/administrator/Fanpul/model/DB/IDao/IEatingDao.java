package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IEatingDao {
        void getEatingByIndex(int index, String tableSize, Restaurant restaurant, OneObjectCallBack callBack);//通过index获得eating
        void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName);//通过桌号删除该桌号用餐的eating
}
