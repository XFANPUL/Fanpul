package com.example.administrator.Fanpul.model.DB.IDBCallBack;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public interface OneObjectCallBack<T> {
    void Success(T result);
    void Failed();
}
