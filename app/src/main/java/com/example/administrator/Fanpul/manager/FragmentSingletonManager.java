package com.example.administrator.Fanpul.manager;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class FragmentSingletonManager {
        private static Map<String,Fragment> fragmentHashMap = new HashMap<>();

        public static void registerFragment(String key , Fragment instance){
            if(!fragmentHashMap.containsKey(key)){
                fragmentHashMap.put(key,instance);
            }
        }

        public static Fragment getFragment(String key){
            return fragmentHashMap.get(key);
        }
}
