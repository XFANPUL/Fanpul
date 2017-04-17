package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

public class OrdersListAdapter extends BaseAdapter {

	
    private Context context;
	private List<Map<String, Object>> list;
	private String order_type;
	
	
	public OrdersListAdapter(Context context, List<Map<String, Object>> list, String order_type) {
		this.context = context;
		this.list=list;
		this.order_type=order_type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		//不同的订单状态加载不同的layout
		if(order_type.equals("排队中")) {
			HolderViewOrderLists1 holderViewod = null;

			if (view == null) {
				holderViewod = new HolderViewOrderLists1();
				view = LayoutInflater.from(context).inflate(R.layout.match_orders_list_card_inline, parent, false);

				ViewUtils.inject(holderViewod, view);
				view.setTag(holderViewod);
			} else {
				holderViewod = (HolderViewOrderLists1) view.getTag();
			}
			holderViewod.shop_name_inline.setText((String) list.get(position).get("shop_name"));
		}else if(order_type.equals("待评论")){

			HolderViewOrderLists2 holderViewod2 = null;

			if (view == null) {
				holderViewod2 = new HolderViewOrderLists2();
				view = LayoutInflater.from(context).inflate(R.layout.match_orders_list_card_toeva, parent, false);

				ViewUtils.inject(holderViewod2, view);
				view.setTag(holderViewod2);
			} else {
				holderViewod2 = (HolderViewOrderLists2) view.getTag();
			}

			holderViewod2.shop_name_toeva.setText((String) list.get(position).get("shop_name"));

		}else{
			HolderViewOrderLists1 holderViewod1 = null;

			if (view == null) {
				holderViewod1 = new HolderViewOrderLists1();
				view = LayoutInflater.from(context).inflate(R.layout.match_orders_list_card_inline, parent, false);


				ViewUtils.inject(holderViewod1, view);
				view.setTag(holderViewod1);
			} else {
				holderViewod1 = (HolderViewOrderLists1) view.getTag();
			}

			holderViewod1.shop_name_inline.setText((String) list.get(position).get("shop_name"));
		}
		return view;
	}


}

class HolderViewOrderLists1 {    //排队中
@ViewInject(R.id.orderedshop_inline_name)
TextView shop_name_inline;

}

class HolderViewOrderLists2 {    //待评价
@ViewInject(R.id.ordered_toeva_name)
TextView shop_name_toeva;

}

class HolderViewOrderLists3{    //历史的


}