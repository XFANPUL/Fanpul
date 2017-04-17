package com.example.administrator.Fanpul.IView;

import com.example.administrator.Fanpul.model.entity.CookEntity.CookDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface ICookSearchResultView {

    void onCookSearchLoadMoreSuccess(ArrayList<CookDetail> list);
    void onCookSearchLoadMoreFaile(String msg);
    void onCookSearchLoadMoreNoData();

}
