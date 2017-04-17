package com.example.administrator.Fanpul.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public class MenuCategory extends BmobObject {
    private String categoryName;//菜品类别
    private Restaurant restaurant;//菜品所属店家

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
