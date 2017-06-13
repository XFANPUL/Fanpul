package com.example.administrator.Fanpul.model.entity.bmobEntity;
import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class EvaluateRestaurant extends BmobObject {
    private Restaurant restaurant;
    private Float menusScore;//菜品总评分
    private Float speedScore;//上菜速度评分
    private Float serviceScore;//服务质量评分

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    private String textEvaluate;//文本评价
    private String userName;
    private Float price;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Float getMenusScore() {
        return menusScore;
    }

    public void setMenusScore(Float menusScore) {
        this.menusScore = menusScore;
    }

    public Float getSpeedScore() {
        return speedScore;
    }

    public void setSpeedScore(Float speedScore) {
        this.speedScore = speedScore;
    }

    public Float getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Float serviceScore) {
        this.serviceScore = serviceScore;
    }

    public String getTextEvaluate() {
        return textEvaluate;
    }

    public void setTextEvaluate(String textEvaluate) {
        this.textEvaluate = textEvaluate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
