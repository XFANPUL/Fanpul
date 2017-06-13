package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface ICollectionMenuDao {
    void queryCollectionMenubyUserName(String userName, DBQueryCallback callback);//通过username查询收藏的菜品
    void judgeIsCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack);//判断该菜品是否被收藏
    void addCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack);//收藏菜品
    void deleteCollectionMenu(String userName, Menu menu, OneObjectCallBack callBack);//取消收藏
}
