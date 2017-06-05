package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.DB.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateMenu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.EvaluateRestaurant;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Order;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.example.administrator.Fanpul.utils.ToastUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class EvaluateOrderActivity extends BaseActivity {

    public static final String ORDER = "com.example.administrator.Fanpul.ui.activity.EvaluateOrderActivity.ORDER";

    @Bind(R.id.recyview_eva_menu)
    public RecyclerView recyclerView;  //订单的通用ListView
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

    private Map<Menu,Integer> evaluateMenuMap = new HashMap<>();

    private float serverScore;
    private float menuScore;
    private float speedScore;
    private String comment;
    private Restaurant restaurant;
    private Order order;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_order;
    }

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //星级评分表
        //店铺
        ratBarMenu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                menuScore =  ratBarMenu.getRating();
                ratBarMenuScore.setText(menuScore + "");
            }
        });

        //上菜速度
        ratBarSpeed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                speedScore =  ratBarSpeed.getRating();
                ratBarSpeedScore.setText(speedScore + "");
            }
        });

        //服务质量
        ratBarServer.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                serverScore =  ratBarServer.getRating();
                ratBarServerScore.setText(serverScore + "");
            }
        });

        //editText
        et_feedback.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et_feedback.setGravity(Gravity.TOP);
        et_feedback.setSingleLine(false);
        et_feedback.setHorizontallyScrolling(false);
        et_feedback.addTextChangedListener(mTextWatcher);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        order =(Order)getIntent().getSerializableExtra(ORDER);
        List<Menu> menus = order.getMenuList();
        recyclerView.setAdapter(new EvaluateOrderAdapter(menus));
        String restaurantName =order.getRestaurantName();
        DBConnection.queryRestaurantByName(restaurantName, new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                 new GlideUtil().attach(eva_shop_image).injectImageWithNull(result.getIconfile().getUrl());
                restaurant =result;
            }

            @Override
            public void Failed() {

            }
        });

        tvbtn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateRestaurant evaluateRestaurant = new EvaluateRestaurant();
                if(serverScore!=0){
                    evaluateRestaurant.setServiceScore(serverScore);
                }
                if(menuScore!=0){
                    evaluateRestaurant.setMenusScore(menuScore);
                }
                if(speedScore!=0){
                    evaluateRestaurant.setSpeedScore(speedScore);
                }
                comment = et_feedback.getText().toString();
                if(comment!=null&&!comment.equals("")){
                    evaluateRestaurant.setTextEvaluate(comment);
                }
                evaluateRestaurant.setUserName("张三");
                evaluateRestaurant.setRestaurant(restaurant);
                evaluateRestaurant.setPrice(order.getTotalPrice());
                evaluateRestaurant.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {

                    }
                });

                Iterator<Menu> it = evaluateMenuMap.keySet().iterator();
                while(it.hasNext()){
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
                   order.setEvaluateState(1);
                   order.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            OrdersTabActivity.startActivity(EvaluateOrderActivity.this,2);
                        }

                    }
                });
            }

        });
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            temp = s;
        }
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }
        public void afterTextChanged(Editable s) {
            editStart =  et_feedback.getSelectionStart();
            editEnd =  et_feedback.getSelectionEnd();
            //判断有没有超过字数
            if (temp.length() >= 100) {
                ToastUtil.showToast(EvaluateOrderActivity.this,getString(R.string.toast_msg_no_more_words));
                //Toast.makeText(EvaluateOrderActivity.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
                s.delete(editStart-1, editEnd);
                int tempSelection = editStart;
                et_feedback.setText(s);
                et_feedback.setSelection(tempSelection);
            } else{
                //更改总字数
                int sum = temp.length();
                edit_num.setText(sum+"/100");
            }
        }
    };

    public static void startActivity(Context context,Order order){
        Intent i = new Intent(context,EvaluateOrderActivity.class);
        i.putExtra(ORDER,order);
        context.startActivity(i);
    }

    private class EvaluateHolder extends RecyclerView.ViewHolder{
        private ImageView eva_menu_image;
        private TextView eva_menu_name;
        private RadioGroup radioGroup;
        public EvaluateHolder(View itemView) {
            super(itemView);
            eva_menu_image = (ImageView)itemView.findViewById(R.id.eva_menu_image);
            eva_menu_name = (TextView)itemView.findViewById(R.id.eva_menu_name);
            radioGroup = (RadioGroup)itemView.findViewById(R.id.upada_radiogroup);
        }

        public void bindData(final Menu menu){
            new GlideUtil().attach(eva_menu_image).injectImageWithNull(menu.getImgUrl());
            eva_menu_name.setText(menu.getMenuName());
            if(evaluateMenuMap.get(menu)!=null){
                switch (evaluateMenuMap.get(menu)) {
                    case 0:
                    radioGroup.check(R.id.menu_good);
                        break;
                    case 1:
                        radioGroup.check(R.id.menu_normal);
                        break;
                    case 2:
                        radioGroup.check(R.id.menu_worse);
                        break;
                }
            }else{
                radioGroup.clearCheck();
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId){
                        case R.id.menu_good:
                            evaluateMenuMap.put(menu,0);
                            break;
                        case R.id.menu_normal:
                            evaluateMenuMap.put(menu,1);
                            break;
                        case R.id.menu_worse:
                            evaluateMenuMap.put(menu,2);
                            break;
                    }
                }
            });
        }
    }

    private class EvaluateOrderAdapter extends  RecyclerView.Adapter<EvaluateHolder>{
        List<Menu> menuList;
        public EvaluateOrderAdapter(List<Menu> menus){
            menuList = menus;
        }
        @Override
        public EvaluateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(EvaluateOrderActivity.this);
            View v = layoutInflater.inflate(R.layout.match_eva_list_menu,parent,false);
            return new EvaluateHolder(v);
        }

        @Override
        public void onBindViewHolder(EvaluateHolder holder, int position) {
            holder.bindData(menuList.get(position));
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }
    }

}

