package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDao.IMenuDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.MenuCategory;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class MenuDaoImpl implements IMenuDao {
    public MenuDaoImpl(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    //通过菜品类别查找菜品
    public void queryMenuByCategory(MenuCategory menuCategory, final DBQueryCallback callback) { //通过菜品种类查找菜品
        if (Utils.checkNetwork(context)) {
            BmobQuery<Menu> menuBmobQuery = new BmobQuery<Menu>();
            menuBmobQuery.addWhereEqualTo("relation", menuCategory);
            menuBmobQuery.findObjects(new FindListener<Menu>() {
                @Override
                public void done(List<Menu> list, BmobException e) {
                    if (e == null) {
                        callback.Success(list);
                    } else {
                        callback.Failed();
                    }
                }

            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callback.Failed();
        }
    }
}
