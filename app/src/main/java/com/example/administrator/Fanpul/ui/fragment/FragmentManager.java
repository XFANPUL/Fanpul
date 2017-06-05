package com.example.administrator.Fanpul.ui.fragment;

import android.support.v4.app.Fragment;

public class FragmentManager {
    public static Fragment getFragment(String title) {
        if (title.equals("排队")) {
            return QueueFragment.CreateFragment();
        } else if (title.equals("菜谱")) {
            return CookBookFragment.CreateFragment();
        } else if (title.equals("店铺")) {
            return ShopFragment.CreateFragment();
        } else if (title.equals("待评论")) {
            PreCommentFragment preCommentFragment = PreCommentFragment.CreateFragment();
            return preCommentFragment;
        } else if (title.equals("排队中")) {
            QueuingFragment queuingFragment = QueuingFragment.CreateFragment();
            return queuingFragment;
        }else if(title.equals("更多")){
           return ShopMoreFragment.CreateFragment();
        }else if(title.equals("历史的")){
            return HistoryOrderFragment.CreateFragment();
        }
        return new Fragment();
    }
}


