package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.bmob.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.SeeOrderDetailActivity;
import com.example.administrator.Fanpul.utils.GlideUtil;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
public class OrdersListAdapter{
	public static final String RES_NAME = "com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter.RES_NAME";
	public static final String TABLE_SIZE = "com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter.TABLE_SIZE";
	public static final String TABLE_NUMBER = "com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter.TABLE_NUMBER";
	public  QueuingListAdapter CreateQueuingListAdapter(Context context, List<Queue> list){
		return  new QueuingListAdapter(context,list);
	}
	public  PreCommentListAdapter CreatePreCommentListAdapter(Context context, List<Order> list){
		return  new PreCommentListAdapter(context,list);
	}
	 class QueuingListAdapter extends RecyclerView.Adapter<QueuingHolder> { //排队中的适配器
		private Context context;  //上下文,一般是activity
		private List<Queue> list; //排队的list

		public QueuingListAdapter(Context context, List<Queue> list) {
			this.context = context;
			this.list=list;
		}


		@Override
		public QueuingHolder onCreateViewHolder(ViewGroup parent, int viewType) { //创建排队的Holder
			LayoutInflater layoutInflater = LayoutInflater.from(context); //获得context的布局,也可以说是容器
			//向context容器中插入一个视图，并得到这个view
			View v = layoutInflater.inflate(R.layout.match_orders_list_card_inline,parent,false);
			//返回一个Holder，这将传到下面的onBindViewHolder
			return new QueuingHolder(v);
		}

		@Override
		public void onBindViewHolder(QueuingHolder holder, int position) {
			//调用holder中的bindHolder，更新holder视图
			holder.bindHolder(list.get(position));
		}

		@Override
		public int getItemCount() {
			return list.size();
		}
	}

	class QueuingHolder extends RecyclerView.ViewHolder {    //排队中
		@Bind(R.id.orderedshop_inline_name)
		TextView shop_name_inline; //店铺名的TextView
		@Bind(R.id.btn_book)
		Button btn_book;
		public QueuingHolder(View itemView) {
			super(itemView);//Adapter中new Holder(View)中的view,这里是itemView
			ButterKnife.bind(this, itemView);//可以通过Bind绑定控件
		}
		//更新视图
		public void bindHolder(final Queue queue){
			shop_name_inline.setText(queue.getRestaurantName());
			btn_book.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(itemView.getContext(), OrderMenuActivity.class);
					intent.putExtra(OrdersListAdapter.RES_NAME, queue.getRestaurantName());
					intent.putExtra(OrdersListAdapter.TABLE_SIZE, queue.getTableSize());
					itemView.getContext().startActivity(intent);
				}
			});
		}
	}


	class PreCommentListAdapter extends RecyclerView.Adapter<PreCommentHolder> {
		private Context context;
		private List<Order> list;

		public PreCommentListAdapter(Context context, List<Order> list) {
			this.context = context;
			this.list=list;
		}

		@Override
		public PreCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View v = layoutInflater.inflate(R.layout.match_orders_list_card_toeva,parent,false);
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
		TextView  order_toeva_price;

		public PreCommentHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindHolder(final Order order){
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
			order_toeva_price.setText("￥:"+order.getTotalPrice());
			btn_order_toeva_mybook.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeeOrderDetailActivity.startActivity(itemView.getContext(),order);
				}
			});

		}
	}

}




