package com.example.administrator.Fanpul.ui.adapter;
import android.content.Context;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import java.util.List;

public class OrdersListAdapter {
    public static final String QUEUE = "com.example.administrator.Fanpul.ui.adapter.OrdersListAdapter.QUEUE";

    public QueuingListAdapter CreateQueuingListAdapter(Context context, List<Queue> list) {
        return new QueuingListAdapter(context, R.layout.match_orders_list_card_inline, list);
    }

    public PreCommentListAdapter CreatePreCommentListAdapter(Context context, List<Order> list) {
        return new PreCommentListAdapter(context, R.layout.match_orders_list_card_toeva, list);
    }

    public HistoryListAdapter CreateHistoryListAdapter(Context context, List<Order> list) {
        return new HistoryListAdapter(context, R.layout.match_orders_list_card_toeva, list);
    }
}