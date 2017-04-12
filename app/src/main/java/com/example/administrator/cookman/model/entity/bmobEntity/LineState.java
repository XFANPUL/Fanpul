package com.example.administrator.cookman.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class LineState extends BmobObject {//排队状态表
    private String userName;
    private Integer bigTableLineNumber;//大桌排队人数
    private Integer middleTableLineNumber;//中桌排队人数
    private Integer smallTableLineNumber;//小桌排队人数
    private Restaurant restaurant; //店铺
    private Integer state;//0表示直接排队(点击直接排队状态)，1表示排队点餐(点单完成状态),2表示未进行操作或取消排队
    //当点完餐后将存储该订单,可通过用户+店铺+订单状态查找

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getBigTableLineNumber() {
        return bigTableLineNumber;
    }

    public void setBigTableLineNumber(Integer bigTableLineNumber) {
        this.bigTableLineNumber = bigTableLineNumber;
    }

    public Integer getMiddleTableLineNumber() {
        return middleTableLineNumber;
    }

    public void setMiddleTableLineNumber(Integer middleTableLineNumber) {
        this.middleTableLineNumber = middleTableLineNumber;
    }

    public Integer getSmallTableLineNumber() {
        return smallTableLineNumber;
    }

    public void setSmallTableLineNumber(Integer smallTableLineNumber) {
        this.smallTableLineNumber = smallTableLineNumber;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
