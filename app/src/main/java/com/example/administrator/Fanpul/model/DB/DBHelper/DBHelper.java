package com.example.administrator.Fanpul.model.DB.DBHelper;

import android.content.Context;

import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.CategoryDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.CollectionMenuDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.CollectionRestaurantDaoImplement;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.EatingDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.EvaluateRestaurantDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.MenuDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.OrderDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.QueueDaoImpl;
import com.example.administrator.Fanpul.model.DB.BmobDaoImpl.RestaurantDaoImpl;
import com.example.administrator.Fanpul.model.DB.DBInterface;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.ICategoryDao;
import com.example.administrator.Fanpul.model.DB.IDao.ICollectionMenuDao;
import com.example.administrator.Fanpul.model.DB.IDao.ICollectionRestaurantDao;
import com.example.administrator.Fanpul.model.DB.IDao.IEatingDao;
import com.example.administrator.Fanpul.model.DB.IDao.IEvaluateRestaurantDao;
import com.example.administrator.Fanpul.model.DB.IDao.IMenuDao;
import com.example.administrator.Fanpul.model.DB.IDao.IOrderDao;
import com.example.administrator.Fanpul.model.DB.IDao.IQueueDao;
import com.example.administrator.Fanpul.model.DB.IDao.IRestaurantDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class DBHelper implements DBInterface {
    private static DBHelper bmobDBConnection;
    private IRestaurantDao restaurantDaoInterface;
    private IQueueDao queueDaoInterface;
    private IEatingDao eatingDaoInteface;
    private ICategoryDao categoryDaoInterface;
    private IMenuDao menuDaoInterface;
    private ICollectionMenuDao collectionMenuDaoInterface;
    private ICollectionRestaurantDao collectionRestaurantDaoInterface;
    private IOrderDao orderDaoInterface;
    private IEvaluateRestaurantDao evaluateRestaurantDaoInterface;

    public DBHelper(Context context) {
        this.restaurantDaoInterface = new RestaurantDaoImpl(context);
        this.queueDaoInterface = new QueueDaoImpl(context);
        this.eatingDaoInteface = new EatingDaoImpl(context);
        this.categoryDaoInterface = new CategoryDaoImpl(context);
        this.menuDaoInterface = new MenuDaoImpl(context);
        this.collectionMenuDaoInterface = new CollectionMenuDaoImpl(context);
        this.collectionRestaurantDaoInterface = new CollectionRestaurantDaoImplement(context);
        this.orderDaoInterface = new OrderDaoImpl(context);
        this.evaluateRestaurantDaoInterface = new EvaluateRestaurantDaoImpl(context);
    }

    public static DBHelper getBmobDBConnection(Context context) {
        if (bmobDBConnection == null) {
            bmobDBConnection = new DBHelper(context);
        }
        return bmobDBConnection;
    }

    public void queryRestaurantByBql(String bql, final DBQueryCallback callback) {
        restaurantDaoInterface.queryRestaurantByBql(bql, callback);
    }

    public void queryRestaurantByName(String restaurantName, final OneObjectCallBack callback) {
        restaurantDaoInterface.queryRestaurantByName(restaurantName, callback);
    }

    public void RemoveRestaurantTableNumber(String restaurantName, String tableSize, Integer tableNum, DBQueryCallback callback) { //获得桌号并锁定桌号
        restaurantDaoInterface.RemoveRestaurantTableNumber(restaurantName, tableSize, tableNum, callback);
    }

    @Override
    public void RemoveRestaurantTableIndex(String restaurantName, String tableSize, OneObjectCallBack oneObjectCallBack) {
        restaurantDaoInterface.RemoveRestaurantTableIndex(restaurantName, tableSize, oneObjectCallBack);
    }


    //通过用户名查找排队情况
    public void queryQueueByUserName(String userName, DBQueryCallback callBack) {
        queueDaoInterface.queryQueueByUserName(userName, callBack);
    }

    //通过店铺名查找排队列表
    public void queryQueueByResName(String restaurantName, DBQueryCallback callback) {
        queueDaoInterface.queryQueueByResName(restaurantName, callback);
    }

    //通过店铺名查找排队列表
    public void queryQueueByResNameAndTableSize(String restaurantName, String tableSize, DBQueryCallback callback) {
        queueDaoInterface.queryQueueByResNameAndTableSize(restaurantName, tableSize, callback);
    }

    //更新排队队列
    public void updateQueue(Queue queue, Order order) {
        queueDaoInterface.updateQueue(queue, order);
    }

    public void updateQueueIsArrive(Queue queue, Integer tableNumber, OneObjectCallBack callBack) {
        queueDaoInterface.updateQueueIsArrive(queue, tableNumber, callBack);
    }

    //通过queue获得它排队的序号
    public void getPreTableNumber(Queue queue, OneObjectCallBack callBack) {
        queueDaoInterface.getPreTableNumber(queue, callBack);
    }

    //取消排队
    public void cancelQueue(Queue queue) {
        queueDaoInterface.cancelQueue(queue);
    }

    public void getEatingByIndex(int index, String tableSize, Restaurant restaurant, OneObjectCallBack callBack) {
        eatingDaoInteface.getEatingByIndex(index, tableSize, restaurant, callBack);
    }


    public void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName) {
        eatingDaoInteface.deleteEatingByTableNumber(number, tableSize, restaurantName);
    }

    //通过店铺名查找菜单类别
    public void queryRestaurantCategory(String restaurantName, DBQueryCallback callback) {
        categoryDaoInterface.queryRestaurantCategory(restaurantName, callback);
    }

    //通过菜品类别查找菜品
    public void queryMenuByCategory(MenuCategory menuCategory, DBQueryCallback callback) { //通过菜品种类查找菜品
        menuDaoInterface.queryMenuByCategory(menuCategory, callback);
    }

    public void queryCollectionMenubyUserName(String userName, DBQueryCallback callback) {
        collectionMenuDaoInterface.queryCollectionMenubyUserName(userName, callback);
    }


    public void judgeIsCollectionMenu(String userName, Menu menu, final OneObjectCallBack callBack) {
        collectionMenuDaoInterface.judgeIsCollectionMenu(userName, menu, callBack);
    }

    public void addCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack) {
        collectionMenuDaoInterface.addCollectionMenu(userName, menu, callBack);
    }

    public void deleteCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack) {
        collectionMenuDaoInterface.deleteCollectionMenu(userName, menu, callBack);
    }

    public void judgeIsCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callback) {
        collectionRestaurantDaoInterface.judgeIsCollectionRestaurant(userName, restaurant, callback);
    }

    public void operationCollectionRestaurant(final String operation, final String userName, final Restaurant restaurant, final OneObjectCallBack callBack) {
        collectionRestaurantDaoInterface.operationCollectionRestaurant(operation, userName, restaurant, callBack);
    }

    public void getCollectionRestaurant(String userName, final DBQueryCallback callback) {//根据姓名得到收藏的店铺
        collectionRestaurantDaoInterface.getCollectionRestaurant(userName, callback);
    }

    //删除收藏的店铺，这是最后哟个restaurant,所以将收藏的这一列删除
    public void removeCollectionRestaurant(final OneObjectCallBack callBack, CollectionRestaurant coll) {
        collectionRestaurantDaoInterface.removeCollectionRestaurant(callBack, coll);
    }

    //删除收藏的店铺，在关系的数量大于1
    public void deleteCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack, CollectionRestaurant coll) {
        collectionRestaurantDaoInterface.deleteCollectionRestaurant(userName, restaurant, callBack, coll);
    }

    //addCollectionRestaurant后添加收藏店铺,只需添加一个relation即可
    public void updateCollectionRestaurant(String userName, final Restaurant restaurant, final OneObjectCallBack callBack, CollectionRestaurant coll) {
        collectionRestaurantDaoInterface.updateCollectionRestaurant(userName, restaurant, callBack, coll);
    }

    public void addCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack) {//开始时添加收藏的店铺
        collectionRestaurantDaoInterface.addCollectionRestaurant(userName, restaurant, callBack);
    }

    //通过用户名查找订单
    public void queryOrderByUserName(String userName, Integer state, DBQueryCallback callback) {
        orderDaoInterface.queryOrderByUserName(userName, state, callback);
    }

    public void updateOrderEvaluate(Order order, int state) {
        orderDaoInterface.updateOrderEvaluate(order, state);
    }


    public void getEvaluateRestaurant(Restaurant restaurant, DBQueryCallback callback) {
        evaluateRestaurantDaoInterface.getEvaluateRestaurant(restaurant, callback);
    }

    public void getEvaluateRestaurantByUserName(String userName, DBQueryCallback callback) {
        evaluateRestaurantDaoInterface.getEvaluateRestaurantByUserName(userName, callback);
    }

}
