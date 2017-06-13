package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.ICollectionRestaurantDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.ArrayList;
import java.util.List;

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
 * Created by Administrator on 2017/6/9 0009.
 */

public class CollectionRestaurantDaoImplement implements ICollectionRestaurantDao {
    private Context context;

    public CollectionRestaurantDaoImplement(Context context) {
        this.context = context;
    }

    @Override
    public void judgeIsCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callback) {
        BmobQuery<CollectionRestaurant> b = new BmobQuery<>();
        b.addWhereEqualTo("userName", userName);
        b.addWhereEqualTo("relation", restaurant);
        if (Utils.checkNetwork(context)) {
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
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callback.Failed();
        }
    }

    @Override
    public void operationCollectionRestaurant(final String operation, final String userName, final Restaurant restaurant, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionRestaurant where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
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
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }

    }
    @Override

    public void getCollectionRestaurant(String userName, final DBQueryCallback callback) {//根据姓名得到收藏的店铺
        String sql = "Select * from CollectionRestaurant where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
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
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callback.Failed();
        }
    }
    @Override
    //删除收藏的店铺，这是最后哟个restaurant,所以将收藏的这一列删除
    public void removeCollectionRestaurant(final OneObjectCallBack callBack, CollectionRestaurant coll) {
        if (Utils.checkNetwork(context)) {
            coll.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        callBack.Success(null);
                    }

                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    //删除收藏的店铺，在关系的数量大于1
    public void deleteCollectionRestaurant(String userName, final Restaurant restaurant, final OneObjectCallBack callBack, CollectionRestaurant coll) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.remove(restaurant);
        coll.setRelation(bmobRelation);
        if (Utils.checkNetwork(context)) {
            coll.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        callBack.Success(null);
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    //addCollectionRestaurant后添加收藏店铺,只需添加一个relation即可
    public void updateCollectionRestaurant(String userName, final Restaurant restaurant, final OneObjectCallBack callBack, CollectionRestaurant coll) {
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(restaurant);
        coll.setRelation(bmobRelation);
        if (Utils.checkNetwork(context)) {
            coll.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        callBack.Success(null);
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    public void addCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callBack) {//开始时添加收藏的店铺
        CollectionRestaurant collectionRestaurant = new CollectionRestaurant();
        collectionRestaurant.setUserName(userName);
        BmobRelation relation = new BmobRelation();
        relation.add(restaurant);
        collectionRestaurant.setRelation(relation);
        if (Utils.checkNetwork(context)) {
            collectionRestaurant.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        callBack.Success(null);
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }
}
