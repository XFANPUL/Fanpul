package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.OrderedMenuActivity;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;
import com.example.administrator.Fanpul.ui.fragment.QueuingFragment;

import java.util.List;

import static com.example.administrator.Fanpul.R.id.cancel_order;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class QueuingListAdapter extends CommonAdapter<Queue> {
    public QueuingListAdapter(Context context, int layoutId, List<Queue> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(final ViewHolder holder, final Queue queue) {
           holder.setText(R.id.orderedshop_inline_name ,queue.getRestaurantName() );
           DBProxy.proxy .queryRestaurantByName(queue.getRestaurantName(), new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                holder.setImageByUrl(R.id.orderedshop_inline_img , result.getIconfile().getUrl());
            }
            @Override
            public void Failed() {

            }
        });

        if(!queue.isArrived()) { //没到号时
            DBProxy.proxy .getPreTableNumber(queue, new OneObjectCallBack<Integer>() {
                @Override
                public void Success(Integer result) {
                    holder.setText(R.id.ordered_desk_info,getmContext().getString(R.string.order_desk_info_02,queue.getTableSize(),result));
                }
                @Override
                public void Failed() {

                }
            });
        }else{
            holder.setText(R.id.ordered_desk_info,getmContext().getString(R.string.order_desk_info_01,queue.getTableSize(),queue.getTableNumber()));
            holder.setText(R.id.btn_book,getmContext().getString(R.string.start_order));
            holder.setText(R.id.ordered_wait_time,getmContext().getString(R.string.wait_time_info));
            holder.setVisibility(R.id.ser_item, View.GONE);
            holder.setVisibility(cancel_order,View.GONE);
        }

        holder.setOnClickListener(R.id.btn_book , new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(holder.getText(R.id.btn_book).equals(getmContext().getString(R.string.start_order))){
                    QueuingFragment.cancel(getmContext());
                }
                Intent intent = new Intent(getmContext(), OrderMenuActivity.class);
                intent.putExtra(OrdersListAdapter.QUEUE, queue);
                getmContext().startActivity(intent);

            }
        });
       holder.setOnClickListener(R.id.cancel_order,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(queue);
                DBProxy.proxy .cancelQueue(queue);

            }
        });

        holder.setOnClickListener(R.id.btn_mybook , new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderedMenuActivity.startOrderedMenuActivity(getmContext(), queue);
            }
        });
        updateUI(queue.isOrder(),holder);
    }

    public void updateUI(boolean isOrder,ViewHolder holder) {
        if (isOrder) {
            holder.setVisibility(R.id.btn_book , View.GONE);
            holder.setVisibility(R.id.cancel_order , View.GONE);
            holder.setVisibility(R.id.btn_mybook , View.VISIBLE);
            holder.setVisibility(R.id.ser_item , View.GONE);
        }else{
            holder.setVisibility(R.id.btn_book , View.VISIBLE);
            if(!holder.getText(R.id.btn_book).equals(getmContext().getString(R.string.start_order)))
                holder.setVisibility(R.id.cancel_order , View.VISIBLE);
            holder.setVisibility(R.id.btn_mybook , View.GONE);
        }
    }

}

