package com.example.administrator.Fanpul.model.bmob;

import android.util.Log;

import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class BmobUtil {
    public static void queryBmobObject(String bql, final BmobQueryCallback callback) {
        Log.i("BQL", bql);
        new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
            @Override
            public void done(BmobQueryResult<Restaurant> bmobQueryResult, BmobException e) {
                if (e == null) {
                    // Log.i("SucSSSS",bmobQueryResult.getResults().toString());
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
    }

    public static void queryRestaurantByName(String restaurantName, final OneObjectCallBack callback) {
        String bql = "Select * from Restaurant where restaurantName = '" + restaurantName + "' order by createAt";
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
    }


    public static void queryQueueByUserName(String userName, final BmobQueryCallback callBack) {
        String sql = "Select * from Queue where userName = '" + userName + "' order by createAt";
        new BmobQuery<Queue>().doSQLQuery(sql, new SQLQueryListener<Queue>() {
            @Override
            public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                if (e == null)
                    callBack.Success(bmobQueryResult.getResults());
                else
                    callBack.Failed();
            }
        });
    }

    public static void queryOrderByUserName(String userName,Integer state, final BmobQueryCallback callback) {
        String sql = "Select * from Order where userName = '" + userName + "' and evaluateState = "+state;
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
    }

    public static void queryRestaurantCategory(String restaurantName, final BmobQueryCallback callback) {
        String bql = "select * from Restaurant where restaurantName = '" + restaurantName + "'";
        new BmobQuery<Restaurant>().doSQLQuery(bql, new SQLQueryListener<Restaurant>() {
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
        });
    }

    public static void queryMenuByCategory(MenuCategory menuCategory, final BmobQueryCallback callback) { //通过菜品种类查找菜品
        BmobQuery<Menu> menuBmobQuery = new BmobQuery<Menu>();
        menuBmobQuery.addWhereEqualTo("relation", menuCategory);
        menuBmobQuery.findObjects(new FindListener<Menu>() {
            @Override
            public void done(List<Menu> list, BmobException e) {
                if (e == null) {
                    callback.Success(list);
                } else {
                    callback.Failed();
                }
            }

        });
    }

    public static void queryQueueByResName(String restaurantName, final BmobQueryCallback bmobQueryCallback) {
        String bql = "select * from Queue where " +
                "restaurantName = '" + restaurantName + "' order by createAt";
        new BmobQuery<Queue>().doSQLQuery(bql, new SQLQueryListener<Queue>() {
            @Override
            public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<Queue> queueList = bmobQueryResult.getResults();
                    bmobQueryCallback.Success(queueList);
                } else {
                    bmobQueryCallback.Failed();
                }
            }
        });
    }


    public static void RemoveRestaurantTableNumber(String restaurantName, String tableSize,
                                                   final Integer tableNum, final BmobQueryCallback bmobQueryCallback) { //获得桌号并锁定桌号
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
                    bmobQueryCallback.Success(bmobQueryResult.getResults());
                } else {
                    bmobQueryCallback.Failed();
                }
            }
        });
    }

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

    public static void RemoveRestaurantTableIndex(String restaurantName, final String tableSize,
                                                  final OneObjectCallBack oneObjectCallBack) { //获得桌号并锁定桌号

        String sql = "select * from Restaurant where restaurantName = '" + restaurantName + "'";
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
    }




}
