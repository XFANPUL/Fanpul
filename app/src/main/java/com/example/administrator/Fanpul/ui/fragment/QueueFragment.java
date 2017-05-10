package com.example.administrator.Fanpul.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;
import com.example.administrator.Fanpul.model.bmob.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Eating;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.presenter.Presenter;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.OrdersTabActivity;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.component.dialog.LineDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class QueueFragment extends BaseFragment {   //排队的fragment

    @Bind(R.id.want_book)
    public Button showed_wb;
    @Bind(R.id.want_line)
    public Button showed_wl;
    @Bind(R.id.radiogroup)
    public RadioGroup group;
    @Bind(R.id.waitA)
    public TextView waitA_Info;
    @Bind(R.id.waitB)
    public TextView waitB_Info;
    @Bind(R.id.waitC)
    public TextView waitC_Info;

    private int smallTableLeft;  //小桌剩余量，为负数时表示有人排队
    private int middleTableLeft;//中桌桌剩余量
    private int bigTableLeft; //大桌剩余量
    private Restaurant restaurant;//从主页面传过来的，主要得到商店名
    private Handler responseHander; //主线程的handler，用于更新UI
    public static final int MESSAGE_UPDATE = 0;//handler接收的消息类型

    public static final String RES_NAME = "com.example.administrator.Fanpul.ui.fragment.QueueFragment.RES_NAME";
    public static final String TABLE_NUMBER = "com.example.administrator.Fanpul.ui.fragment.QueueFragment.TABLE_NUMBER";
    public static final String TABLE_SIZE = "com.example.administrator.Fanpul.ui.fragment.QueueFragment.TABLE_SIZE";
    private Handler mRequestHandler; //请求的handler,在子线程中
    private HandlerThread reqHandlerThread;//子线程中的HandlerThread

    private String tableSize;//当前桌号类型
    private int curTableNum;//当前桌的A或B或C类型桌号的数量
    private Long curTableTime;

    private boolean isUpdate;//判断当前fragment生命周期是否结束，结束时停止实时跟新

    private Integer preWaitTime;//预计等待时间

    @Override
    protected Presenter getPresenter() {
        return null;
    }

    public static QueueFragment CreateFragment() {
        return new QueueFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.line_simple_card;
    }

    @Override
    public void onPause() {
        isUpdate = false;
        if (mRequestHandler != null && mRequestHandler.hasMessages(MESSAGE_UPDATE)) {
            mRequestHandler.removeMessages(MESSAGE_UPDATE);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        isUpdate = true;
        if (tableSize != null && !mRequestHandler.hasMessages(MESSAGE_UPDATE)) {
            mRequestHandler.sendEmptyMessage(MESSAGE_UPDATE);
        }
        super.onResume();
    }


    @Override
    public void onDestroy() {//销毁fragment和handler，因为fragment和handler生命周期不一致，所以将他们同时销毁
        if (reqHandlerThread != null)
            reqHandlerThread.quit();
        if (reqHandlerThread != null) {
            mRequestHandler = null;
            //mRequestHandler.removeCallbacksAndMessages(null);
        }
        if (responseHander != null) {
            responseHander.removeCallbacksAndMessages(null);
            responseHander = null;
        }

        super.onDestroy();
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        restaurant = ((SegmentTabActivity) getActivity()).getRestaurant();
        responseHander = new Handler();

        String bql = "select * from Restaurant where restaurantName = '" + restaurant.getRestaurantName() + "'";
        BmobUtil.queryBmobObject(bql, new BmobQueryCallback<Restaurant>() {
            @Override
            public void Success(List<Restaurant> bmobObjectList) {
                final Restaurant restaurant1 = bmobObjectList.get(0);
                BmobUtil.queryQueueByResName(restaurant1.getRestaurantName(), new BmobQueryCallback<Queue>() {
                    @Override
                    public void Success(List<Queue> bmobObjectList) {
                        tableSize = "A";
                        updateUI(bmobObjectList, restaurant1);
                        init();
                    }

                    @Override
                    public void Failed() {

                    }
                });

            }

            @Override
            public void Failed() {
                Log.i("Failed", "Failed");
            }
        });
        //绑定数据
        //为两个按钮设置监听器

        showed_wb.setOnClickListener(new View.OnClickListener() {  //book我要点单
            @Override
            public void onClick(View v) {

                BmobUtil.RemoveRestaurantTableIndex(restaurant.getRestaurantName(), tableSize,
                        new OneObjectCallBack<Integer>() {
                            @Override
                            public void Success(Integer tableNumber) {
                                Eating eating = new Eating();
                                eating.setRestaurantName(restaurant.getRestaurantName());
                                eating.setUserName("张三");
                                eating.setTableNumber(tableNumber);
                                eating.setTableSize(tableSize);
                                eating.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });

                                Intent intent = new Intent(getActivity(), OrderMenuActivity.class);
                                intent.putExtra(RES_NAME, restaurant.getRestaurantName());
                                intent.putExtra(TABLE_SIZE, tableSize);
                                intent.putExtra(TABLE_NUMBER, tableNumber);
                                startActivity(intent);
                            }

                            @Override
                            public void Failed() {

                            }
                        });
            }

        });

        showed_wl.setOnClickListener(new View.OnClickListener() {  //line我要排队
            @Override
            public void onClick(View v) {
                final LineDialog dialog = new LineDialog(getActivity(), R.style.LineUpDialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTableSize(tableSize);
                dialog.setTableNumber(Math.abs(curTableNum));
                dialog.setTime(curTableTime);

                dialog.show();
                dialog.updateUI();
                TextView submit_btn_dialog = (TextView) dialog.findViewById(R.id.submit_btn_dialog);
                TextView back_btn_dialog = (TextView) dialog.findViewById(R.id.back_btn_dialog);
                submit_btn_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Queue queue = new Queue();
                        queue.setRestaurantName(restaurant.getRestaurantName());
                        queue.setUserName("张三");
                        queue.setOrder(true);
                        queue.setTableSize(tableSize);
                        queue.setOrder(false);
                        queue.setArrived(false);
                        queue.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {

                                    OrdersTabActivity.startActivity(getActivity(), 0);
                                } else
                                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                back_btn_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toaster.showLongToast("返回");
                        dialog.dismiss();
                    }
                });

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
                switch (radioButtonId) {
                    case R.id.deskA: {
                        tableSize = "A";
                        if (smallTableLeft > 0) {   //前面无人排队
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        } else {
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                            curTableNum = smallTableLeft;
                        }
                        break;
                    }
                    case R.id.deskB: {
                        tableSize = "B";
                        if (middleTableLeft > 0) {   //前面无人排队
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        } else {
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                            curTableNum = middleTableLeft;
                        }
                        break;
                    }
                    case R.id.deskC: {
                        tableSize = "C";
                        if (bigTableLeft > 0) {   //前面无人排队
                            showed_wb.setVisibility(View.VISIBLE);
                            showed_wl.setVisibility(View.GONE);
                        } else {
                            showed_wb.setVisibility(View.GONE);
                            showed_wl.setVisibility(View.VISIBLE);
                            curTableNum = bigTableLeft;
                        }
                        break;
                    }
                }


            }
        });

    }

    public void init() {
        reqHandlerThread = new HandlerThread("UPDATE");
        reqHandlerThread.start();
        mRequestHandler = new Handler(reqHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == MESSAGE_UPDATE) {
                    String bql = "select * from Restaurant where restaurantName = '" + restaurant.getRestaurantName() + "'";
                    BmobUtil.queryBmobObject(bql, new BmobQueryCallback<Restaurant>() {
                        @Override
                        public void Success(List<Restaurant> bmobObjectList) {
                            final Restaurant restaurant1 = bmobObjectList.get(0);
                            BmobUtil.queryQueueByResName(restaurant1.getRestaurantName(), new BmobQueryCallback<Queue>() {
                                @Override
                                public void Success(List<Queue> bmobObjectList) {
                                    checkForUpdate(bmobObjectList, restaurant1);
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
                    if (isUpdate)
                        mRequestHandler.sendEmptyMessageDelayed(MESSAGE_UPDATE, 1500);

                }
            }
        };
        mRequestHandler.sendEmptyMessage(MESSAGE_UPDATE);

    }

    public void checkForUpdate(final List<Queue> queues, final Restaurant restaurant1) {
        responseHander.post(new Runnable() {
            @Override
            public void run() {
                updateUI(queues, restaurant1);
            }
        });
    }


    public void updateUI(List<Queue> bmobObjectList, Restaurant restaurant1) {
        int ATableNumber = 0;
        int BTableNumber = 0;
        int CTableNumber = 0;
        for (int i = 0; i < bmobObjectList.size(); i++) {
            if (bmobObjectList.get(i).getTableSize().equals("A")) {
                ATableNumber++;
            } else if (bmobObjectList.get(i).getTableSize().equals("B")) {
                BTableNumber++;
            } else if (bmobObjectList.get(i).getTableSize().equals("C")) {
                CTableNumber++;
            }
        }

        smallTableLeft = restaurant1.getSmallTableLeft().size() - ATableNumber;
        middleTableLeft = restaurant1.getMiddleTableLeft().size() - BTableNumber;
        bigTableLeft = restaurant1.getBigTableLeft().size() - CTableNumber;

        if (smallTableLeft > 0) {
            waitA_Info.setText("当前无人等待，可以直接点单");
            if (tableSize.equals("A")) {
                showed_wb.setVisibility(View.VISIBLE);
                showed_wl.setVisibility(View.GONE);
            }
        } else {
            //waitA_Info.setText("有" + Math.abs(smallTableLeft) + "桌在等待，预计等待时间为"+8+"分钟");
           BmobUtil.getEatingByIndex(Math.abs(smallTableLeft% restaurant.getSmalltableNum()),"A",restaurant, new OneObjectCallBack<Long>() {
                @Override
                public void Success(Long result) {
                    waitA_Info.setText("有" + Math.abs(smallTableLeft) + "桌在等待，预计等待时间为"+result+"分钟");
                    if(tableSize.equals("A")){
                        curTableTime = result;
                    }
                }

                @Override
                public void Failed() {

                }
            });
            if (tableSize.equals("A")) {
                showed_wb.setVisibility(View.GONE);
                showed_wl.setVisibility(View.VISIBLE);
                curTableNum = smallTableLeft;
            }

        }
        if (middleTableLeft > 0) {
            waitB_Info.setText("当前无人等待，可以直接点单");
            if (tableSize.equals("B")) {
                showed_wb.setVisibility(View.VISIBLE);
                showed_wl.setVisibility(View.GONE);
            }
        } else {
            BmobUtil.getEatingByIndex(Math.abs(middleTableLeft% restaurant.getMiddletableNum()),"B",restaurant, new OneObjectCallBack<Long>() {
                @Override
                public void Success(Long result) {
                    if(tableSize.equals("B")){
                        curTableTime = result;
                    }
                    waitB_Info.setText("有" + Math.abs(middleTableLeft) + "桌在等待，预计等待时间为"+result+"分钟");
                }

                @Override
                public void Failed() {

                }
            });
            //waitB_Info.setText("有" + Math.abs(smallTableLeft) + "桌在等待，预计等待时间为"+8+"分钟");
            if (tableSize.equals("B")) {
                showed_wb.setVisibility(View.GONE);
                showed_wl.setVisibility(View.VISIBLE);
                curTableNum = middleTableLeft;
            }
        }
        if (bigTableLeft > 0) {
            waitC_Info.setText("当前无人等待，可以直接点单");
            if (tableSize.equals("C")) {
                showed_wb.setVisibility(View.VISIBLE);
                showed_wl.setVisibility(View.GONE);
            }
        } else {
            BmobUtil.getEatingByIndex(Math.abs(bigTableLeft%restaurant.getBigtableNum()),"C",restaurant, new OneObjectCallBack<Long>() {
                @Override
                public void Success(Long result) {
                    if(tableSize.equals("C")){
                        curTableTime = result;
                    }
                    waitC_Info.setText("有" + Math.abs(bigTableLeft) + "桌在等待，预计等待时间为"+result+"分钟");
                }

                @Override
                public void Failed() {

                }
            });
           // waitC_Info.setText("有" + Math.abs(smallTableLeft) + "桌在等待，预计等待时间为"+8+"分钟");
            if (tableSize.equals("C")) {
                showed_wb.setVisibility(View.GONE);
                showed_wl.setVisibility(View.VISIBLE);
                curTableNum = bigTableLeft;
            }
        }
    }



}
