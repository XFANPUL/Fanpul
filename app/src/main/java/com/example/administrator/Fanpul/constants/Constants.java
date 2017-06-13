package com.example.administrator.Fanpul.constants;

import com.example.administrator.Fanpul.R;

/**
 * Created by Administrator on 2017/2/17.
 */

public final class Constants {
    public final static String Common_Tag = "Fanpul";
    public final static String Key_MobAPI_Cook = "1b5cf0085e135";
    public final static int Per_Page_Size = 20;
    public final static String baseURL = "http://apicloud.mob.com/v1/cook/";
    public final static String Cook_Service_CategoryQuery = "category/query";//查询菜谱的所有分类
    public final static String Cook_Service_MenuSearch = "menu/search";//根据标签ID/菜谱名称查询菜谱详情
    public final static String Cook_Parameter_Key = "key";//MobAPI 开发者Key
    public final static String Cook_Parameter_Cid = "cid";//
    public final static String Cook_Parameter_Name = "name";//
    public final static String Cook_Parameter_Page = "page";//
    public final static String Cook_Parameter_Size = "size";//
    public final static String Umeng_Event_Id_Search = "Umeng_Event_Id_Search";
    public final static String INTENT_START_ACTIVITY_MANAGER_OBJECT = "INTENT_START_ACTIVITY_MANAGER_OBJECT";

    public static String[] navSort={"火锅","川湘菜","披萨","儿童套餐","快餐","意面","拉面","韩餐",
            "粤菜","炒饭","寿司","海鲜","烧烤","面点","小炒","甜点",
            "饮品","云吞","小食","煎饼","烹炸","汤包","糕点","凉菜"};

    public static int[] navSortImages={R.drawable.nav_huoguo,R.drawable.nav_chuanxiang,R.drawable.nav_pisa,R.drawable.nav_ertong,
            R.drawable.nav_kuaican,R.drawable.nav_yimian,R.drawable.nav_lamian,R.drawable.nav_hancan,
            R.drawable.nav_yuecai,R.drawable.nav_chaofan,R.drawable.nav_shousi,R.drawable.nav_haixian,
            R.drawable.nav_shaokao,R.drawable.nav_miandian,R.drawable.nav_xiaochao,R.drawable.nav_tiandian,
            R.drawable.nav_yinpin,R.drawable.nav_yuntun,R.drawable.nav_xiaoshi,R.drawable.nav_jianbing,
            R.drawable.nav_pengzha,R.drawable.nav_tangbao,R.drawable.nav_gaodian,R.drawable.nav_liangcai,};

    public static final String BMOB_CONNECTION = "BMOB_CONNECTION";

}
