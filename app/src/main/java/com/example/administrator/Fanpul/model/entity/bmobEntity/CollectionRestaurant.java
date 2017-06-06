package com.example.administrator.Fanpul.model.entity.bmobEntity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2017/4/10 0010.
 */

public class CollectionRestaurant extends BmobObject {
    private BmobRelation relation;//收藏的店铺
    private String userName;

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
