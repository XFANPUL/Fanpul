package com.example.administrator.Fanpul.model.entity.bmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class Restaurant extends BmobObject {
    private String restaurantName;
    private Float score;  //评分
    private Integer order; //订单数目
    private Integer bigtableNum;//初始大桌数量
    private Integer middletableNum;
    private Integer smalltableNum;
    private List<BmobFile> bmobFileList;//主页下每个商家横滑的图片list
    private BmobFile iconfile;
    private String address;
    private List<Integer> bigTableLeft;//剩余的大桌数量,存储的是桌号，通过随机生成数字i（<size),取得桌号
    private List<Integer> middleTableLeft;//存储的是桌号
    private List<Integer> smallTableLeft;//存储的是桌号
    private List<String> favourable;//优惠信息

    public List<Integer> getBigTableLeft() {
        return bigTableLeft;
    }

    public void setBigTableLeft(List<Integer> bigTableLeft) {
        this.bigTableLeft = bigTableLeft;
    }

    public List<Integer> getMiddleTableLeft() {
        return middleTableLeft;
    }

    public void setMiddleTableLeft(List<Integer> middleTableLeft) {
        this.middleTableLeft = middleTableLeft;
    }

    public List<Integer> getSmallTableLeft() {
        return smallTableLeft;
    }

    public void setSmallTableLeft(List<Integer> smallTableLeft) {
        this.smallTableLeft = smallTableLeft;
    }

    public void setScore(Float score) {
        this.score = score;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BmobFile> getBmobFileList() {
        return bmobFileList;
    }

    public void setBmobFileList(List<BmobFile> bmobFileList) {
        this.bmobFileList = bmobFileList;
    }

    public BmobFile getIconfile() {
        return iconfile;
    }

    public void setIconfile(BmobFile iconfile) {
        this.iconfile = iconfile;
    }

    private Integer distance;
    private String description;

    public List<String> getFavourable() {
        return favourable;
    }

    public void setFavourable(List<String> favourable) {
        this.favourable = favourable;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getBigtableNum() {
        return bigtableNum;
    }

    public void setBigtableNum(Integer bigtableNum) {
        this.bigtableNum = bigtableNum;
    }

    public Integer getMiddletableNum() {
        return middletableNum;
    }

    public void setMiddletableNum(Integer middletableNum) {
        this.middletableNum = middletableNum;
    }

    public Integer getSmalltableNum() {
        return smalltableNum;
    }

    public void setSmalltableNum(Integer smalltableNum) {
        this.smalltableNum = smalltableNum;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
