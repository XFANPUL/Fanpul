package com.example.administrator.cookman.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.model.entity.bmobEntity.Menu;
import com.example.administrator.cookman.ui.activity.CookDetailActivity;
import com.example.administrator.cookman.ui.activity.CookListActivity;
import com.example.administrator.cookman.ui.fragment.CookListFragment;
import com.example.administrator.cookman.utils.GlideUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/19.
 */

public class CookListAdapter extends BaseRecyclerAdapter<Menu>{

    private Activity activity;
    private GlideUtil glideUtil;

    public CookListAdapter(Activity activity){
        this.activity = activity;
        glideUtil = new GlideUtil();
    }
  //CookDetail->Menu
    @Override
    public CommonHolder<Menu> setViewHolder(ViewGroup parent) {
        return new CardHolder(parent.getContext(), parent);
    }

    class CardHolder extends CommonHolder<Menu> {
        private int currentNum=0;
        private CookListFragment cookListFragment;
        @Bind(R.id.img_cook)
        ImageView imgvCook;

        @Bind(R.id.text_name)
        TextView textName;

        @Bind(R.id.delete)
        Button deleteButton;

        @Bind(R.id.add)
        Button addButton;

        @Bind(R.id.numbertext)
        TextView numberText;

        public CardHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_list);
            cookListFragment = MainPageViewPageAdapter.getCurrentCookListCurrentFragment();
        }

        @Override
        public void bindData(final Menu cook) {
            textName.setText(cook.getMenuName());
            if(cookListFragment!=null&&cookListFragment.getBuy(cook.getObjectId()).getMenu()!=null){
                numberText.setText(cookListFragment.getBuy(cook.getObjectId()).getNumber()+"");
                currentNum = cookListFragment.getBuy(cook.getObjectId()).getNumber();
            }else{
                numberText.setText(0+"");
                currentNum = 0;
            }
            /*if(cook.getRecipe() != null && cook.getRecipe().getImg() != null && (!TextUtils.isEmpty(cook.getRecipe().getImg()))) {
                glideUtil.attach(imgvCook).injectImageWithNull(cook.getRecipe().getImg());
            }else imgvCook.setImageResource(R.mipmap.qiudaoyu);*/
            if(cook!=null && cook.getImgUrl()!=null&&(!TextUtils.isEmpty(cook.getImgUrl()))){
                glideUtil.attach(imgvCook).injectImageWithNull(cook.getImgUrl());
            }

            imgvCook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //CookDetailActivity.startActivity(activity, imgvCook, cook, true);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentNum>0) {
                        cookListFragment = MainPageViewPageAdapter.getCurrentCookListCurrentFragment();
                        currentNum--;
                        MainPageViewPageAdapter.Buy buy = cookListFragment.getBuy(cook.getObjectId());
                        buy.setNumber(buy.getNumber()-1);
                        cookListFragment.ReduceCurrentNum(cook.getObjectId(),currentNum);
                        numberText.setText(currentNum+"");
                      //  buy.setNumber(currentNum);
                    }
                    else{
                        Toast.makeText(activity,"数量不能为负",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentNum<9) {
                        cookListFragment = MainPageViewPageAdapter.getCurrentCookListCurrentFragment();
                       // Toast.makeText(activity,MainPageViewPageAdapter.getCurrentCookListCurrentFragment().toString(),Toast.LENGTH_SHORT).show();
                        MainPageViewPageAdapter.Buy buy = cookListFragment.getBuy(cook.getObjectId());
                        currentNum++;
                       buy.setNumber(currentNum);
                        buy.setMenu(cook);
                        cookListFragment.AddCurrentNum(cook.getObjectId(),buy);
                        numberText.setText(currentNum + "");
                     //   buy.setNumber(currentNum);
                    }else{
                        Toast.makeText(activity,"数量超过限制",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

}
