package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface ICollectionRestaurantDao {
    void judgeIsCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callback);//判断该店铺是否被收藏
    //对店铺收藏的添加和删除操作
    void operationCollectionRestaurant(String operation, String userName, Restaurant restaurant, OneObjectCallBack callBack);
    void getCollectionRestaurant(String userName, DBQueryCallback callback);//得到收藏的店铺
    //删除收藏的店铺，这是最后哟个restaurant,所以将收藏的这一列删除
    void removeCollectionRestaurant(OneObjectCallBack callBack, CollectionRestaurant coll);
    //删除收藏的店铺，在关系的数量大于1
    void deleteCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack, CollectionRestaurant coll);
    //addCollectionRestaurant后添加收藏店铺,只需添加一个relation即可
    void updateCollectionRestaurant(String userName, Restaurant restaurant, OneObjectCallBack callBack, CollectionRestaurant coll);
    void addCollectionRestaurant(String userName, Restaurant restaurant, final OneObjectCallBack callBack);//开始时添加收藏的店铺

}

