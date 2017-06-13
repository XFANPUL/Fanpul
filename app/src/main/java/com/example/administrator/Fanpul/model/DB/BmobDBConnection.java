package com.example.administrator.Fanpul.model.DB;

import android.util.Log;

import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionMenu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Eating;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public class BmobDBConnection implements DBInterface {
    private static BmobDBConnection bmobDBConnection;

    public static BmobDBConnection getBmobDBConnection() {
        if(bmobDBConnection == null) {
             bmobDBConnection = new BmobDBConnection();
        }
        return bmobDBConnection;
    }


    public void queryRestaurantByBql(String bql, final DBQueryCallback callback) {
        Log.i("BQL", bql);
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
    }


    //通过店名查找店铺
    public void queryRestaurantByName(String restaurantName, final OneObjectCallBack callback) {
        String bql = "Select * from Restaurant where restaurantName = '" + restaurantName + "'";
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


    //通过用户名查找排队情况
    public void queryQueueByUserName(String userName, final DBQueryCallback callBack) {
        String sql = "Select * from Queue where userName = '" + userName + "' order by createdAt";
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

    //通过用户名查找订单
    public void queryOrderByUserName(String userName, Integer state, final DBQueryCallback callback) {
        String sql = "Select * from Order where userName = '" + userName + "' and evaluateState = " + state;
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

    //通过店铺名查找菜单类别
    public void queryRestaurantCategory(String restaurantName, final DBQueryCallback callback) {
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

    //通过菜品类别查找菜品
    public void queryMenuByCategory(MenuCategory menuCategory, final DBQueryCallback callback) { //通过菜品种类查找菜品
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

    //通过店铺名查找排队列表
    public void queryQueueByResName(String restaurantName, final DBQueryCallback DBQueryCallback) {
        String bql = "select * from Queue where " +
                "restaurantName = '" + restaurantName + "' and isArrived = false order by createdAt";//and isArrived = false
        new BmobQuery<Queue>().doSQLQuery(bql, new SQLQueryListener<Queue>() {
            @Override
            public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<Queue> queueList = bmobQueryResult.getResults();
                    DBQueryCallback.Success(queueList);
                } else {
                    DBQueryCallback.Failed();
                }
            }
        });
    }

    //通过店铺名查找排队列表
    public void queryQueueByResNameAndTableSize(String restaurantName, String tableSize, final DBQueryCallback DBQueryCallback) {
        String bql = "select * from Queue where " +
                "restaurantName = '" + restaurantName + "' and tableSize = '" + tableSize +
                "' and isArrived = false order by createdAt";//and isArrived = false
        new BmobQuery<Queue>().doSQLQuery(bql, new SQLQueryListener<Queue>() {
            @Override
            public void done(BmobQueryResult<Queue> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<Queue> queueList = bmobQueryResult.getResults();
                    DBQueryCallback.Success(queueList);
                } else {
                    DBQueryCallback.Failed();
                }
            }
        });
    }


    public void RemoveRestaurantTableNumber(String restaurantName, String tableSize,
                                            final Integer tableNum, final DBQueryCallback DBQueryCallback) { //获得桌号并锁定桌号
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
                    DBQueryCallback.Success(bmobQueryResult.getResults());
                } else {
                    DBQueryCallback.Failed();
                }
            }
        });
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


    public void RemoveRestaurantTableIndex(String restaurantName, final String tableSize,
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

    //更新排队队列
    public void updateQueue(Queue queue, Order order) {
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
    }

    public void updateOrderEvaluate(Order order, int state) {
        order.setEvaluateState(state);
        order.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }

    public void updateQueueIsArrive(Queue queue, final Integer tableNumber, final OneObjectCallBack callBack) {
        queue.setArrived(true);
        queue.setTableNumber(tableNumber);
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
    }

    //通过queue获得它排队的序号
    public void getPreTableNumber(final Queue queue, final OneObjectCallBack callBack) {
        String sql = "Select * from Queue where tableSize = '" + queue.getTableSize() + "' and  restaurantName = '" + queue.getRestaurantName() + "' and isArrived = false order by createdAt";
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
    }

    //取消排队
    public void cancelQueue(Queue queue) {
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
    }

    public void getEatingByIndex(final int index, String tableSize, final Restaurant restaurant, final OneObjectCallBack callBack) {
        String sql = "Select * from Eating where tableSize = '" + tableSize + "' Order by createdAt";
        new BmobQuery<Eating>().doSQLQuery(sql, new SQLQueryListener<Eating>() {
            @Override
            public void done(BmobQueryResult<Eating> bmobQueryResult, BmobException e) {
                if (e == null) {
                    long time = getPreWaitTime(bmobQueryResult.getResults().get(index), restaurant);
                    callBack.Success(time);
                } else {
                    callBack.Failed();
                }
            }
        });
    }

    public void deleteEatingByTableNumber(Integer number, String tableSize, String restaurantName) {
        String sql = "Select * from Eating where restaurantName = '" + restaurantName + "' " +
                "and tableSize = '" + tableSize + "' and tableNumber = " + number;
        new BmobQuery<Eating>().doSQLQuery(sql, new SQLQueryListener<Eating>() {
            @Override
            public void done(BmobQueryResult<Eating> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() > 0) {
                        Eating eating = bmobQueryResult.getResults().get(0);
                        eating.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                    }

                }
            }
        });
    }

    public static long getPreWaitTime(Eating eating, Restaurant restaurant) {
        String date = eating.getCreatedAt();//正在吃饭的最早的人开始吃饭的时间
        int avgTime = restaurant.getAvgTime();//餐馆平均吃饭时间,单位是分钟
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = formatter.format(currentTime);
        //String theEarliest = formatter.format(date);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            Date d1 = df.parse(nowDate);
            Date d2 = df.parse(date);
            long diff = d1.getTime() - d2.getTime();

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long diffTime = hours * 60 + minutes;
            time = avgTime - diffTime;

        } catch (Exception e) {
        }
        return time;
    }

    public void queryCollectionMenubyUserName(String userName, final DBQueryCallback callback) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
            @Override
            public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                if (e == null && bmobQueryResult.getResults().size() > 0) {
                    callback.Success(bmobQueryResult.getResults().get(0).getMenuList());
                } else {
                    callback.Failed();
                }
            }
        });
    }

    public void judgeIsCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
            @Override
            public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() > 0) {
                        List<Menu> menus = bmobQueryResult.getResults().get(0).getMenuList();
                        for (int i = 0; i < menus.size(); i++) {
                            if (menus.get(i).getObjectId().equals(menu.getObjectId())) {
                                callBack.Success(true);
                                return;
                            }
                        }
                    }
                    callBack.Success(false);
                } else {

                }

            }

        });

    }

    public void addCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
            @Override
            public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() == 0) {
                        CollectionMenu collectionMenu = new CollectionMenu();
                        collectionMenu.setUserName(userName);
                        List<Menu> arrayList = new ArrayList<>();
                        arrayList.add(menu);
                        collectionMenu.setMenuList(arrayList);
                        collectionMenu.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    callBack.Success(null);
                                }

                            }
                        });
                    } else {
                        CollectionMenu collectionMenu = bmobQueryResult.getResults().get(0);
                        collectionMenu.getMenuList().add(menu);
                        collectionMenu.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    callBack.Success(null);
                                }

                            }
                        });
                    }
                } else {
                    callBack.Failed();
                }
            }
        });
    }

    public void deleteCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
            @Override
            public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() > 0) {
                        CollectionMenu collectionMenu = bmobQueryResult.getResults().get(0);
                        if (collectionMenu.getMenuList().size() > 1) {
                            List<Menu> list = new ArrayList<Menu>();
                            list = collectionMenu.getMenuList();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getObjectId().equals(menu.getObjectId())) {
                                    list.remove(i);
                                    break;
                                }
                            }
                            collectionMenu.setMenuList(list);
                            collectionMenu.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        } else {
                            collectionMenu.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }
                        callBack.Success(true);
                    }
                }
            }
        });
    }

    public void judgeIsCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callback) {
        BmobQuery<CollectionRestaurant> b = new BmobQuery<>();
        b.addWhereEqualTo("userName", userName);
        b.addWhereEqualTo("relation", restaurant);
        b.findObjects(new FindListener<CollectionRestaurant>() {
            @Override
            public void done(List<CollectionRestaurant> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        callback.Success(true);
                    } else {
                        callback.Success(false);
                    }
                } else {
                    callback.Success(false);
                }
            }
        });
    }

    public void operationCollectionRestaurant(final String operation, final String userName, final Restaurant restaurant, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionRestaurant where userName = '" + userName + "'";
        new BmobQuery<CollectionRestaurant>().doSQLQuery(sql, new SQLQueryListener<CollectionRestaurant>() {
            @Override
            public void done(BmobQueryResult<CollectionRestaurant> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() == 0) {
                        addCollectionRestaurant(userName, restaurant, callBack);
                    } else {
                        final CollectionRestaurant coll = bmobQueryResult.getResults().get(0);
                        BmobQuery<Restaurant> restaurantBmobQuery = new BmobQuery<Restaurant>();
                        restaurantBmobQuery.addWhereRelatedTo("relation", new BmobPointer(coll));
                        restaurantBmobQuery.findObjects(new FindListener<Restaurant>() {
                            @Override
                            public void done(List<Restaurant> list, BmobException e) {
                                if (e == null) {
                                    if (operation.equals("delete")) {
                                        if (list.size() > 1) {
                                            deleteCollectionRestaurant(userName, restaurant, callBack, coll);
                                        } else {
                                            removeCollectionRestaurant(callBack, coll);
                                        }
                                    } else if (operation.equals("add")) {
                                        updateCollectionRestaurant(userName, restaurant, callBack, coll);
                                    }
                                }
                            }
                        });

                    }
                }
            }
        });

    }

    //删除收藏的店铺，这是最后哟个restaurant,所以将收藏的这一列删除
    public void removeCollectionRestaurant(final OneObjectCallBack callBack, CollectionRestaurant coll) {
        coll.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callBack.Success(null);
                }

            }
        });
    }

    //删除收藏的店铺，在关系的数量大于1
    public void deleteCollectionRestaurant(String userName, final Restaurant restaurant, final OneObjectCallBack callBack, CollectionRestaurant coll) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.remove(restaurant);
        coll.setRelation(bmobRelation);
        coll.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callBack.Success(null);
                }
            }
        });
    }

    //addCollectionRestaurant后添加收藏店铺,只需添加一个relation即可
    public void updateCollectionRestaurant(String userName, final Restaurant restaurant, final OneObjectCallBack callBack, CollectionRestaurant coll) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(restaurant);
        coll.setRelation(bmobRelation);
        coll.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callBack.Success(null);
                }
            }
        });
    }

    public void addCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callBack) {//开始时添加收藏的店铺
        CollectionRestaurant collectionRestaurant = new CollectionRestaurant();
        collectionRestaurant.setUserName(userName);
        BmobRelation relation = new BmobRelation();
        relation.add(restaurant);
        collectionRestaurant.setRelation(relation);
        collectionRestaurant.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    callBack.Success(null);
                }
            }
        });
    }

    public void getCollectionRestaurant(String userName, final DBQueryCallback callback) {//根据姓名得到收藏的店铺
        String sql = "Select * from CollectionRestaurant where userName = '" + userName + "'";
        new BmobQuery<CollectionRestaurant>().doSQLQuery(sql, new SQLQueryListener<CollectionRestaurant>() {
            @Override
            public void done(BmobQueryResult<CollectionRestaurant> bmobQueryResult, BmobException e) {
                if (e == null) {
                    if (bmobQueryResult.getResults().size() > 0) {
                        CollectionRestaurant collectionRestaurant = bmobQueryResult.getResults().get(0);
                        BmobQuery<Restaurant> restaurantQuery = new BmobQuery<Restaurant>();
                        restaurantQuery.addWhereRelatedTo("relation", new BmobPointer(collectionRestaurant));
                        restaurantQuery.findObjects(new FindListener<Restaurant>() {
                            @Override
                            public void done(List<Restaurant> list, BmobException e) {
                                if (e == null) {
                                    callback.Success(list);
                                }
                            }
                        });
                    } else {
                        callback.Success(new ArrayList<Restaurant>());
                    }
                }
            }
        });
    }

    public void getEvaluateRestaurant(Restaurant restaurant, final DBQueryCallback callback) {
        BmobQuery<EvaluateRestaurant> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("restaurant", restaurant);
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
    }

    public void getEvaluateRestaurantByUserName(String userName, final DBQueryCallback callback) {
        String sql = "select include restaurant,* from EvaluateRestaurant where userName = '" + userName + "'";
        new BmobQuery<EvaluateRestaurant>().doSQLQuery(sql, new SQLQueryListener<EvaluateRestaurant>() {
            @Override
            public void done(BmobQueryResult<EvaluateRestaurant> bmobQueryResult, BmobException e) {
                if (e == null) {
                    callback.Success(bmobQueryResult.getResults());
                } else {
                    callback.Failed();
                }
            }
        });
    }


}
