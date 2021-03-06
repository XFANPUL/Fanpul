package com.example.administrator.Fanpul.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.ui.fragment.CookListFragment;

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

    public int getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(int curPosition) {
        this.curPosition = curPosition;
    }

    public static  Map<String,Buy> buyMap = new HashMap<>();
    private FragmentManager fragmentManager;
    private static CookListFragment cookListCurrentFragment;
    private int curPosition;
    public MainPageViewPageAdapter(FragmentManager fm, List<MenuCategory> menuCategoryList){//List<TB_CustomCategory> customCategoryDatas){
        super(fm);
        fragmentManager =fm;
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
        curPosition = position;
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

    private List<String> tagList = new ArrayList<>();
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tagList.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void updateUI(int item){
        CookListFragment cookListFragment = (CookListFragment)fragmentManager.findFragmentByTag(tagList.get(item));
        if(cookListFragment!=null)
        cookListFragment.updateUI();

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
