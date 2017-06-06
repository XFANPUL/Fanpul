package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class MyCollectionMenuActivity extends BaseActivity {
    @Bind(R.id.recyview_collection)
    public RecyclerView recyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.my_collection_menu_activity;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
    }

    public void updateUI(){
        DBConnection.queryCollectionMenubyUserName("张三", new DBQueryCallback<Menu>() {
            @Override
            public void Success(List<Menu> bmobObjectList) {
                if(bmobObjectList!=null)
                    recyclerView.setAdapter(new MenuCookAdapter(bmobObjectList));
                else
                    recyclerView.setAdapter(new MenuCookAdapter(new ArrayList<Menu>()));
            }

            @Override
            public void Failed() {

            }
        });
    }

    public static void starActivity(Context context){
        Intent i = new Intent(context,MyCollectionMenuActivity.class);
        context.startActivity(i);
    }

    private class MenuCookHolder extends RecyclerView.ViewHolder{
        private ImageView cookbookImg;
        private TextView cookbookName;
        private TextView priceText;
        public MenuCookHolder(View itemView) {
            super(itemView);
            cookbookImg = (ImageView)itemView.findViewById(R.id.img_cook_book);
            cookbookName = (TextView)itemView.findViewById(R.id.text_name_book);
            priceText = (TextView)itemView.findViewById(R.id.price);
        }

        public void bindData(String url,String name,int price){
            new GlideUtil().attach(cookbookImg).injectImageWithNull(url);
            cookbookName.setText(name);
            priceText.setText("￥:"+price);
            priceText.setVisibility(View.VISIBLE);
        }
    }

    private class MenuCookAdapter extends  RecyclerView.Adapter<MenuCookHolder>{
        List<Menu> menuList;
        public MenuCookAdapter(List<Menu> menus){
            menuList = menus;
        }
        @Override
        public MenuCookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MyCollectionMenuActivity.this);
            View v = layoutInflater.inflate(R.layout.menu_cook_item,parent,false);
            return new MenuCookHolder(v);
        }

        @Override
        public void onBindViewHolder(MenuCookHolder holder, int position) {
            holder.bindData(menuList.get(position).getImgUrl(),menuList.get(position).getMenuName(),menuList.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }
    }

}
