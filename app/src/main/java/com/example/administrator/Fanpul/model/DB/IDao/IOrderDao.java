package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IOrderDao {
    void queryOrderByUserName(String userName, Integer state, DBQueryCallback callback);//通过用户名查找订单
    void updateOrderEvaluate(Order order, int state);//更新评价状态

}
