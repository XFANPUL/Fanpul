package com.example.administrator.Fanpul.ui.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.example.administrator.Fanpul.R;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by whj on 2017/4/24.
 */

public class LineDialog extends Dialog{
    Context context;
    private TextView ordered_desk_info;


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    private String tableSize;
    private TextView ordered_wait_time;
    private Long time;


    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    private int tableNumber;
    public LineDialog(Context context) {
        super(context);
        this.context=context;
        this.setContentView(R.layout.line_up);
    }

    public LineDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
        this.setContentView(R.layout.line_up);
        ordered_desk_info = (TextView)findViewById(R.id.ordered_desk_info);

        ordered_wait_time = (TextView)findViewById(R.id.ordered_wait_time);


        }

        public void updateUI(){
            ordered_desk_info.setText("您的桌型为"+tableSize+"型，您前面还有"+tableNumber+"桌");

            ordered_wait_time.setText("预计等待时间"+time+"分钟");

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView(R.layout.line_up);
        }
}
