package com.example.administrator.Fanpul.IView;

import com.example.administrator.Fanpul.model.entity.CookEntity.CookDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface ICookSearchView {

    void onCookSearchSuccess(ArrayList<CookDetail> list, int totalPages);
    void onCookSearchFaile(String msg);
    void onCookSearchEmpty();

}
