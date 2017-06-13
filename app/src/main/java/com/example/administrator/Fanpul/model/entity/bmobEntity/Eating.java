package com.example.administrator.Fanpul.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class Eating extends BmobObject{
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUserName() {
        return userName;
    }

    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    private String tableSize;
    private String restaurantName;
    private String userName;
    private Integer tableNumber;

}
