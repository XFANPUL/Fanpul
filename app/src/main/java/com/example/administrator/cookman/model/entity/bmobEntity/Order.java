package com.example.administrator.cookman.model.entity.bmobEntity;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class Order extends BmobObject {
    private User user; //用户
    private Restaurant restaurant; //店铺
    private List<Menu> menuList;  //购买的商品
    private Integer peopleNumber;//用餐人数
    private Float totalPrice; //订单菜品总额
    private String payMeasure; //支付方式
    private Integer menuNumber; //商品数量
    private Integer showName; //0表示显示姓名,1表示匿名;默认0
    private Date orderDate;  //订单完成日期
    private Boolean evaluateState;//0表示待评价，1已评价,2表示排队中
    private String evaluateInformation;//评价信息

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    private Integer orderNumber;//点击再来一单时加一

    public Date getOrderDate() {
        return orderDate;
    }

    public Boolean getEvaluateState() {
        return evaluateState;
    }

    public void setEvaluateState(Boolean evaluateState) {
        this.evaluateState = evaluateState;
    }
    public String getEvaluateInformation() {
        return evaluateInformation;
    }

    public void setEvaluateInformation(String evaluateInformation) {
        this.evaluateInformation = evaluateInformation;
    }


    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayMeasure() {
        return payMeasure;
    }

    public void setPayMeasure(String payMeasure) {
        this.payMeasure = payMeasure;
    }

    public Integer getMenuNumber() {
        return menuNumber;
    }

    public void setMenuNumber(Integer menuNumber) {
        this.menuNumber = menuNumber;
    }

    public Integer getShowName() {
        return showName;
    }

    public void setShowName(Integer showName) {
        this.showName = showName;
    }



}
