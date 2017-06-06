package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;

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

    }
}
