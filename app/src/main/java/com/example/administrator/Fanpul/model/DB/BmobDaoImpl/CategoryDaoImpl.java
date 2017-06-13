package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDao.ICategoryDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
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

public class CategoryDaoImpl implements ICategoryDao {
    private Context context;

    public CategoryDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    //通过店铺名查找菜单类别
    public void queryRestaurantCategory(String restaurantName, final DBQueryCallback callback) {
        if (Utils.checkNetwork(context)) {
            BmobQuery<Restaurant> restaurantBmobQuery = new BmobQuery<>();
            restaurantBmobQuery.addWhereEqualTo("restaurantName",restaurantName);
            restaurantBmobQuery.findObjects(new FindListener<Restaurant>() {
                @Override
                public void done(List<Restaurant> list, BmobException e) {
                    if(e==null && list.size()>0){
                        Restaurant restaurant = list.get(0);
                        BmobQuery<MenuCategory> menuCategoryBmobQuery = new BmobQuery<MenuCategory>();
                        menuCategoryBmobQuery.addWhereEqualTo("restaurant", restaurant);
                        menuCategoryBmobQuery.findObjects(new FindListener<MenuCategory>() {
                            @Override
                            public void done(List<MenuCategory> list, BmobException e) {
                                if (e == null) {
                                    callback.Success(list);
                                } else {
                                    callback.Failed();
                                }
                            }
                        });
                    }else {
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
/* new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
                @Override
                public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        Restaurant restaurant = bmobQueryResult.getResults().get(0);
                        BmobQuery<MenuCategory> menuCategoryBmobQuery = new BmobQuery<MenuCategory>();
                        menuCategoryBmobQuery.addWhereEqualTo("restaurant", restaurant);
                        menuCategoryBmobQuery.findObjects(new FindListener<MenuCategory>() {
                            @Override
                            public void done(List<MenuCategory> list, BmobException e) {
                                if (e == null) {
                                    callback.Success(list);
                                } else {
                                    callback.Failed();
                                }
                            }
                        });
                    } else {
                        callback.Failed();
                    }
                }
            });*/