package com.example.administrator.Fanpul.model.bmob;

import android.util.Log;

import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class BmobUtil {
    public  static void queryBmobObject(String bql, final BmobQueryCallback callback) {
        Log.i("BQL", bql);
        new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
            @Override
            public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                if(e==null){
                   // Log.i("SucSSSS",bmobQueryResult.getResults().toString());
                    if(bmobQueryResult.getResults().size()>0) {
                        callback.Success(bmobQueryResult.getResults());
                    }
                    else{
                        callback.Failed();
                    }
                }else{
                    Log.i("Failed","Fai");
                    callback.Failed();
                }
            }
        });
    }
    public static void queryRestaurantCategory(String restaurantName, final BmobQueryCallback callback){
        String bql = "select * from Restaurant where restaurantName = '"+restaurantName+"'";
        new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
            @Override
            public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                if(e == null){
                    Restaurant restaurant = bmobQueryResult.getResults().get(0);
                    BmobQuery<MenuCategory> menuCategoryBmobQuery = new BmobQuery<MenuCategory>();
                    menuCategoryBmobQuery.addWhereEqualTo("restaurant",restaurant);
                    menuCategoryBmobQuery.findObjects(new FindListener<MenuCategory>() {
                        @Override
                        public void done(List<MenuCategory> list, BmobException e) {
                            if(e == null){
                               callback.Success(list);
                            }
                            else{
                                callback.Failed();
                            }
                        }
                    });
                }else{
                    callback.Failed();
                }
            }
        });
    }

    public static void queryMenuByCategory(MenuCategory menuCategory, final BmobQueryCallback callback){ //通过菜品种类查找菜品
        BmobQuery<Menu> menuBmobQuery=new BmobQuery<Menu>();
        menuBmobQuery.addWhereEqualTo("relation",menuCategory);
        menuBmobQuery.findObjects(new FindListener<Menu>() {
            @Override
            public void done(List<Menu> list, BmobException e) {
                if(e == null){
                    callback.Success(list);
                }
                else{
                    callback.Failed();
                }
            }

        });
    }
}
