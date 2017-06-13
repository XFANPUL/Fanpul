package com.example.administrator.Fanpul.model.DB;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public interface DBQueryCallback<T> {
    void Success(List<T> bmobObjectList);
    void Failed();
}
