package com.example.administrator.Fanpul.presenter;

import android.content.Context;
import android.util.Log;
import com.example.administrator.Fanpul.IView.ICookListView;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import java.util.List;
/**
 * Created by Administrator on 2017/2/20.
 */

public class CookListPresenter extends Presenter{
    private ICookListView iCookListView;
    public CookListPresenter(Context context, ICookListView iCookListView){
        super(context);

        this.iCookListView = iCookListView;
    }
    public void updateRefreshCookMenuCategory(MenuCategory menuCategory){
        DBConnection.queryMenuByCategory(menuCategory, new DBQueryCallback<Menu>() {
            @Override
            public void Success(List<Menu> bmobObjectList) {
                iCookListView.onMenuListUpdateRefreshSuccess(bmobObjectList);
            }

            @Override
            public void Failed() {
                Log.i("Failed","查询菜品失败");
            }
        });
    }
    public void loadMoreMenuByCategory(MenuCategory menuCategory){
        DBConnection.queryMenuByCategory(menuCategory, new DBQueryCallback() {
            @Override
            public void Success(List bmobObjectList) {

            }

            @Override
            public void Failed() {

            }
        });
    }

    @Override
    public void destroy() {

    }
}
