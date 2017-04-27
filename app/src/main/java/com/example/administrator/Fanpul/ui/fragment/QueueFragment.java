package com.example.administrator.Fanpul.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class QueueFragment extends BaseFragment {   //排队的fragment
    @Bind(R.id.want_book)
    public Button showed_wb;
    @Bind(R.id.want_line)
    public Button showed_wl;
    @Bind(R.id.radiogroup)
    public RadioGroup group ;

    private int waitA_test ;  //排队的人 A
    private int waitB_test ;
    private int waitC_test ;
    @Override
    protected Presenter getPresenter() {
        return null;
    }
    public static QueueFragment CreateFragment(){
        return  new QueueFragment();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.line_simple_card;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String bql = "select * from Restaurant where restaurantName = '"+((SegmentTabActivity)getActivity()).getRestaurant().getRestaurantName()+"'";
        BmobUtil.queryBmobObject(bql,new BmobQueryCallback<Restaurant>() {

            @Override
            public void Success(List<Restaurant> bmobObjectList) {
                Restaurant restaurant1 = bmobObjectList.get(0);

                waitA_test=restaurant1.getSmalltableNum()-restaurant1.getSmallTableLeft().size();
                waitB_test=restaurant1.getMiddletableNum()-restaurant1.getMiddleTableLeft().size();
                waitC_test=restaurant1.getBigtableNum()-restaurant1.getBigTableLeft().size();


                TextView waitA_Info = (TextView)getActivity().findViewById(R.id.waitA);
                TextView waitB_Info = (TextView)getActivity().findViewById(R.id.waitB);
                TextView waitC_Info = (TextView)getActivity().findViewById(R.id.waitC);

                if(waitA_test>=0) {
                    waitA_Info.setText("当前无人等待，可以直接点单");
                }else{
                    //从排队表里读吧
                    waitA_Info.setText("有" + waitA_test + "桌在等待，预计等待时间为8分钟");
                }

                if(waitB_test>=0) {
                    waitB_Info.setText("当前无人等待，可以直接点单");
                }else{
                    //从排队表里读吧
                    waitB_Info.setText("有" + waitB_test + "桌在等待，预计等待时间为8分钟");
                }

                if(waitC_test>=0) {
                    waitC_Info.setText("当前无人等待，可以直接点单");
                }else{
                    //从排队表里读吧
                    waitC_Info.setText("有" + waitC_test + "桌在等待，预计等待时间为8分钟");
                }

            }

            @Override
            public void Failed() {
                Log.i("Failed","Failed");
            }
        });
        //绑定数据
        if(waitA_test>0){
            showed_wb.setVisibility(View.GONE);
            showed_wl.setVisibility(View.VISIBLE);
        }else{
            showed_wb.setVisibility(View.VISIBLE);
            showed_wl.setVisibility(View.GONE);
        }

        //为两个按钮设置监听器
        showed_wb.setOnClickListener(new View.OnClickListener() {  //book我要点单
            @Override
            public void onClick(View v) {
                                                 /*Intent intent = new Intent(getActivity(), OrderMenuActivity.class);
                                                 intent.putExtra(ZxingFragment.TAG_ZXING, restaurant.getRestaurantName());
                                                 startActivity(intent);*/
            }
        });

        showed_wl.setOnClickListener(new View.OnClickListener() {  //line我要排队
            @Override
            public void onClick(View v) {

                   /* Intent intent = new Intent(getActivity(), OrderMenuActivity.class);
                    intent.putExtra(ZxingFragment.TAG_ZXING, restaurant.getRestaurantName());
                    startActivity(intent);*/
            }
        });


        //绑定一个匿名监听器
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                //获取变更后的选中项的ID
                int radioButtonId = arg0.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) getActivity().findViewById(radioButtonId);

                //根据选中的button的数值来判断显示什么按钮 排队/点单

                Button showed_wb = (Button) getActivity().findViewById(R.id.want_book);
                Button showed_wl = (Button) getActivity().findViewById(R.id.want_line);

                switch(radioButtonId){
                    case R.id.deskA: {
                        if(waitA_test>0) {   //前面有人排队
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                        }else{
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        }
                        break;
                    }
                    case R.id.deskB: {
                        if(waitB_test>0) {   //前面有人排队
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                        }else{
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        }
                        break;
                    }
                    case R.id.deskC: {
                        if(waitC_test>0) {   //前面有人排队
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                        }else{
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        }
                        break;
                    }
                }


            }
        });

    }
}
