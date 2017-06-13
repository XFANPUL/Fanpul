package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDao.IEvaluateRestaurantDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class EvaluateRestaurantDaoImpl implements IEvaluateRestaurantDao {
    private Context context;

    public EvaluateRestaurantDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getEvaluateRestaurant(Restaurant restaurant, final DBQueryCallback callback) {
        BmobQuery<EvaluateRestaurant> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("restaurant", restaurant);
        if (Utils.checkNetwork(context)) {
            bmobQuery.findObjects(new FindListener<EvaluateRestaurant>() {
                @Override
                public void done(List<EvaluateRestaurant> list, BmobException e) {
                    if (e == null) {
                        callback.Success(list);
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
    public void getEvaluateRestaurantByUserName(String userName, final DBQueryCallback callback) {
        String sql = "select include restaurant,* from EvaluateRestaurant where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<EvaluateRestaurant>().doSQLQuery(sql, new SQLQueryListener<EvaluateRestaurant>() {
                @Override
                public void done(BmobQueryResult<EvaluateRestaurant> bmobQueryResult, BmobException e) {
                    if (e == null && bmobQueryResult!=null) {
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

}
