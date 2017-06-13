package com.example.administrator.Fanpul.model.DB.IDao;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public interface IMenuDao {
    void queryMenuByCategory(MenuCategory menuCategory, DBQueryCallback callback);//通过菜品类别查找菜品
}
