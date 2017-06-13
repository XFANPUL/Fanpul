package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateMenu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.adapter.EvaluateOrderAdapter;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.example.administrator.Fanpul.utils.ToastUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

//评价订单的activity
public class EvaluateOrderActivity extends BaseActivity {

    public static final String ORDER = "com.example.administrator.Fanpul.ui.activity.EvaluateOrderActivity.ORDER";

    @Bind(R.id.recyview_eva_menu)
    public RecyclerView recyclerView;  //订单的通用recyclerView
    @Bind(R.id.et_feedback)
    public EditText et_feedback;  //评论

    @Bind(R.id.edit_num)
    public TextView edit_num;   //字数

    @Bind(R.id.ratBarMenu)
    public RatingBar ratBarMenu;      //商品评分

    @Bind(R.id.ratBarMenuScore)
    public TextView ratBarMenuScore;

    @Bind(R.id.ratBarSpeed)
    public RatingBar ratBarSpeed;      //上菜速度评分

    @Bind(R.id.ratBarSpeedScore)
    public TextView ratBarSpeedScore;

    @Bind(R.id.ratBarServer)
    public RatingBar ratBarServer;      //服务评分

    @Bind(R.id.ratBarServerScore)
    public TextView ratBarServerScore;

    @Bind(R.id.eva_shop_image)
    public ImageView eva_shop_image;

    @Bind(R.id.tvbtn_submit)
    public Button tvbtn_submit;
    private float serverScore;
    private float menuScore;
    private float speedScore;
    private String comment;
    private Restaurant restaurant;
    private Order order;
    private EvaluateOrderAdapter evaluateOrderAdapter;
    private Map<Menu, Integer> evaluateMenuMap = new HashMap<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_order;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @OnClick(R.id.tvbtn_submit)
    public void tvbtnClick(View v) {
        saveEvaluateRestaurant();
        saveEvaluateMenu();
        updateOrder();
        finish();

    }

    public void saveEvaluateMenu() {
       evaluateMenuMap = evaluateOrderAdapter.getEvaluateMenuMap();
        Iterator<Menu> it = evaluateMenuMap.keySet().iterator();
        while (it.hasNext()) {
            Menu menu = it.next();
            Integer i = evaluateMenuMap.get(menu);
            EvaluateMenu evaluateMenu = new EvaluateMenu();
            evaluateMenu.setMenu(menu);
            evaluateMenu.setLike(i);
            evaluateMenu.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {

                }
            });
        }
    }

    public void saveEvaluateRestaurant() {
        EvaluateRestaurant evaluateRestaurant = new EvaluateRestaurant();
        evaluateRestaurant.setServiceScore(serverScore);
        evaluateRestaurant.setMenusScore(menuScore);
        evaluateRestaurant.setSpeedScore(speedScore);
        comment = et_feedback.getText().toString();
        if (comment != null && !comment.equals("")) {
            evaluateRestaurant.setTextEvaluate(comment);
        }
        evaluateRestaurant.setUserName(getString(R.string.user_name));
        evaluateRestaurant.setRestaurant(restaurant);
        evaluateRestaurant.setPrice(order.getTotalPrice());
        evaluateRestaurant.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
    }


    public void updateOrder() {
        order.setEvaluateState(1);
        order.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    OrdersTabActivity.startActivity(EvaluateOrderActivity.this, 2);
                }

            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        order = (Order) getIntent().getSerializableExtra(ORDER);
        initRatingBar();
        initFeedback();
        initRecyclerView();
        initShopImage();
    }

    public void initRecyclerView() {
        List<Menu> menus = order.getMenuList();
        evaluateOrderAdapter = new EvaluateOrderAdapter(this, R.layout.match_eva_list_menu, menus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(evaluateOrderAdapter);
    }

    public void initShopImage() {
        String restaurantName = order.getRestaurantName();
        DBProxy.proxy.queryRestaurantByName(restaurantName, new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                GlideUtil.getGlideUtil().attach(eva_shop_image).injectImageWithNull(result.getIconfile().getUrl());
                restaurant = result;
            }

            @Override
            public void Failed() {

            }
        });
    }

    public void initRatingBar() {
        //星级评分表
        //店铺
        ratBarMenu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                menuScore = ratBarMenu.getRating();
                ratBarMenuScore.setText(menuScore + "");
            }
        });

        //上菜速度
        ratBarSpeed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                speedScore = ratBarSpeed.getRating();
                ratBarSpeedScore.setText(speedScore + "");
            }
        });

        //服务质量
        ratBarServer.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                serverScore = ratBarServer.getRating();
                ratBarServerScore.setText(serverScore + "");
            }
        });
    }

    public void initFeedback() {
        et_feedback.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_feedback.setGravity(Gravity.TOP);
        et_feedback.setSingleLine(false);
        et_feedback.setHorizontallyScrolling(false);
        et_feedback.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }

        public void afterTextChanged(Editable s) {
            editStart = et_feedback.getSelectionStart();
            editEnd = et_feedback.getSelectionEnd();
            //判断有没有超过字数
            if (temp.length() >= 100) {
                ToastUtil.showToast(EvaluateOrderActivity.this, getString(R.string.toast_msg_no_more_words));
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                et_feedback.setText(s);
                et_feedback.setSelection(tempSelection);
            } else {
                //更改总字数
                int sum = temp.length();
                edit_num.setText(sum + "/100");
            }
        }
    };

    public static void startActivity(Context context, Order order) {
        Intent i = new Intent(context, EvaluateOrderActivity.class);
        i.putExtra(ORDER, order);
        context.startActivity(i);
    }
}

