package com.example.administrator.Fanpul.manager;

import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class AdapterSingletonManager {
     private static Map<String,CommonAdapter> commonAdapterMap = new HashMap<>();

    public static void registerCommonAdapter(String key , CommonAdapter instance){
        if(!commonAdapterMap.containsKey(key)){
            commonAdapterMap.put(key,instance);
        }
    }

    public static CommonAdapter getCommonAdapter(String key){
        return commonAdapterMap.get(key);
    }

}

