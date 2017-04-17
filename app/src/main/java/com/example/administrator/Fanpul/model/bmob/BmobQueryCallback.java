package com.example.administrator.Fanpul.model.bmob;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8 0008.
 */

public interface BmobQueryCallback<T> {
    void Success(List<T> bmobObjectList);
    void Failed();
}
