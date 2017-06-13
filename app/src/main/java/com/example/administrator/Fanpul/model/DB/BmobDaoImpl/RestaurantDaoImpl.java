package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.IRestaurantDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class RestaurantDaoImpl implements IRestaurantDao {
    public RestaurantDaoImpl(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void queryRestaurantByBql(String bql, final DBQueryCallback callback) {
        if (Utils.checkNetwork(context)) {

            new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
                @Override
                public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            callback.Success(bmobQueryResult.getResults());
                        } else {
                            callback.Failed();
                        }
                    } else {
                        Log.i("Failedss", "Fai");
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
    public void queryRestaurantByName(String restaurantName, final OneObjectCallBack callback) {
        String bql = "Select * from Restaurant where restaurantName = '" + restaurantName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
                @Override
                public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            callback.Success(bmobQueryResult.getResults().get(0));
                        } else {
                            callback.Failed();
                        }
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
    public void RemoveRestaurantTableNumber(String restaurantName, String tableSize, final Integer tableNum, final DBQueryCallback callback) {
        String SizeABC = "";//桌号的ABC类型
        if (tableSize.equals("C")) {
            SizeABC = "C";
            tableSize = "bigTableLeft";//桌子的size
        } else if (tableSize.equals("B")) {
            SizeABC = "B";
            tableSize = "middleTableLeft";
        } else if (tableSize.equals("A")) {
            SizeABC = "A";
            tableSize = "smallTableLeft";
        }
        String sql = "select * from Restaurant where restaurantName = '" + restaurantName +
                "' and " + tableSize + " = " + tableNum;
        final String finalSizeABC = SizeABC;
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Restaurant>().doSQLQuery(sql, new SQLQueryListener<Restaurant>() {
                @Override
                public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<Integer> integers = null;
                        List<Restaurant> bmobObjectList = bmobQueryResult.getResults();
                        if (finalSizeABC.equals("C")) {
                            integers = bmobObjectList.get(0).getBigTableLeft();
                            integers.remove(tableNum);
                            Restaurant restaurant = bmobObjectList.get(0);
                            restaurant.setBigTableLeft(integers);
                            restaurant.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("Success", "Success");
                                    } else {
                                        Log.i("Failed", "Failed");
                                    }
                                }
                            });
                        } else if (finalSizeABC.equals("B")) {
                            integers = bmobObjectList.get(0).getMiddleTableLeft();
                            integers.remove(tableNum);
                            Restaurant restaurant = bmobObjectList.get(0);
                            restaurant.setMiddleTableLeft(integers);
                            restaurant.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("Success", "Success");
                                    } else {
                                        Log.i("Failed", "Failed");
                                    }
                                }
                            });
                        } else if (finalSizeABC.equals("A")) {
                            integers = bmobObjectList.get(0).getSmallTableLeft();
                            integers.remove(tableNum);
                            Restaurant restaurant = bmobObjectList.get(0);
                            restaurant.setSmallTableLeft(integers);
                            restaurant.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("Success", "Success");
                                    } else {
                                        Log.i("Failed", "Failed");
                                    }
                                }
                            });
                        }
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
    public void RemoveRestaurantTableIndex(String restaurantName, final String tableSize,
                                           final OneObjectCallBack oneObjectCallBack) { //获得桌号并锁定桌号

        String sql = "select * from Restaurant where restaurantName = '" + restaurantName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<Restaurant>().doSQLQuery(sql, new SQLQueryListener<Restaurant>() {
                @Override
                public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<Integer> integers = null;
                        Integer integer = 0;
                        List<Restaurant> bmobObjectList = bmobQueryResult.getResults();
                        Restaurant restaurant = bmobObjectList.get(0);
                        int tableIndex = getRandomTableNumber(tableSize, restaurant);
                        if (tableSize.equals("C")) {
                            integers = bmobObjectList.get(0).getBigTableLeft();
                            integer = integers.get(tableIndex);
                            integers.remove(integer);
                            restaurant.setBigTableLeft(integers);
                        } else if (tableSize.equals("B")) {
                            integers = bmobObjectList.get(0).getMiddleTableLeft();
                            integer = integers.get(tableIndex);
                            integers.remove(integer);
                            restaurant.setMiddleTableLeft(integers);
                        } else if (tableSize.equals("A")) {
                            integers = bmobObjectList.get(0).getSmallTableLeft();
                            integer = integers.get(tableIndex);
                            integers.remove(integer);
                            restaurant.setSmallTableLeft(integers);

                        }
                        restaurant.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("Success", "Success");
                                } else {
                                    Log.i("Failed", "Failed");
                                }
                            }
                        });
                        oneObjectCallBack.Success(integer);
                    } else {
                        oneObjectCallBack.Failed();
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            oneObjectCallBack.Failed();
        }
    }

    //得到随机桌号
    public static int getRandomTableNumber(String tableSize, Restaurant restaurant) {
        Random random = new Random();
        int ran = 0;

        if (tableSize.equals("A")) {
            ran = Math.abs(random.nextInt() % (restaurant.getSmallTableLeft().size()));
        } else if (tableSize.equals("B")) {
            ran = Math.abs(random.nextInt() % (restaurant.getMiddleTableLeft().size()));
        } else if (tableSize.equals("C")) {
            ran = Math.abs(random.nextInt() % (restaurant.getBigTableLeft().size()));
        }

        return ran;
    }
}
