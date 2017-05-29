package com.example.administrator.Fanpul.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateOrderActivity extends AppCompatActivity {

    private List<Map<String, Object>> list  ;  //ListView 装订单中的menu用的
    private Map<String,Object> map ;

    //@ViewInject(R.id.list_eva_menu)
    private ListView list_eva_menu;  //订单的通用ListView

    //@ViewInject(R.id.et_feedback)
    private EditText et_feedback;  //评论

  //  @ViewInject(R.id.edit_num)
    private TextView edit_num;   //字数
    private Button tvbtn_submit;  //提交

    private RatingBar ratBarMenu;      //商品评分
    private TextView ratBarMenuScore;
    float menuScore;
    private RatingBar ratBarSpeed;      //上菜速度评分
    private TextView ratBarSpeedScore;
    float speedScore;
    private RatingBar ratBarServer;      //服务评分
    private TextView ratBarServerScore;
    float serverScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_order);

        list_eva_menu=(ListView)findViewById(R.id.list_eva_menu);  //订单的通用ListView
        et_feedback=(EditText)findViewById(R.id.et_feedback);
        edit_num = (TextView)findViewById(R.id.edit_num);
        tvbtn_submit=(Button)findViewById(R.id.tvbtn_submit);

        list =this.getMenuList();

        EvaMenuListAdapter eml_adapter = new EvaMenuListAdapter(EvaluateOrderActivity.this,list);
        list_eva_menu.setAdapter(eml_adapter);

        //星级评分表
        //店铺
        ratBarMenu = (RatingBar)findViewById(R.id.ratBarMenu);
        ratBarMenuScore =(TextView)findViewById(R.id.ratBarMenuScore);

        ratBarMenu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                menuScore =  ratBarMenu.getRating();
                ratBarMenuScore.setText(menuScore + "");
            }
        });

        //上菜速度
        ratBarSpeed = (RatingBar)findViewById(R.id.ratBarSpeed);
        ratBarSpeedScore =(TextView)findViewById(R.id.ratBarSpeedScore);

        ratBarSpeed.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                speedScore =  ratBarSpeed.getRating();
                ratBarSpeedScore.setText(speedScore + "");
            }
        });

        //服务质量
        ratBarServer = (RatingBar)findViewById(R.id.ratBarServer);
        ratBarServerScore =(TextView)findViewById(R.id.ratBarServerScore);

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


        //listView里的不会写
        //提交

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart ;
        private int editEnd ;
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            temp = s;
        }
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {
        }

        public void afterTextChanged(Editable s) {
            editStart =  et_feedback.getSelectionStart();
            editEnd =  et_feedback.getSelectionEnd();
            //判断有没有超过字数
            if (temp.length() >= 100) {
                Toast.makeText(EvaluateOrderActivity.this, "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT).show();
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
    public List<Map<String, Object>> getMenuList(){

        list = new ArrayList<Map<String, Object>>();
        //从数据库读
        String[] names={"红烧狮子头","宫保鸡丁","水煮肉片","嫩牛五方","鱼香肉丝"};
        for (int i = 0; i < names.length; i++) {
            map = new HashMap<String, Object>();
            map.put("menu_name", names[i]);
            list.add(map);
        }
        return list;
    }
}


//适配器
class EvaMenuListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, Object>> list;


    public EvaMenuListAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

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
        HolderViewEvaMenu holderViewem = null;

        if (view == null) {
            holderViewem = new HolderViewEvaMenu();
            view = LayoutInflater.from(context).inflate(R.layout.match_eva_list_menu, parent, false);
            ViewUtils.inject(holderViewem, view);
            view.setTag(holderViewem);
        } else {
            holderViewem = (HolderViewEvaMenu) view.getTag();
        }
        holderViewem.eva_menu_name.setText((String) list.get(position).get("menu_name"));

        return view;
    }
}


class HolderViewEvaMenu{
    @ViewInject(R.id.eva_menu_name)    //订单里每道菜的名字
    TextView eva_menu_name;


    @ViewInject(R.id.eva_menu_image)    //订单里菜的图片
    ImageView eva_menu_image;
}
