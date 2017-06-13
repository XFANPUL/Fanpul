package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class CategoryAdapter extends CommonAdapter<MenuCategory> {
    private RecyclerView recyclerViewCookBook;
    private MenuCookAdapter menuCookAdapter;
    public CategoryAdapter(Context context, int layoutId, List<MenuCategory> datas , RecyclerView recyclerView) {
        super(context, layoutId, datas);
        this.recyclerViewCookBook = recyclerView;
        menuCookAdapter = new MenuCookAdapter(getmContext() , R.layout.menu_cook_item , null);
        recyclerViewCookBook.setAdapter(menuCookAdapter);
    }
    @Override
    public void bindDatas(ViewHolder holder, final MenuCategory menuCategory) {
          holder.setText(R.id.cook_book_category , menuCategory.getCategoryName());
          holder.getmItemView().setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  DBProxy.proxy.queryMenuByCategory(menuCategory, new DBQueryCallback<Menu>() {
                      @Override
                      public void Success(List<Menu> bmobObjectList) {
                          menuCookAdapter.setmDatas(bmobObjectList);
                      }
                      @Override
                      public void Failed() {

                      }
                  });
              }
          });
    }

}


