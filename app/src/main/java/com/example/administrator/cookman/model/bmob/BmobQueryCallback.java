package com.example.administrator.cookman.model.bmob;

import com.example.administrator.cookman.model.entity.bmobEntity.Restaurant;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public interface BmobQueryCallback<T> {
    void Success(List<T> bmobObjectList);
    void Failed();
}
