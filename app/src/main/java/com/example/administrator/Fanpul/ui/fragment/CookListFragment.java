package com.example.administrator.Fanpul.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.Fanpul.IView.ICookListView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.entity.CookEntity.CookDetail;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.presenter.CookListPresenter;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.CookCategoryActivity;
import com.example.administrator.Fanpul.ui.activity.CookCollectionListActivity;
import com.example.administrator.Fanpul.ui.activity.ShoppingCartActivity;
import com.example.administrator.Fanpul.ui.adapter.CookListAdapter;
import com.example.administrator.Fanpul.ui.adapter.MainPageViewPageAdapter;
import com.example.administrator.Fanpul.ui.component.fab_transformation.FabTransformation;
import com.example.administrator.Fanpul.ui.component.twinklingrefreshlayout.Footer.BottomProgressView;
import com.example.administrator.Fanpul.ui.component.twinklingrefreshlayout.RefreshListenerAdapter;
import com.example.administrator.Fanpul.ui.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.example.administrator.Fanpul.ui.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;
import com.example.administrator.Fanpul.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CookListFragment extends BaseFragment implements ICookListView {

    @Bind(R.id.refresh_layout)
    public TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    @Bind(R.id.view_overlay)
    public View viewOverlay;
    @Bind(R.id.fab_app)
    public FloatingActionButton floatingActionButton;
/*    @Bind(R.id.view_sheet)
    public View viewSheet;*/

    private CookListAdapter cookListAdapter;

    //private TB_CustomCategory customCategoryData;
    private CookListPresenter cookListPresenter;

    @Override
    public void onResume() {
        recyclerList.setAdapter(cookListAdapter);
        cookListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected Presenter getPresenter(){
        return cookListPresenter;
    }

    @Override
    protected int getLayoutId(){
        return R.layout.fragment_cook_list;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(buyMap.isEmpty()) {
            floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            floatingActionButton.setEnabled(false);
        }
        recyclerList.setLayoutManager(new LinearLayoutManager(recyclerList.getContext()));
        cookListAdapter = new CookListAdapter(getActivity());
        recyclerList.setAdapter(cookListAdapter);

        BezierLayout headerView = new BezierLayout(getActivity());
        headerView.setRoundProgressColor(getResources().getColor(R.color.colorPrimary));
        headerView.setWaveColor(getResources().getColor(R.color.main_indicator_bg));
        headerView.setRippleColor(getResources().getColor(R.color.white));
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setWaveHeight(140);

        BottomProgressView bottomProgressView = new BottomProgressView(twinklingRefreshLayout.getContext());
        bottomProgressView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
        twinklingRefreshLayout.setBottomView(bottomProgressView);
        twinklingRefreshLayout.setOverScrollBottomShow(true);

        final ArrayList<Menu> datas = new ArrayList<>();
        cookListAdapter.setDataList(datas);

        cookListPresenter = new CookListPresenter(getActivity(), this);
        //cookListPresenter.updateRefreshCookMenuByID(customCategoryData.getCtgId());
        cookListPresenter.updateRefreshCookMenuCategory(menuCategory);
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                //cookListPresenter.updateRefreshCookMenuByID(customCategoryData.getCtgId());
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                //cookListPresenter.loadMoreCookMenuByID(customCategoryData.getCtgId());
            }
        });
    }

/*    @OnClick(R.id.fab_app)
    public void onClickFabApp(){
        if (floatingActionButton.getVisibility() == View.VISIBLE) {
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay).transformTo(viewSheet);
        }
    }*/
    public static final String restaurantNameIntent = "com.example.administrator.cookman.ui.fragment.CookListFragment";

    public static final String TABLESIZE = "com.example.administrator.cookman.ui.fragment.CookListFragment.TABLESIZE";
    public static final String TABLENUM = "com.example.administrator.cookman.ui.fragment.CookListFragment.TABLENUM";
    @OnClick(R.id.fab_app)
    public void onClickBuy(){
        if(!buyMap.isEmpty()) {
            MenuOrderFragment main = (MenuOrderFragment) getFragmentManager().findFragmentById(R.id.fragment_main_content);
            Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
            intent.putExtra(restaurantNameIntent, main.getRestaurantName());
            intent.putExtra(TABLESIZE,main.getTableSize());
            intent.putExtra(TABLENUM,main.getTableNum());
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(),"购物车不能为空",Toast.LENGTH_SHORT).show();
        }
        /*onClickOverlay();*/
    }

   /* @OnClick(R.id.view_overlay)
    public void onClickOverlay() {
        closeFabMenu();
    }

    @OnClick(R.id.relative_category)
    public void onClickCategory(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Category);

        CookCategoryActivity.startActivity(getActivity());
        onClickOverlay();
    }

    @OnClick(R.id.txt_title_collection)
    public void onClickCollection(){
        MobclickAgent.onEvent(getActivity(), Constants.Umeng_Event_Id_Collection_See);

        CookCollectionListActivity.startActivity(getActivity());
        onClickOverlay();
    }
*/
    /********************************************************************************************/
    public void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list){
        //twinklingRefreshLayout.finishRefreshing();

        //cookListAdapter.setDataList(conversion(list));
       // cookListAdapter.notifyDataSetChanged();
    }



    @Override
    public void onMenuListUpdateRefreshSuccess(List<Menu> list) {
        twinklingRefreshLayout.finishRefreshing();
        cookListAdapter.setDataList(list);
        cookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCookListUpdateRefreshFaile(String msg){
        twinklingRefreshLayout.finishRefreshing();
        ToastUtil.showToast(getActivity(), msg);
    }

    @Override
    public void onCookListLoadMoreSuccess(ArrayList<CookDetail> list) {
       // twinklingRefreshLayout.finishLoadmore();
       // cookListAdapter.addItems(conversion(list));
    }

    @Override
    public void onMenuListLoadMoreSuccess(List<Menu> list){
        twinklingRefreshLayout.finishLoadmore();
        cookListAdapter.addItems(list);
    }

    @Override
    public void onCookListLoadMoreFaile(String msg){
        twinklingRefreshLayout.finishLoadmore();
        ToastUtil.showToast(getActivity(), msg);
    }
    /********************************************************************************************/

   /* private List<Menu> conversion(ArrayList<Menu> list){
        List<Menu> datas = new ArrayList<>();
        for(Menu item : list){
            datas.add(item);
        }

        return datas;
    }*/

/*    public boolean closeFabMenu(){
        if (floatingActionButton.getVisibility() != View.VISIBLE) {
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay).transformFrom(viewSheet);
            return true;
        }

        return false;
    }*/

    /*public void setCustomCategoryData(TB_CustomCategory customCategoryData){
        this.customCategoryData = customCategoryData;
    }*/
    private MenuCategory menuCategory;
    public void setMenuCategory(MenuCategory menuCategory){
        this.menuCategory = menuCategory;
    }
    private Map<String,MainPageViewPageAdapter.Buy> buyMap=new HashMap<>();
    public void setBuyMap(Map<String,MainPageViewPageAdapter.Buy> buyList){
        this.buyMap = buyList;
    }
    public MainPageViewPageAdapter.Buy getBuy(String menuId){
            if(buyMap.get(menuId)!=null){
                return buyMap.get(menuId);
            }
            else{
                return new MainPageViewPageAdapter.Buy();
            }
    }

    public void ReduceCurrentNum(String menuId,int number){
        //Toast.makeText(getActivity(),"减少成功"+ buyMap.get(menuId).getCookDetail().getMenuId(),Toast.LENGTH_SHORT).show();

        if(number==0){
            buyMap.remove(menuId);
            if (buyMap.isEmpty()){
                floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                floatingActionButton.setEnabled(false);
                updatePos();
            }
        }
    }
    public void AddCurrentNum(String menuId, MainPageViewPageAdapter.Buy buy){
       int number = buy.getNumber();
        if(number == 1){
            buyMap.put(menuId,buy);
            floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            floatingActionButton.setEnabled(true);
            updatePos();
        }
        //Toast.makeText(getActivity(),"添加成功"+buy.getCookDetail().getMenuId(),Toast.LENGTH_SHORT).show();
    }

    public void updatePos(){
        MenuOrderFragment menuOrderFragment = (MenuOrderFragment)getFragmentManager().findFragmentById(R.id.fragment_main_content);
        int pos = menuOrderFragment.getMainPageViewPageAdapter().getCurPosition();
        if(  pos>0&& pos<  menuOrderFragment.getMainPageViewPageAdapter().getCount()-1) {
            menuOrderFragment.getMainPageViewPageAdapter().updateUI(pos + 1);
            menuOrderFragment.getMainPageViewPageAdapter().updateUI(pos - 1);
        }
        else if(pos==0){
            menuOrderFragment.getMainPageViewPageAdapter().updateUI(pos + 1);
        }
        else if(pos==menuOrderFragment.getMainPageViewPageAdapter().getCount()-1){
            menuOrderFragment.getMainPageViewPageAdapter().updateUI(pos - 1);
        }
    }

    public void updateUI(){
        if(buyMap.isEmpty()) {
            floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
            floatingActionButton.setEnabled(false);
        }
        else{
            floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            floatingActionButton.setEnabled(true);
        }
    }
}
