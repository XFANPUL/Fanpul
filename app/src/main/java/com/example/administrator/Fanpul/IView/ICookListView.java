package com.example.administrator.Fanpul.IView;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface ICookListView {
    void onCookListUpdateRefreshSuccess(ArrayList<Menu> list);
    void onMenuListUpdateRefreshSuccess(List<Menu> list);
    void onCookListUpdateRefreshFaile(String msg);
    void onCookListLoadMoreSuccess(ArrayList<Menu> list);
    void onMenuListLoadMoreSuccess(List<Menu> list);
    void onCookListLoadMoreFaile(String msg);
}
