package com.example.administrator.Fanpul.model.entity.bmobEntity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class EvaluateMenu extends BmobObject {//菜品评价实体类
    private Menu menu ;//菜品
    private String userName;
    private Integer like;//0表示赞，1表示一般，2表示踩

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
