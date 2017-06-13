package com.example.administrator.Fanpul.model.DB.BmobDaoImpl;

import android.content.Context;
import android.widget.Toast;

import com.example.administrator.Fanpul.model.DB.IDBCallBack.DBQueryCallback;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.DB.IDao.ICollectionMenuDao;
import com.example.administrator.Fanpul.model.entity.bmobEntity.CollectionMenu;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Menu;
import com.example.administrator.Fanpul.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class CollectionMenuDaoImpl implements ICollectionMenuDao {
    private Context context;

    public CollectionMenuDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public void queryCollectionMenubyUserName(String userName, final DBQueryCallback callback) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
                @Override
                public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                    if (e == null && bmobQueryResult.getResults().size() > 0) {
                        callback.Success(bmobQueryResult.getResults().get(0).getMenuList());
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

    @Override

    public void judgeIsCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
                @Override
                public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            List<Menu> menus = bmobQueryResult.getResults().get(0).getMenuList();
                            for (int i = 0; i < menus.size(); i++) {
                                if (menus.get(i).getObjectId().equals(menu.getObjectId())) {
                                    callBack.Success(true);
                                    return;
                                }
                            }
                        }
                        callBack.Success(false);
                    } else {

                    }

                }

            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }

    }


    @Override
    public void addCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
                @Override
                public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() == 0) {
                            CollectionMenu collectionMenu = new CollectionMenu();
                            collectionMenu.setUserName(userName);
                            List<Menu> arrayList = new ArrayList<>();
                            arrayList.add(menu);
                            collectionMenu.setMenuList(arrayList);
                            collectionMenu.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        callBack.Success(null);
                                    }

                                }
                            });
                        } else {
                            CollectionMenu collectionMenu = bmobQueryResult.getResults().get(0);
                            collectionMenu.getMenuList().add(menu);
                            collectionMenu.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        callBack.Success(null);
                                    }

                                }
                            });
                        }
                    } else {
                        callBack.Failed();
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }

    @Override
    public void deleteCollectionMenu(final String userName, final Menu menu, final OneObjectCallBack callBack) {
        String sql = "Select * from CollectionMenu where userName = '" + userName + "'";
        if (Utils.checkNetwork(context)) {
            new BmobQuery<CollectionMenu>().doSQLQuery(sql, new SQLQueryListener<CollectionMenu>() {
                @Override
                public void done(BmobQueryResult<CollectionMenu> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            CollectionMenu collectionMenu = bmobQueryResult.getResults().get(0);
                            if (collectionMenu.getMenuList().size() > 1) {
                                List<Menu> list = new ArrayList<Menu>();
                                list = collectionMenu.getMenuList();
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getObjectId().equals(menu.getObjectId())) {
                                        list.remove(i);
                                        break;
                                    }
                                }
                                collectionMenu.setMenuList(list);
                                collectionMenu.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            } else {
                                collectionMenu.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                            callBack.Success(true);
                        }
                    }
                }
            });
        } else {
            Toast.makeText(context, "网络状态不好，请检查网络", Toast.LENGTH_SHORT).show();
            callBack.Failed();
        }
    }
}
