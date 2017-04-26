package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.utils.GlideUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class CookBookFragment extends BaseFragment {//菜单fragemnt
    @Bind(R.id.recyview_cook_book_category)
    public RecyclerView recyclerViewCategory ;//菜品类别列表
    @Bind(R.id.recyview_cook_book_menu)
    public RecyclerView recyclerViewCookBook ;//菜品列表
    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.menu_cook_book_fragment;
    }
    public static CookBookFragment CreateFragment(){
        return  new CookBookFragment();
    }
    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCookBook.setLayoutManager(new LinearLayoutManager(getActivity()));
        BmobUtil.queryRestaurantCategory(((SegmentTabActivity)getActivity()).getRestaurant().getRestaurantName(), new BmobQueryCallback<MenuCategory>() {
            @Override
            public void Success(List<MenuCategory> bmobObjectList) {
                recyclerViewCategory.setAdapter(new CategoryAdapter(bmobObjectList,recyclerViewCookBook));
            }

            @Override
            public void Failed() {
                Log.i("Failed","Failed");
            }
        });
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

}
