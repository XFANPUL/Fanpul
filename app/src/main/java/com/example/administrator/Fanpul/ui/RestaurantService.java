package com.example.administrator.Fanpul.ui;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.bmob.BmobQueryCallback;
import com.example.administrator.Fanpul.model.bmob.BmobUtil;

import com.example.administrator.Fanpul.model.bmob.OneObjectCallBack;

import com.example.administrator.Fanpul.model.entity.bmobEntity.Eating;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Queue;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.OrderMenuActivity;
import com.example.administrator.Fanpul.ui.activity.OrdersTabActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


import cn.bmob.v3.BmobQuery;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by Administrator on 2017/5/7 0007.
 */

public class RestaurantService extends Service {

    public static final String QUEUE = "com.example.administrator.Fanpul.ui.RestaurantService.QUEUE";
    private String tableSize;
    public RestaurantBinder binder = new RestaurantBinder();
    private static RestaurantService restaurantService;
    private ServiceCallback serviceCallback;

    public ServiceCallback getServiceCallback() {
        return serviceCallback;
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }

    public interface ServiceCallback{
        void UpdateUI();
    }
    public void resetServiceCallback(){
        serviceCallback = null;
    }

    public static RestaurantService getRestaurantService() {
        if(restaurantService!=null)
        return restaurantService;
        else
            return  null;
    }

    public static void setRestaurantService(RestaurantService restaurantService) {
        RestaurantService.restaurantService = restaurantService;
    }

    public class RestaurantBinder  extends  Binder{
        public RestaurantService getService(){
            return  RestaurantService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public static Context contexts;

    public static Intent newIntent(Context context){
        contexts = context;
        Intent intent = new Intent(context,RestaurantService.class);
        return  intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        initDatabase();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;

    }


    BmobRealTimeData timeData = new BmobRealTimeData();
    //监控数据库
    protected void initDatabase() {
        timeData.start( new ValueEventListener() {

            @Override
            public void onConnectCompleted(Exception e) {
                if (timeData.isConnected()) {
                    timeData.subTableUpdate("Restaurant");
                }
            }

            @Override
            public void onDataChange(JSONObject arg0) {
                // TODO Auto-generated method stub
                if (BmobRealTimeData.ACTION_UPDATETABLE.equals(arg0.optString("action"))) {    //数据库操作动作

                        //用户用餐完毕释放桌位，数据库表Restaurant中smallTableLeft改变增加桌号
                        //进入此函数
                        //得到餐厅id,餐厅名,大小中桌的第一个桌号
                        JSONObject data = arg0.optJSONObject("data");    //得到改变的一行餐厅数据
                        String restaurantId = data.optString("objectId");
                        String restaurantName = data.optString("restaurantName");

                        JSONArray arraySmall = data.optJSONArray("smallTableLeft");
                        JSONArray arrayMiddle = data.optJSONArray("middleTableLeft");
                        JSONArray arrayBig = data.optJSONArray("bigTableLeft");
                        final String smallTable = arraySmall.optString(0);
                        final String middleTable = arrayMiddle.optString(0);
                        final String bigTable = arrayBig.optString(0);
                        final String tableNumber;
                        if (!smallTable.isEmpty()) {
                            tableSize = "A";
                            tableNumber = smallTable;
                        } else if (!middleTable.isEmpty()) {
                            tableSize = "B";
                            tableNumber = middleTable;
                        } else {
                            tableSize = "C";
                            tableNumber=bigTable;
                        }
                        //判断排队队列中是否有该餐厅正在排队的用户
                        // 若有，判断是否是本机用户，若是则将桌号分配给自己，排队成功
                        // Restaurant restaurant=new Restaurant();
                        //判断排队队列中是否有该餐厅正在排队的用户
                        String bql = "select * from Restaurant where restaurantName = '" + restaurantName + "'";
                        BmobUtil.queryBmobObject(bql, new BmobQueryCallback<Restaurant>() {
                            @Override
                            public void Success(List<Restaurant> bmobObjectList) {
                                final Restaurant restaurant1 = bmobObjectList.get(0);
                                BmobUtil.queryQueueByResNameAndTableSize(restaurant1.getRestaurantName(), tableSize, new BmobQueryCallback<Queue>() {
                                    @Override
                                    public void Success(List<Queue> bmobObjectList) {
                                        //判断队列中的最早的Queue是否是本用户
                                        if (bmobObjectList.size() > 0) {
                                            int left=0;
                                            if(tableSize.equals("A")){
                                                left = restaurant1.getSmallTableLeft().size();
                                            }else if(tableSize.equals("B")){
                                                left = restaurant1.getMiddleTableLeft().size();
                                            }else if(tableSize.equals("C")){
                                                left = restaurant1.getBigTableLeft().size();
                                            }
                                            if (left>0) {
                                                Queue queue = bmobObjectList.get(0);
                                                if (queue != null) {
                                                    if ("张三".equals(queue.getUserName())) {
                                                        //最早的排队用户为本用
                                                        Toast.makeText(RestaurantService.this, "用户张三排队成功,桌号为：" + tableSize + tableNumber,
                                                                Toast.LENGTH_SHORT).show();
                                                        // showMultiBtnDialog("张三",tableSize+tableNumber,restaurant1.getRestaurantName());
                                                        showNotification(queue, tableSize, Integer.parseInt(tableNumber));
                                                    }
                                                }
                                            }
                                        }

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
                    }

            }
        });

    }

    protected void showNotification(final Queue queue, final String tableSize, final Integer tableNumber){
        final boolean isOrder = queue.isOrder();


      /*  BmobUtil.deleteEatingByTableNumber(tableNumber, tableSize, queue.getRestaurantName(), new OneObjectCallBack() {
            @Override
            public void Success(Object result) {

            }

            @Override
            public void Failed() {

            }
        });*/


        BmobUtil.RemoveRestaurantTableNumber(queue.getRestaurantName(), tableSize, tableNumber, new BmobQueryCallback() {
            @Override
            public void Success(List bmobObjectList) {
                if(queue.isOrder())
                BmobUtil.updateOrderEvaluate1(queue.getMyOrder());

                Eating eating = new Eating();
                eating.setUserName("张三");
                eating.setTableSize(tableSize);
                eating.setTableNumber(tableNumber);
                eating.setRestaurantName(queue.getRestaurantName());
                eating.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {


                        if(isOrder){

                            Intent intent = OrdersTabActivity.newIntent(RestaurantService.this,1);
                            PendingIntent pi = PendingIntent.getActivity(RestaurantService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            Bitmap largeIcon = ((BitmapDrawable) getResources().getDrawable(R.drawable.icon_my_order)).getBitmap();

                            Notification notification;
                            notification = new NotificationCompat.Builder(RestaurantService.this)
                                    .setTicker("排队成功")
                                    .setSmallIcon(R.drawable.icon_my_order)
                                    .setContentTitle("排队成功")
                                    .setContentText("您已排队成功，您的桌号是"+tableSize+tableNumber)
                                    .setLargeIcon(largeIcon)
                                    .setContentIntent(pi)
                                    .setAutoCancel(true)
                                    .build();
                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(RestaurantService.this);
                            notificationManagerCompat.notify(0,notification);


                            queue.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(RestaurantService.this,"DeleteSuccess",Toast.LENGTH_SHORT).show();

                                         if(serviceCallback!=null){
                                             serviceCallback.UpdateUI();
                                         }

                                    } else {
                                        Toast.makeText(RestaurantService.this,"DeleteF",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                     }
                   else{

                        BmobUtil.updateQueueIsArrive(queue, tableNumber, new OneObjectCallBack() {
                            @Override
                            public void Success(Object result) {
                                if(serviceCallback!=null){
                                    serviceCallback.UpdateUI();
                                }
                                Intent intent = new Intent(RestaurantService.this, OrderMenuActivity.class);
                                intent.putExtra(QUEUE,queue);
                                PendingIntent pi = PendingIntent.getActivity(RestaurantService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                Bitmap largeIcon = ((BitmapDrawable) getResources().getDrawable(R.drawable.icon_my_order)).getBitmap();
                                Notification notification;
                                notification = new NotificationCompat.Builder(RestaurantService.this)
                                        .setTicker("排队成功")
                                        .setSmallIcon(R.drawable.icon_my_order)
                                        .setContentTitle("排队成功")
                                        .setContentText("您已排队成功，您的桌号是"+tableSize+tableNumber+",你还未点餐，是否现在点餐？")
                                        .setLargeIcon(largeIcon)
                                        .setContentIntent(pi)
                                        .setAutoCancel(true)
                                        .build();
                                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(RestaurantService.this);
                                notificationManagerCompat.notify(0,notification);
                            }

                            @Override
                            public void Failed() {

                            }
                        });
                      }



                    }
                });



            }

            @Override
            public void Failed() {
                Toast.makeText(RestaurantService.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }


    /*protected void showMultiBtnDialog(String username,String table,String restaurant){
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(contexts);
        normalDialog.setIcon(R.drawable.icon_my_order);
        normalDialog.setTitle("用户"+username+"排队成功").setMessage("店铺:"+restaurant+"\n桌号:"+table);
        normalDialog.setPositiveButton("点单",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNeutralButton("确认",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNegativeButton("放弃", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // ...To-do
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }*/
}
