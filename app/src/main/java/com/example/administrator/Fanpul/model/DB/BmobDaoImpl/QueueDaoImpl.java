package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.IQueueDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class QueueDaoImpl implements IQueueDao {
    private Context context;
    public QueueDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public void queryQueueByUserName(String userName, final DBQueryCallback callBack) {
        String sql = "Select * from Queue where userName = '" + userName + "' order by createdAt";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Queue>().doSQLQuery(sql, new SQLQueryListener<Queue>() {
                @Override
                public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                    if (e == null)
                        callBack.Success(bmobQueryResult.getResults());
                    else
                        callBack.Failed();
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }


    @Override
    public void queryQueueByResName(String restaurantName, final DBQueryCallback callback) {
        String bql = "select * from Queue where " +
                "restaurantName = '" + restaurantName + "' and isArrived = false order by createdAt";//and isArrived = false
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Queue>().doSQLQuery(bql, new SQLQueryListener<Queue>() {
                @Override
                public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<Queue> queueList = bmobQueryResult.getResults();
                        callback.Success(queueList);
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
    public void queryQueueByResNameAndTableSize(String restaurantName, String tableSize, final DBQueryCallback callback) {
        String bql = "select * from Queue where " +
                "restaurantName = '" + restaurantName + "' and tableSize = '" + tableSize +
                "' and isArrived = false order by createdAt";//and isArrived = false
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Queue>().doSQLQuery(bql, new SQLQueryListener<Queue>() {
                @Override
                public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<Queue> queueList = bmobQueryResult.getResults();
                        callback.Success(queueList);
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
    public void updateQueue(Queue queue, Order order) {
        if (Utils.checkNetwork(context)) {
            if (!queue.isArrived()) {
                queue.setMyOrder(order);
                queue.setOrder(true);
                queue.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("Success", "Success");
                        } else {
                            Log.i("Fail1", "Fail1");
                        }
                    }
                });
            } else {
                queue.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void updateQueueIsArrive(Queue queue, final Integer tableNumber, final OneObjectCallBack callBack) {
        queue.setArrived(true);
        queue.setTableNumber(tableNumber);
        if (Utils.checkNetwork(context)) {
            queue.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.i("Success", "Success");
                        callBack.Success(tableNumber);
                    } else {
                        Log.i("Fail1", "Fail1");
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    public void getPreTableNumber(final Queue queue, final OneObjectCallBack callBack) {
        String sql = "Select * from Queue where tableSize = '" + queue.getTableSize() + "' and  restaurantName = '" + queue.getRestaurantName() + "' and isArrived = false order by createdAt";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Queue>().doSQLQuery(sql, new SQLQueryListener<Queue>() {
                @Override
                public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        for (int i = 0; i < bmobQueryResult.getResults().size(); i++) {
                            if (bmobQueryResult.getResults().get(i).getObjectId().equals(queue.getObjectId())) {
                                callBack.Success(i);
                                break;
                            }
                        }

                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    public void cancelQueue(Queue queue) {
        if (Utils.checkNetwork(context)) {
            queue.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.i("Success", "Success");

                    } else {
                        Log.i("Failed", "Failed");
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
        }
    }
}
