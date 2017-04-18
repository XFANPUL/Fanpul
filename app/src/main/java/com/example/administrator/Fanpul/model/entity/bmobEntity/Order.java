package com.example.administrator.Fanpul.model.entity.bmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class Order extends BmobObject {
    private String userName; //用户
    private String restaurantName; //店铺
    private List<Menu> menuList;  //购买的商品
    private Float totalPrice; //订单菜品总额
    private String payMeasure; //支付方式
    private Integer menuNumber; //商品数量
    private Integer showName; //0表示显示姓名,1表示匿名;默认0
    private String orderDate;  //订单完成日期
    private Integer evaluateState;//0表示待评价，1已评价,2表示排队中
    private String evaluateInformation;//评价信息
    private Integer orderNumber;//点击再来一单时加一
    private List<Integer> menuNumberList;

    public List<Integer> getMenuNumberList() {
        return menuNumberList;
    }

    public void setMenuNumberList(List<Integer> menuNumberList) {
        this.menuNumberList = menuNumberList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Integer getEvaluateState() {
        return evaluateState;
    }

    public void setEvaluateState(Integer evaluateState) {
        this.evaluateState = evaluateState;
    }

    public void setOrderDate(String orderDate) {

        this.orderDate = orderDate;
    }


    public String getEvaluateInformation() {
        return evaluateInformation;
    }

    public void setEvaluateInformation(String evaluateInformation) {
        this.evaluateInformation = evaluateInformation;
    }


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
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
