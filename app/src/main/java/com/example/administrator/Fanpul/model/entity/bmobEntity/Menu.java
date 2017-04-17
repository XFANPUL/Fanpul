package com.example.administrator.Fanpul.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2017/4/9 0009.
 */

public class Menu extends BmobObject {
    private String menuName;//菜品名称
    private String imgUrl;//菜品图片url
    private String description;
    private Integer price;//菜品价格
    private BmobRelation relation;//菜品和菜品类别之间多对多关系
    private Integer state; //菜品状态，0（充足),1(少量），2(售罄)
    private Integer saleNum; //菜品销售数量,估计用不上

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }



}
