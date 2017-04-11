package com.example.administrator.cookman.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.model.entity.bmobEntity.Menu;
import com.example.administrator.cookman.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CookDetail;
import com.example.administrator.cookman.model.entity.tb_cook.TB_CustomCategory;
import com.example.administrator.cookman.ui.fragment.CookListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */

public class MainPageViewPageAdapter extends FragmentPagerAdapter {

   // private List<TB_CustomCategory> customCategoryDatas;
    private List<MenuCategory> menuCategoryList;
    public static  Map<String,Buy> buyMap = new HashMap<>();

    private static CookListFragment cookListCurrentFragment;
    public MainPageViewPageAdapter(FragmentManager fm, List<MenuCategory> menuCategoryList){//List<TB_CustomCategory> customCategoryDatas){
        super(fm);
       // this.customCategoryDatas = customCategoryDatas;
        this.menuCategoryList= menuCategoryList;
    }

    @Override
    public CookListFragment getItem(int position) {
        CookListFragment fragment = null;

        fragment = new CookListFragment();
        //fragment.setCustomCategoryData(customCategoryDatas.get(position));
        fragment.setMenuCategory(menuCategoryList.get(position));
        fragment.setBuyMap(buyMap);
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        cookListCurrentFragment = (CookListFragment)object;
        super.setPrimaryItem(container, position, object);
    }

    public static CookListFragment getCurrentCookListCurrentFragment(){
         return cookListCurrentFragment;
    }

    @Override
    public int getCount() {
        if(null == menuCategoryList)
            return 0;

        return menuCategoryList.size();
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = menuCategoryList.get(position).hashCode();
        return hashCode;
    }


    public static class Buy{
        private Menu menu;

        public Menu getMenu() {
            return menu;
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
        }

        private int number = 0 ;
        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
    }

}
