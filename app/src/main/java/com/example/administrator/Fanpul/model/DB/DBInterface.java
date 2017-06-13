package com.example.administrator.Fanpul.model.DB;

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

public interface DBInterface {
    void queryRestaurantByBql(String bql,  DBQueryCallback callback);//通过sql语句查询
    void queryRestaurantByName(String restaurantName,  OneObjectCallBack callback);//通过店铺名查询
    void RemoveRestaurantTableNumber(String restaurantName, String tableSize,
                                      Integer tableNum,  DBQueryCallback DBQueryCallback);//通过固定桌号（扫描二维码形式)获得桌号并锁定桌号
    void RemoveRestaurantTableIndex(String restaurantName,  String tableSize,
                                     OneObjectCallBack oneObjectCallBack); //获得随机桌号并锁定桌号


    void queryQueueByUserName(String userName,  DBQueryCallback callBack);//通过用户名查找排队情况
    void queryQueueByResName(String restaurantName,  DBQueryCallback DBQueryCallback); //通过店铺名查找排队列表
    void queryQueueByResNameAndTableSize(String restaurantName,  //通过店铺名查找排队列表
                                         String tableSize,  DBQueryCallback DBQueryCallback);
    void updateQueue(Queue queue, Order order); //更新排队队列
    void updateQueueIsArrive(Queue queue,  Integer tableNumber,  OneObjectCallBack callBack);//更新排队状态
    void getPreTableNumber( Queue queue,  OneObjectCallBack callBack);//获得排队序号
    void cancelQueue(Queue queue);//取消排队

    void getEatingByIndex(int index, String tableSize, Restaurant restaurant,  OneObjectCallBack callBack);//通过index获得eating
    void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName);//通过桌号删除该桌号用餐的eating

    void queryOrderByUserName(String userName, Integer state,  DBQueryCallback callback);//通过用户名查找订单
    void updateOrderEvaluate(Order order,int state);//更新评价状态

    void queryRestaurantCategory(String restaurantName,  DBQueryCallback callback); //通过店铺名查找菜单类别

    void queryMenuByCategory(MenuCategory menuCategory,  DBQueryCallback callback);//通过菜品类别查找菜品

    void queryCollectionMenubyUserName(String userName,  DBQueryCallback callback);//通过username查询收藏的菜品
    void judgeIsCollectionMenu( String userName,  Menu menu,  OneObjectCallBack callBack);//判断该菜品是否被收藏
    void addCollectionMenu( String userName,  Menu menu,  OneObjectCallBack callBack);//收藏菜品
    void deleteCollectionMenu( String userName,  Menu menu,  OneObjectCallBack callBack);//取消收藏

    void judgeIsCollectionRestaurant(String userName, Restaurant restaurant,  OneObjectCallBack callback);//判断该店铺是否被收藏
    //对店铺收藏的添加和删除操作
    void operationCollectionRestaurant( String operation,  String userName,  Restaurant restaurant,  OneObjectCallBack callBack);
    void getCollectionRestaurant(String userName,  DBQueryCallback callback);//得到收藏的店铺
    //删除收藏的店铺，这是最后哟个restaurant,所以将收藏的这一列删除
    void removeCollectionRestaurant( OneObjectCallBack callBack, CollectionRestaurant coll);
    //删除收藏的店铺，在关系的数量大于1
    void deleteCollectionRestaurant(String userName,  Restaurant restaurant,  OneObjectCallBack callBack, CollectionRestaurant coll);
    //addCollectionRestaurant后添加收藏店铺,只需添加一个relation即可
    void updateCollectionRestaurant(String userName,  Restaurant restaurant,  OneObjectCallBack callBack, CollectionRestaurant coll);
    void addCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callBack);//开始时添加收藏的店铺

    void getEvaluateRestaurant(Restaurant restaurant,  DBQueryCallback callback);//通过店铺得到用户对店铺的评价
    void getEvaluateRestaurantByUserName(String userName,  DBQueryCallback callback);//通过用户名得到用户的评价
}
