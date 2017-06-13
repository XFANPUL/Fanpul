package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IQueueDao {
    void queryQueueByUserName(String userName, DBQueryCallback callBack);//通过用户名查找排队情况
    void queryQueueByResName(String restaurantName, DBQueryCallback DBQueryCallback); //通过店铺名查找排队列表
    void queryQueueByResNameAndTableSize(String restaurantName,  //通过店铺名查找排队列表
                                         String tableSize, DBQueryCallback DBQueryCallback);
    void updateQueue(Queue queue, Order order); //更新排队队列
    void updateQueueIsArrive(Queue queue, Integer tableNumber, OneObjectCallBack callBack);//更新排队状态
    void getPreTableNumber(Queue queue, OneObjectCallBack callBack);//获得排队序号
    void cancelQueue(Queue queue);//取消排队

}
