package com.example.administrator.cookman.model.entity.bmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class CollectionRestaurant extends BmobObject {
    private List<Restaurant> restaurants;//收藏的店铺
    private User user;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

}
