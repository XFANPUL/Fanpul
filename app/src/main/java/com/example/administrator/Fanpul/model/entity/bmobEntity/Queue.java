package com.example.administrator.Fanpul.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by why on 2017/4/26.
 */

public class Queue extends BmobObject {
    private String userName;    //用户姓名
    private String tel;     //用户电话

    private Integer tableNumber;

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public void setArrived(boolean arrived) {
        isArrived = arrived;
    }

    private String tableSize;   //桌大小 C-大桌 B-中桌 A-小桌
    private boolean   isOrder; //是否点单
    private Order myOrder;  //用户的订单 若为点单则为空
    private String restaurantName;
    private boolean isArrived;//是否排到



    public Queue() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }


    public Order getMyOrder() {
        return myOrder;
    }

    public void setMyOrder(Order myOrder) {
        this.myOrder = myOrder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }
}
