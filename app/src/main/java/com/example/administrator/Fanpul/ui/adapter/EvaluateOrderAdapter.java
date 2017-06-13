package com.example.administrator.Fanpul.ui.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.CommonAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class EvaluateOrderAdapter extends CommonAdapter<Menu>{
    private Map<Menu,Integer> evaluateMenuMap = new HashMap<>();

    public Map<Menu, Integer> getEvaluateMenuMap() {
        return evaluateMenuMap;
    }

    public void setEvaluateMenuMap(Map<Menu, Integer> evaluateMenuMap) {
        this.evaluateMenuMap = evaluateMenuMap;
    }

    private RadioGroup radioGroup;
    public EvaluateOrderAdapter(Context context, int layoutId, List<Menu> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void bindDatas(ViewHolder holder, final Menu menu) {
        holder.setImageByUrl(R.id.eva_menu_image,menu.getImgUrl());
        holder.setText(R.id.eva_menu_name,menu.getMenuName());
        radioGroup = holder.getView(R.id.upada_radiogroup);
        holder.setOnCheckedChangeListener(R.id.upada_radiogroup, new RadioGroup.OnCheckedChangeListener() {
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
        initRadioGroup(menu);

    }

    public void initRadioGroup(Menu menu){
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
    }
}
