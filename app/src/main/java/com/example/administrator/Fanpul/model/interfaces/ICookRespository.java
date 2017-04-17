package com.example.administrator.Fanpul.model.interfaces;

import com.example.administrator.Fanpul.model.entity.CookEntity.subscriberEntity.CategorySubscriberResultInfo;
import com.example.administrator.Fanpul.model.entity.CookEntity.subscriberEntity.SearchCookMenuSubscriberResultInfo;

import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface ICookRespository {

    Observable<CategorySubscriberResultInfo> getCategoryQuery();
    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(final String cid, final int page, final int size);
    Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(final String name, final int page, final int size);
}
