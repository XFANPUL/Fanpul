package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class ImageItemAdapter extends CommonAdapter<String> {

    public ImageItemAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }
    @Override
    public void bindDatas(ViewHolder holder, String url) {
          holder.setImageByUrl(R.id.imageView_my , url);
    }
}
