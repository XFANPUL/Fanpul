package com.example.administrator.Fanpul.ui.fragment;

import android.support.v4.app.Fragment;
public class FragmentManager{
    public static Fragment getFragment(String title){
        if(title.equals("排队")) {
            return QueueFragment.CreateFragment();
        }
        else if(title.equals("菜谱")){
            return  CookBookFragment.CreateFragment();
        }
        else if(title.equals("店铺")){
           return   ShopFragment.CreateFragment();
        }
        else if(title.equals("待评论")){
            PreCommentFragment preCommentFragment = PreCommentFragment.CreateFragment();
            preCommentFragment.setmTitle(title);
            return preCommentFragment;
        }
        else if(title.equals("排队中")){
            QueuingFragment queuingFragment=QueuingFragment.CreateFragment();
            queuingFragment.setmTitle(title);
            return queuingFragment;
        }
        return  new Fragment();
    }
}


