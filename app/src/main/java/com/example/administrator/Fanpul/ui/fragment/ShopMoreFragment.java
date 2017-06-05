package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class ShopMoreFragment extends BaseFragment {
    @Bind(R.id.shop_comment_list)
    RecyclerView shopCommentRy;
    @Override
    protected Presenter getPresenter() {
        return null;
    }

    public static Fragment CreateFragment(){
        return new ShopMoreFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_more_comment;
    }

    private CommentAdapter commentAdapter;

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         shopCommentRy.setLayoutManager(new LinearLayoutManager(getActivity()));
         Restaurant restaurant = ((SegmentTabActivity) getActivity()).getRestaurant();
         DBConnection.getEvaluateRestaurant(restaurant, new DBQueryCallback<EvaluateRestaurant>() {
            @Override
            public void Success(List<EvaluateRestaurant> bmobObjectList) {
                commentAdapter = new CommentAdapter(bmobObjectList);
                shopCommentRy.setAdapter(commentAdapter);
            }

            @Override
            public void Failed() {

            }
        });

    }

     class CommentAdapter extends RecyclerView.Adapter<CommentHolder>{

         private List<EvaluateRestaurant> evaluates = new ArrayList<>();

         public CommentAdapter(List<EvaluateRestaurant> e) {
             evaluates = e;
         }
        @Override
        public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(R.layout.shop_more_comment_content,parent,false);
            CommentHolder holder = new CommentHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(CommentHolder holder, int position) {
            holder.bindData(evaluates.get(position));
        }

        @Override
        public int getItemCount() {
            return evaluates.size();
        }
    }

     class CommentHolder extends RecyclerView.ViewHolder{
         private TextView userNameText;
         private TextView commentTimeText;
         private RatingBar commentRatBar;
         private TextView commentContent;
        public CommentHolder(View itemView) {
            super(itemView);
            userNameText = (TextView)itemView.findViewById(R.id.shop_comment_username);
            commentTimeText = (TextView)itemView.findViewById(R.id.shop_comment_time);
            commentRatBar = (RatingBar)itemView.findViewById(R.id.shop_comment_ratBarShop);
            commentContent = (TextView)itemView.findViewById(R.id.shop_comment_content);
        }

        public void bindData(EvaluateRestaurant e){
            userNameText.setText(e.getUserName());
            commentTimeText.setText(e.getCreatedAt().substring(0,10));
            commentRatBar.setRating(getValue(e));
            commentContent.setText(e.getTextEvaluate());
        }
    }

    public float getValue(EvaluateRestaurant e){
        float avg = (e.getMenusScore()+e.getServiceScore()+e.getSpeedScore())/3;
        int i_avg = Math.round(avg);
        return i_avg;
    }
}
