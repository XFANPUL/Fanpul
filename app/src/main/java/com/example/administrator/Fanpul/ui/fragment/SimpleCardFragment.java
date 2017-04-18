package com.example.administrator.Fanpul.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("ValidFragment")

public class SimpleCardFragment extends Fragment {
    private String mTitle;

    private List<Map<String, Object>> list ;  //ListView 装数据用的
    private Map<String,Object> map ;

    private Restaurant restaurant; //餐馆

    public SimpleCardFragment() {
    }

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment(); //取得和标题相符合的SimpleCardFragments
        sf.mTitle = title;
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurant = (Restaurant) getActivity().getIntent().getSerializableExtra(SegmentTabActivity.SEGMENTTAB_RESTAURANT);

        if (mTitle.equals("排队")) {
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.line_simple_card, container,false);
            TextView card_title_tv = (TextView) v.findViewById(R.id.test_line);
            Button button = (Button)v.findViewById(R.id.order_menu_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(getActivity(), OrderMenuActivity.class);
                    intent.putExtra(ZxingFragment.TAG_ZXING,restaurant.getRestaurantName());
                    startActivity(intent);
                }
            });
            card_title_tv.setText(mTitle);
            return v;
        }
        else if(mTitle.equals("菜谱")){
            View v = inflater.inflate(R.layout.menu_cook_book_fragment,container,false);
            final RecyclerView recyclerViewCategory =(RecyclerView) v.findViewById(R.id.recyview_cook_book_category);
            final RecyclerView recyclerViewCookBook = (RecyclerView)v.findViewById(R.id.recyview_cook_book_menu);
            recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewCookBook.setLayoutManager(new LinearLayoutManager(getActivity()));
            BmobUtil.queryRestaurantCategory(restaurant.getRestaurantName(), new BmobQueryCallback<MenuCategory>() {
                @Override
                public void Success(List<MenuCategory> bmobObjectList) {
                    recyclerViewCategory.setAdapter(new CategoryAdapter(bmobObjectList,recyclerViewCookBook));
                }

                @Override
                public void Failed() {
                       Log.i("Failed","Failed");
                }
            });
            return v;
        }
        else if (mTitle.equals("店铺")) {
            View v = inflater.inflate(R.layout.shopinfo_simple_card,container,false);
            TextView shopinner_shopnameText = (TextView)v.findViewById(R.id.shopinner_shopname);
            shopinner_shopnameText.setText(restaurant.getRestaurantName());
            TextView shopinner_sale = (TextView)v.findViewById(R.id.shopinner_sale);
            shopinner_sale.setText("月售"+restaurant.getOrder());
            TextView  shopinner_score =(TextView) v.findViewById(R.id.shopinner_score);
            shopinner_score.setText("评分"+restaurant.getScore());
            TextView shopinner_description = (TextView)v.findViewById(R.id.shopinner_description) ;
            shopinner_description.setText(restaurant.getDescription());
            return v;
        }else if(mTitle.equals("排队中")){
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.orders_list_card, null);
             ListView orders_listView = (ListView) v.findViewById(R.id.orders_list);  //订单的通用ListView
            list = this.get_orders_inline_data();
            OrdersListAdapter od_adapter = new OrdersListAdapter(getActivity(), list, mTitle);//新建适配器
            orders_listView.setAdapter(od_adapter);//绑定适配器

            return v;
        }else if (mTitle.equals("待评论")){
            //根据传入不同的title，设置不同的xml显示
            View v = inflater.inflate(R.layout.orders_list_card, null);
            ListView orders_listView = (ListView) v.findViewById(R.id.orders_list);
            list = this.get_orders_to_eva_data();
            OrdersListAdapter od_adapter = new OrdersListAdapter(getActivity(), list, mTitle);//新建适配器
            orders_listView.setAdapter(od_adapter);//绑定适配器
            return v;
        }else{
            View v = inflater.inflate(R.layout.line_simple_card, null);
            TextView card_title_tv = (TextView) v.findViewById(R.id.test_line);
            card_title_tv.setText(mTitle);
            return v;
        }
     }
     private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         private TextView categoryText;
         private RecyclerView recyclerViewCookBook;
         private MenuCategory menuCategory;
         public CategoryHolder(View itemView,RecyclerView recyclerView) {
             super(itemView);
             itemView.setOnClickListener(CategoryHolder.this);
             categoryText = (TextView)itemView.findViewById(R.id.cook_book_category);
             recyclerViewCookBook = recyclerView;
         }
         public void bindData(MenuCategory category){
               categoryText.setText(category.getCategoryName());
               menuCategory = category;
         }

         @Override
         public void onClick(View v) {
              BmobUtil.queryMenuByCategory(menuCategory, new BmobQueryCallback<Menu>() {
                  @Override
                  public void Success(List<Menu> bmobObjectList) {
                      recyclerViewCookBook.setAdapter(new MenuCookAdapter(bmobObjectList));
                  }

                  @Override
                  public void Failed() {

                  }
              });
         }
     }

     private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{
         private List<MenuCategory> menuCategoryList;
         private RecyclerView recyclerViewCookBook;
         public CategoryAdapter(List<MenuCategory> menuCategories, RecyclerView recyclerViewCookBook){
             menuCategoryList = menuCategories;
             this.recyclerViewCookBook = recyclerViewCookBook;
         }
         @Override
         public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
             View view = layoutInflater.inflate(R.layout.cook_book_category_item,parent,false);
             return new CategoryHolder(view,recyclerViewCookBook);
         }

         @Override
         public void onBindViewHolder(CategoryHolder holder, int position) {
                holder.bindData(menuCategoryList.get(position));
         }

         @Override
         public int getItemCount() {
             return menuCategoryList.size();
         }
     }

     private class MenuCookHolder extends RecyclerView.ViewHolder{
         private ImageView cookbookImg;
         private TextView cookbookName;
         public MenuCookHolder(View itemView) {
             super(itemView);
             cookbookImg = (ImageView)itemView.findViewById(R.id.img_cook_book);
             cookbookName = (TextView)itemView.findViewById(R.id.text_name_book);
         }

         public void bindData(String url,String name){
             new GlideUtil().attach(cookbookImg).injectImageWithNull(url);
             cookbookName.setText(name);
         }
     }

     private class MenuCookAdapter extends  RecyclerView.Adapter<MenuCookHolder>{
            List<Menu> menuList;
          public MenuCookAdapter(List<Menu> menus){
                 menuList = menus;
          }
         @Override
         public MenuCookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
             View v = layoutInflater.inflate(R.layout.menu_cook_item,parent,false);
             return new MenuCookHolder(v);
         }

         @Override
         public void onBindViewHolder(MenuCookHolder holder, int position) {
             holder.bindData(menuList.get(position).getImgUrl(),menuList.get(position).getMenuName());
         }

         @Override
         public int getItemCount() {
             return menuList.size();
         }
     }

    public List<Map<String, Object>> get_orders_inline_data(){

        String[] names={"红烧狮子头","宫保鸡丁","水煮肉片","嫩牛五方","鱼香肉丝"};

        list = new ArrayList<Map<String, Object>>();


        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("shop_name", names[i]);
            list.add(map);
        }
        return list;
    }

    public List<Map<String, Object>> get_orders_to_eva_data(){

        String[] names={"红烧狮子头","宫保鸡丁","水煮肉片","嫩牛五方","鱼香肉丝"};

        list = new ArrayList<Map<String, Object>>();


        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("shop_name", names[i]);
            list.add(map);
        }
        return list;
    }
}


