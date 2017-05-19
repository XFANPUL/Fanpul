package com.example.administrator.Fanpul.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.Fanpul.IView.ICookListView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.CookEntity.subscriberEntity.SearchCookMenuSubscriberResultInfo;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.model.interfaces.ICookRespository;
import com.example.administrator.Fanpul.model.respository.CookRespository;
import com.example.administrator.Fanpul.utils.ErrorExceptionUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/20.
 */

public class CookListPresenter extends Presenter{

    private ICookListView iCookListView;
    private ICookRespository iCookRespository;
    private int curPage = 1;
    private int totalPages = 1;

    public CookListPresenter(Context context, ICookListView iCookListView){
        super(context);

        this.iCookListView = iCookListView;
        this.iCookRespository = CookRespository.getInstance();
    }
    public void updateRefreshCookMenuCategory(MenuCategory menuCategory){
        curPage = 1;
        BmobUtil.queryMenuByCategory(menuCategory, new BmobQueryCallback<Menu>() {
            @Override
            public void Success(List<Menu> bmobObjectList) {
                iCookListView.onMenuListUpdateRefreshSuccess(bmobObjectList);
            }

            @Override
            public void Failed() {
                //Toast.makeText(context,"查询失败",Toast.LENGTH_SHORT).show();
                Log.i("Failed","查询菜品失败");
            }
        });
    }

    public void loadMoreMenuByCategory(MenuCategory menuCategory){
        BmobUtil.queryMenuByCategory(menuCategory, new BmobQueryCallback() {
            @Override
            public void Success(List bmobObjectList) {

            }

            @Override
            public void Failed() {

            }
        });
    }

    public void destroy(){
        if(updateRefreshCookMenuByIDSubscriber != null){
            updateRefreshCookMenuByIDSubscriber.unsubscribe();
        }

        if(loadMoreCookMenuByIDSubscriber != null){
            loadMoreCookMenuByIDSubscriber.unsubscribe();
        }
    }

    public void updateRefreshCookMenuByID(String cid){
        curPage = 1;
        rxJavaExecuter.execute(
                iCookRespository.searchCookMenuByID(cid, curPage, Constants.Per_Page_Size)
                , updateRefreshCookMenuByIDSubscriber = new UpdateRefreshCookMenuByIDSubscriber()
        );
    }


    public void loadMoreCookMenuByID(String cid){
        curPage++;
        if(curPage > totalPages){
            curPage--;
            if(iCookListView != null)
                iCookListView.onCookListLoadMoreFaile(context.getString(R.string.toast_msg_no_more_data));
            return ;
        }

        rxJavaExecuter.execute(
                iCookRespository.searchCookMenuByID(cid, curPage, Constants.Per_Page_Size)
                , loadMoreCookMenuByIDSubscriber = new LoadMoreCookMenuByIDSubscriber()
        );
    }

    private LoadMoreCookMenuByIDSubscriber loadMoreCookMenuByIDSubscriber;
    private class LoadMoreCookMenuByIDSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(loadMoreCookMenuByIDSubscriber != null){
                loadMoreCookMenuByIDSubscriber.unsubscribe();
            }

            if(iCookListView != null)
                iCookListView.onCookListLoadMoreFaile(e.getMessage());

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){

            if(iCookListView != null)
                iCookListView.onCookListLoadMoreSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }

    private UpdateRefreshCookMenuByIDSubscriber updateRefreshCookMenuByIDSubscriber;
    private class UpdateRefreshCookMenuByIDSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(updateRefreshCookMenuByIDSubscriber != null){
                updateRefreshCookMenuByIDSubscriber.unsubscribe();
            }

            if(iCookListView != null)
                iCookListView.onCookListUpdateRefreshFaile(ErrorExceptionUtil.getErrorMsg(e));

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){
            totalPages = data.getResult().getTotal();

            if(iCookListView != null)
                iCookListView.onCookListUpdateRefreshSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }
}
