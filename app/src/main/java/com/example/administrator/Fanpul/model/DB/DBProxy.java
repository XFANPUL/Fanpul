package com.example.administrator.Fanpul.model.DB;

import android.content.Context;

import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.DB.DBHelper.DBHelper;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class DBProxy implements DBInterface {
    private DBInterface DBConnection;

    public static DBProxy proxy;

    public static DBProxy getProxy(String connection , Context context) {
        if(proxy == null){
           proxy = new DBProxy(connection , context);
        }
        return proxy;
    }

    public static void setProxy(DBProxy proxy) {
        DBProxy.proxy = proxy;
    }

    public DBProxy(String connection , Context context) {
        this.DBConnection = getDBConnection(connection ,context);
    }

    public DBInterface getDBConnection(String connection , Context context) {
        switch (connection) {
            case Constants.BMOB_CONNECTION:
                return DBHelper.getBmobDBConnection(context);
            default:
                return null;
        }
    }

    @Override
    public void queryRestaurantByBql(String bql, DBQueryCallback callback) {
        DBConnection.queryRestaurantByBql(bql, callback);
    }

    @Override
    public void queryRestaurantByName(String restaurantName, OneObjectCallBack callback) {
        DBConnection.queryRestaurantByName(restaurantName, callback);
    }

    @Override
    public void RemoveRestaurantTableNumber(String restaurantName, String tableSize, Integer tableNum, DBQueryCallback callback) {
        DBConnection.RemoveRestaurantTableNumber(restaurantName, tableSize, tableNum, callback);
    }

    @Override
    public void RemoveRestaurantTableIndex(String restaurantName, String tableSize, OneObjectCallBack oneObjectCallBack) {
        DBConnection.RemoveRestaurantTableIndex(restaurantName, tableSize, oneObjectCallBack);
    }

    @Override
    public void queryQueueByUserName(String userName, DBQueryCallback callBack) {
        DBConnection.queryQueueByUserName(userName, callBack);
    }

    @Override
    public void queryQueueByResName(String restaurantName, DBQueryCallback callback) {
        DBConnection.queryQueueByResName(restaurantName, callback);
    }

    @Override
    public void queryQueueByResNameAndTableSize(String restaurantName, String tableSize, DBQueryCallback callback) {
        DBConnection.queryQueueByResNameAndTableSize(restaurantName, tableSize, callback);
    }

    @Override
    public void updateQueue(Queue queue, Order order) {
        DBConnection.updateQueue(queue, order);
    }

    @Override
    public void updateQueueIsArrive(Queue queue, Integer tableNumber, OneObjectCallBack callBack) {
        DBConnection.updateQueueIsArrive(queue, tableNumber, callBack);
    }

    @Override
    public void getPreTableNumber(Queue queue, OneObjectCallBack callBack) {
        DBConnection.getPreTableNumber(queue, callBack);
    }

    @Override
    public void cancelQueue(Queue queue) {
        DBConnection.cancelQueue(queue);
    }

    @Override
    public void getEatingByIndex(int index, String tableSize, Restaurant restaurant, OneObjectCallBack callBack) {
        DBConnection.getEatingByIndex(index, tableSize, restaurant, callBack);
    }

    @Override
    public void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName) {
        DBConnection.deleteEatingByTableNumber(number, tableSize, restaurantName);
    }

    @Override
    public void queryOrderByUserName(String userName, Integer state, DBQueryCallback callback) {
        DBConnection.queryOrderByUserName(userName, state, callback);
    }

    @Override
    public void updateOrderEvaluate(Order order, int state) {
        DBConnection.updateOrderEvaluate(order, state);
    }

    @Override
    public void queryRestaurantCategory(String restaurantName, DBQueryCallback callback) {
        DBConnection.queryRestaurantCategory(restaurantName, callback);
    }

    @Override
    public void queryMenuByCategory(MenuCategory menuCategory, DBQueryCallback callback) {
        DBConnection.queryMenuByCategory(menuCategory, callback);
    }

    @Override
    public void queryCollectionMenubyUserName(String userName, DBQueryCallback callback) {
        DBConnection.queryCollectionMenubyUserName(userName, callback);
    }

    @Override
    public void judgeIsCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack) {
        DBConnection.judgeIsCollectionMenu(userName, menu, callBack);
    }

    @Override
    public void addCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack) {
        DBConnection.addCollectionMenu(userName, menu, callBack);
    }

    @Override
    public void deleteCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack) {
        DBConnection.deleteCollectionMenu(userName, menu, callBack);
    }

    @Override
    public void judgeIsCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callback) {
        DBConnection.judgeIsCollectionRestaurant(userName, restaurant, callback);
    }

    @Override
    public void operationCollectionRestaurant(String operation, String userName, Restaurant restaurant, OneObjectCallBack callBack) {
        DBConnection.operationCollectionRestaurant(operation, userName, restaurant, callBack);
    }

    @Override
    public void getCollectionRestaurant(String userName, DBQueryCallback callback) {
        DBConnection.getCollectionRestaurant(userName, callback);
    }

    @Override
    public void removeCollectionRestaurant(OneObjectCallBack callBack, CollectionRestaurant coll) {
        DBConnection.removeCollectionRestaurant(callBack, coll);
    }

    @Override
    public void deleteCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack, CollectionRestaurant coll) {
        DBConnection.deleteCollectionRestaurant(userName, restaurant, callBack, coll);
    }

    @Override
    public void updateCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack, CollectionRestaurant coll) {
        DBConnection.updateCollectionRestaurant(userName, restaurant, callBack, coll);
    }

    @Override
    public void addCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack) {
        DBConnection.addCollectionRestaurant(userName, restaurant, callBack);
    }

    @Override
    public void getEvaluateRestaurant(Restaurant restaurant, DBQueryCallback callback) {
        DBConnection.getEvaluateRestaurant(restaurant, callback);
    }

    @Override
    public void getEvaluateRestaurantByUserName(String userName, DBQueryCallback callback) {
        DBConnection.getEvaluateRestaurantByUserName(userName, callback);
    }
}
