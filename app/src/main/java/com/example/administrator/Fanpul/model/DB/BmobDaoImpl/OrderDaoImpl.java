package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDao.IOrderDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.utils.Utils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class OrderDaoImpl implements IOrderDao {
    public OrderDaoImpl(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public void queryOrderByUserName(String userName, Integer state, final DBQueryCallback callback) {
        String sql = "Select * from Order where userName = '" + userName + "' and evaluateState = " + state;
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Order>().doSQLQuery(sql, new SQLQueryListener<Order>() {
                @Override
                public void done(BmobQueryResult<Order> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        callback.Success(bmobQueryResult.getResults());
                    } else {
                        callback.Failed();
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callback.Failed();
        }
    }

    @Override
    public void updateOrderEvaluate(Order order, int state) {
        order.setEvaluateState(state);
        if (Utils.checkNetwork(context)) {
            order.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
}
