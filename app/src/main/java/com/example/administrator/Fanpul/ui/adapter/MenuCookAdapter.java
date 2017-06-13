package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class MenuCookAdapter extends CommonAdapter<Menu> {
    public MenuCookAdapter(Context context, int layoutId, List<Menu> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(ViewHolder holder, Menu menu) {
        float price = menu.getPrice();
        holder.setImageByUrl(R.id.img_cook_book, menu.getImgUrl())
                .setText(R.id.text_name_book, menu.getMenuName())
                .setText(R.id.price, getmContext().getString(R.string.price, price))
                .setVisibility(R.id.price, View.VISIBLE);
    }
}
