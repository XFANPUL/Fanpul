package com.example.administrator.cookman.model.entity.bmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class Restaurant extends BmobObject {
    private String restaurantName;
    private Float score;
    private Integer order;
    private Integer bigtableNum;
    private Integer middletableNum;
    private Integer smalltableNum;
    private List<BmobFile> bmobFileList;
    private BmobFile iconfile;

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

    private List<String> favourable;


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
