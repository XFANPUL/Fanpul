package com.example.administrator.Fanpul.ui.fragment;

import android.support.v4.app.Fragment;

import com.example.administrator.Fanpul.constants.Constants;

public class FragmentManager {
    public static Fragment getFragment(String title) {
        if (title.equals(Constants.QUEUE)) {
            return QueueFragment.CreateFragment();
        } else if (title.equals(Constants.COOK_BOOK)) {
            return CookBookFragment.CreateFragment();
        } else if (title.equals(Constants.RESTAURANT)) {
            return ShopFragment.CreateFragment();
        } else if (title.equals(Constants.PRECOMMENT)) {
            PreCommentFragment preCommentFragment = PreCommentFragment.CreateFragment();
            return preCommentFragment;
        } else if (title.equals(Constants.QUEUING)) {
            QueuingFragment queuingFragment = QueuingFragment.CreateFragment();
            return queuingFragment;
        }else if(title.equals(Constants.MORE)){
           return ShopMoreFragment.CreateFragment();
        }else if(title.equals(Constants.HISTORY)){
            return HistoryOrderFragment.CreateFragment();
        }
        return new Fragment();
    }
}


