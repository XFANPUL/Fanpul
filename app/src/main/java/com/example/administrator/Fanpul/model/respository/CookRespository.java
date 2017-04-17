package com.example.administrator.Fanpul.model.respository;

import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.entity.CookEntity.subscriberEntity.CategorySubscriberResultInfo;
import com.example.administrator.Fanpul.model.entity.CookEntity.subscriberEntity.SearchCookMenuSubscriberResultInfo;
import com.example.administrator.Fanpul.model.interfaces.ICookRespository;
import com.example.administrator.Fanpul.model.interfaces.ICookService;
import com.example.administrator.Fanpul.model.net.RetrofitService;
import com.google.gson.Gson;

import rx.Observable;

/**
 * Created by Administrator on 2017/2/17.
 */

public class CookRespository implements ICookRespository {

    private static CookRespository Instance = null;

    public static CookRespository getInstance(){
        if(Instance == null)
            Instance = new CookRespository();

        return Instance;
    }

    private Gson gson;
    private CookRespository(){
        gson = new Gson();
    }

    @Override
    public Observable<CategorySubscriberResultInfo> getCategoryQuery(){
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.getCategoryQuery(Constants.Key_MobAPI_Cook);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size){
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.searchCookMenuByID(Constants.Key_MobAPI_Cook, cid, page, size);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size){
        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);
        return iCookService.searchCookMenuByName(Constants.Key_MobAPI_Cook, name, page, size);
    }

}
