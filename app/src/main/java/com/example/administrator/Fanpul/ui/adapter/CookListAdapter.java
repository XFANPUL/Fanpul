package com.example.administrator.Fanpul.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.ui.activity.CookDetailActivity;
import com.example.administrator.Fanpul.ui.activity.MenuInDetailActivity;
import com.example.administrator.Fanpul.ui.fragment.CookListFragment;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.example.administrator.Fanpul.utils.ToastUtil;

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
        @Bind(R.id.text_price)
        TextView textPrice;

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
            textPrice.setText("￥"+cook.getPrice());
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
                   MenuInDetailActivity.MenuInDetailActivityStart(activity,cook);
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
                        ToastUtil.showToast(activity,R.string.toast_msg_cannot_be_minus);

                    }
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentNum<4) {
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
                        ToastUtil.showToast(activity,R.string.toast_msg_more_than_max);

                    }

                }
            });

        }


    }

}
