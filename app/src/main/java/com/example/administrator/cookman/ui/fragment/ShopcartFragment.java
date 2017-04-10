package com.example.administrator.cookman.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.entity.CookEntity.CookDetail;
import com.example.administrator.cookman.ui.activity.ShoppingCartActivity;
import com.example.administrator.cookman.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.cookman.utils.GlideUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class ShopcartFragment extends Fragment {

    private RecyclerView recyclerView;
    private GlideUtil glideUtil;
    private List<MainPageViewPageAdapter.Buy> lists = new ArrayList<>();
    private int totalmoney;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glideUtil = new GlideUtil();
        updateData();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopcart_recycler, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.shopcart_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new shopcartAdapter(lists));
        updateUI();
        return view;
    }
    public void updateUI(){
        ((ShoppingCartActivity)getActivity()).updateUI(totalmoney,lists.size());
    }
    public void updateData(){
        totalmoney=0;
            Iterator<String> iterator = MainPageViewPageAdapter.buyMap.keySet().iterator();
            while (iterator.hasNext()) {
                String menuId = iterator.next();
                lists.add(MainPageViewPageAdapter.buyMap.get(menuId));
                totalmoney+= MainPageViewPageAdapter.buyMap.get(menuId).getTotalPrice();
            }


    }

    private class ShopCartHolder extends RecyclerView.ViewHolder {
        private ImageView menuImageView;
        private TextView menuName;
        private TextView perprice;
        private TextView number;

        public ShopCartHolder(View itemView) {
            super(itemView);
            menuImageView = (ImageView) itemView.findViewById(R.id.shop_cart_imageview1);
            menuName = (TextView) itemView.findViewById(R.id.shop_cart_textview1);
            perprice = (TextView) itemView.findViewById(R.id.shop_cart_textview3);
            number = (TextView) itemView.findViewById(R.id.shop_cart_textview4);
        }

        public void bindHolder(String url, String name, int perPrice, int num) {
            if(url!=null&&url!="")
            glideUtil.attach(menuImageView).injectImageWithNull(url);
            else
                menuImageView.setImageResource(R.mipmap.qiudaoyu);

            menuName.setText(name);
            perprice.setText("￥"+perPrice+".00");
            number.setText("×"+num);
        }
    }

    private class shopcartAdapter extends RecyclerView.Adapter<ShopCartHolder> {
        private List<MainPageViewPageAdapter.Buy> mbuyList = new ArrayList<>();

        public shopcartAdapter(List<MainPageViewPageAdapter.Buy> buyList) {
            mbuyList = buyList;
        }

        @Override
        public ShopCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.shopcart_item, parent, false);
            return new ShopCartHolder(view);
        }

        @Override
        public void onBindViewHolder(ShopCartHolder holder, int position) {
            MainPageViewPageAdapter.Buy buy = mbuyList.get(position);
            holder.bindHolder(buy.getCookDetail().getRecipe().getImg(), buy.getCookDetail().getName(),
                    buy.getPerPrice(), buy.getNumber());
        }

        @Override
        public int getItemCount() {
            return mbuyList.size();
        }
    }
}
