package com.example.administrator.Fanpul.ui.adapter;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.bmob.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.RestaurantService;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.OrderedMenuActivity;
import com.example.administrator.Fanpul.ui.activity.SeeOrderDetailActivity;
import com.example.administrator.Fanpul.ui.fragment.QueuingFragment;
import com.example.administrator.Fanpul.utils.GlideUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;

public class OrdersListAdapter {
    public static final String QUEUE = "com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter.QUEUE";

    public QueuingListAdapter CreateQueuingListAdapter(Context context, List<Queue> list) {
        return new QueuingListAdapter(context, list);
    }

    public PreCommentListAdapter CreatePreCommentListAdapter(Context context, List<Order> list) {
        return new PreCommentListAdapter(context, list);
    }

    class QueuingListAdapter extends RecyclerView.Adapter<QueuingHolder> { //排队中的适配器
        private Context context;  //上下文,一般是activity
        private List<Queue> list = new ArrayList<>(); //排队的list
        public static final int MESSAGE0 = 0;

      /*  private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MESSAGE0:
                        QueuingHolder holder = (QueuingHolder)msg.obj;
                        holder.updateWaitTime(msg.arg1);
                        break;
                }
            }
        };*/


        public List<Queue> getList() {
            return list;
        }

        public void remove(Queue queue) {
            for (int i = 0; i < list.size(); i++) {
                if (queue.getObjectId().equals(list.get(i).getObjectId())) {
                    list.remove(i);
                }
            }
            QueuingListAdapter.this.notifyDataSetChanged();
        }


        public QueuingListAdapter(Context context, List<Queue> lists) {
            this.context = context;
            this.list.addAll(lists);
        }


        @Override
        public QueuingHolder onCreateViewHolder(ViewGroup parent, int viewType) { //创建排队的Holder
            LayoutInflater layoutInflater = LayoutInflater.from(context); //获得context的布局,也可以说是容器
            //向context容器中插入一个视图，并得到这个view
            View v = layoutInflater.inflate(R.layout.match_orders_list_card_inline, parent, false);
            //返回一个Holder，这将传到下面的onBindViewHolder
            return new QueuingHolder(v);
        }

        @Override
        public void onBindViewHolder(final QueuingHolder holder, final int position) {
            //调用holder中的bindHolder，更新holder视图
            holder.bindHolder(list.get(position));
             /* Runnable mRunnale=new Runnable() {
                    @Override
                    public void run() {
                    while(true){
                        try{
                            Thread.sleep(60000);
                            BmobUtil.queryRestaurantByName(list.get(position).getRestaurantName(), new OneObjectCallBack<Restaurant>() {
                                @Override
                                public void Success(Restaurant result) {
                                    int index = 0;
                                    if(list.get(position).getTableSize().equals("A")){
                                        index = position%result.getSmalltableNum();
                                    }else if(list.get(position).getTableSize().equals("B")){
                                        index = position%result.getMiddletableNum();
                                    }else if(list.get(position).getTableSize().equals("C")){
                                        index = position%result.getBigtableNum();
                                    }
                                    BmobUtil.getEatingByIndex(index, list.get(position).getTableSize(), result, new OneObjectCallBack<Long>() {
                                        @Override
                                        public void Success(Long result) {
                                            Message message = Message.obtain();
                                            message.what = MESSAGE0;
                                            message.arg1=result.intValue();
                                            message.obj =holder;
                                            handler.sendMessage(message);
                                        }

                                        @Override
                                        public void Failed() {

                                        }
                                    });
                                }

                                @Override
                                public void Failed() {

                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            };
            new Thread(mRunnale).start();*/
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class QueuingHolder extends RecyclerView.ViewHolder {    //排队中
        @Bind(R.id.orderedshop_inline_name)
        TextView shop_name_inline; //店铺名的TextView
        @Bind(R.id.orderedshop_inline_img)
        ImageView shopImg;
        @Bind(R.id.ordered_desk_info)
        TextView tableInfo;
        @Bind(R.id.ordered_wait_time)
        TextView ordered_wait_time;
        @Bind(R.id.btn_book)
        Button btn_book;
        @Bind(R.id.cancel_order)
        Button cancel_order;
        @Bind(R.id.btn_mybook)
        Button btn_mybook;
        @Bind(R.id.ser_item)
        TextView ser_item;

        public QueuingHolder(View itemView) {
            super(itemView);//Adapter中new Holder(View)中的view,这里是itemView
            ButterKnife.bind(this, itemView);//可以通过Bind绑定控件
        }
        //更新视图
        public void bindHolder(final Queue queue) {

            shop_name_inline.setText(queue.getRestaurantName());
            BmobUtil.queryRestaurantByName(queue.getRestaurantName(), new OneObjectCallBack<Restaurant>() {
                @Override
                public void Success(Restaurant result) {
                    new GlideUtil().attach(shopImg).injectImageWithNull(result.getIconfile().getUrl());
                }

                @Override
                public void Failed() {

                }
            });

            if(!queue.isArrived()) { //没到号时
                BmobUtil.getPreTableNumber(queue, new OneObjectCallBack<Integer>() {
                    @Override
                    public void Success(Integer result) {
                        tableInfo.setText("您的桌型为" + queue.getTableSize() + "型，您前面还有" + result + "桌");
                    }

                    @Override
                    public void Failed() {

                    }
                });
            }else{
                tableInfo.setText("您已到号,您的桌号是"+queue.getTableSize()+queue.getTableNumber());
                btn_book.setText("点单");
                ordered_wait_time.setText("请在5分钟内完成订单");
                ser_item.setVisibility(View.GONE);
                cancel_order.setVisibility(View.GONE);
            }

            btn_book.setOnClickListener(new View.OnClickListener() {///
                @Override
                public void onClick(View v){
                    if(btn_book.getText().toString().equals("点单")){
                        QueuingFragment.cancel(btn_book.getContext());
                    }
                    Intent intent = new Intent(itemView.getContext(), OrderMenuActivity.class);
                    intent.putExtra(OrdersListAdapter.QUEUE, queue);
                    itemView.getContext().startActivity(intent);

                }
            });
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((QueuingListAdapter) ((RecyclerView) itemView.getParent()).getAdapter()).remove(queue);
                    BmobUtil.cancelQueue(queue);

                }
            });
            btn_mybook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderedMenuActivity.startOrderedMenuActivity(itemView.getContext(), queue);
                }
            });
            updateUI(queue.isOrder());
        }

        public void updateUI(boolean isOrder) {
            if (isOrder) {
                btn_book.setVisibility(View.GONE);
                cancel_order.setVisibility(View.GONE);
                btn_mybook.setVisibility(View.VISIBLE);
                ser_item.setVisibility(View.GONE);
            }else{
                btn_book.setVisibility(View.VISIBLE);

                if(!btn_book.getText().toString().equals("点单"))
                cancel_order.setVisibility(View.VISIBLE);

                btn_mybook.setVisibility(View.GONE);
            }
        }
    }


    class PreCommentListAdapter extends RecyclerView.Adapter<PreCommentHolder> {
        private Context context;
        private List<Order> list;

        public PreCommentListAdapter(Context context, List<Order> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public PreCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(R.layout.match_orders_list_card_toeva, parent, false);
            return new PreCommentHolder(v);
        }

        @Override
        public void onBindViewHolder(PreCommentHolder holder, int position) {
            holder.bindHolder(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class PreCommentHolder extends RecyclerView.ViewHolder {    //待评论的RecyclerView.Holder
        @Bind(R.id.ordered_toeva_name)
        TextView shop_name_inline;
        @Bind(R.id.orderdshop_img)
        ImageView orderdShopImg;
        @Bind(R.id.btn_order_toeva_mybook)
        Button btn_order_toeva_mybook;
        @Bind(R.id.order_toeva_price)
        TextView order_toeva_price;
        @Bind(R.id.ordered_toeva_date)
        TextView ordered_toeva_date;

        public PreCommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(final Order order) {
            shop_name_inline.setText(order.getRestaurantName());
            BmobUtil.queryRestaurantByName(order.getRestaurantName(), new OneObjectCallBack<Restaurant>() {
                @Override
                public void Success(Restaurant result) {
                    new GlideUtil().attach(orderdShopImg).injectImageWithNull(result.getIconfile().getUrl());
                }

                @Override
                public void Failed() {

                }
            });
            order_toeva_price.setText("￥:" + order.getTotalPrice());
            btn_order_toeva_mybook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeeOrderDetailActivity.startActivity(itemView.getContext(), order);
                }
            });

            ordered_toeva_date.setText(order.getOrderDate());

        }
    }

}




