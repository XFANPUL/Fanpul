package com.example.administrator.Fanpul.ui.fragment;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.constants.Constants;
import com.example.administrator.Fanpul.model.DB.DBConnection;
import com.example.administrator.Fanpul.model.DB.DBQueryCallback;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.activity.SegmentTabActivity;
import com.example.administrator.Fanpul.ui.adapter.baseadapter.BaseRecyclerAdapter;
import com.example.administrator.Fanpul.ui.adapter.holder.CommonHolder;
import com.example.administrator.Fanpul.ui.view.ImageCycleView;
import com.example.administrator.Fanpul.ui.view.WrapContentHeightViewPager;
import com.example.administrator.Fanpul.utils.GlideUtil;
import com.example.administrator.Fanpul.utils.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment  implements DBQueryCallback<Restaurant> {
	@ViewInject(R.id.index_home_viewpager)
	private WrapContentHeightViewPager viewPager;

	@ViewInject(R.id.index_home_rb1)
	private RadioButton rb1;

	@ViewInject(R.id.index_home_rb2)
	private RadioButton rb2;

	@ViewInject(R.id.index_home_rb3)
	private RadioButton rb3;

	private GridView gridView1;
	private GridView gridView2;
	private GridView gridView3;

	@ViewInject(R.id.storelist)

	private RecyclerView recyclerView;//店铺的recycleList
	private RestaurantItemAdapter restaurantItemAdapter;

	@ViewInject(R.id.ad_view)
	private ImageCycleView imagecycleview;

	private ArrayList<String> mImageUrl = null;
	private ArrayList<String> mImageTitle = null;
	private String imageUrl1 = "https://a-ssl.duitang.com/uploads/item/201703/29/20170329122116_aywmH.jpeg";
	private String imageUrl2 = "https://a-ssl.duitang.com/uploads/item/201201/11/20120111185020_jTYiX.jpg";
	private String imageUrl3 = "https://a-ssl.duitang.com/uploads/item/201308/15/20130815195642_ufeCE.jpeg";
	private String imageTitle1 = "111";
	private String imageTitle2 = "222";
	private String imageTitle3 = "333";

	//自动定位
	@ViewInject(R.id.home_top_city)
	TextView cityView;
	//city=(TextView)findViewById(R.id.home_top_city);
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private ProgressDialog dialog;
	private String city;
	private Builder builder;

	@Override
	public void onStart() {
		super.onStart();
		Log.e("jhd","onStart");
		initViewPager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.index_home, container,false);
		ViewUtils.inject(this, view);
		ButterKnife.bind(this,view);
        restaurantItemAdapter = new RestaurantItemAdapter();
        List<Restaurant> restaurantList;restaurantList = new ArrayList<>();
        restaurantItemAdapter.setDataList(restaurantList);
		initCity();
        initGridView();
        initStoreList();
        initImageCycleView();
		String bql = "select * from Restaurant";
		DBConnection.queryBmobObject(bql,this);
		return view;
	}


	private void initImageCycleView() {
		mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
		int stype=1;
		mImageTitle = new ArrayList<String>();
		mImageTitle.add(imageTitle1);
		mImageTitle.add(imageTitle2);
		mImageTitle.add(imageTitle3);
		imagecycleview.setImageResources(mImageUrl ,mImageTitle, mAdCycleViewListener,stype);
	}

	private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {
			//Toast.makeText(getActivity(), mImageUrl.get(position)+position, Toast.LENGTH_SHORT).show();
		}
	};

	private void initStoreList(){
		//storeListAdapter=new StoreListAdapter(getActivity());
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(restaurantItemAdapter);
	}

	@Override
	public void Success(List<Restaurant> bmobObjectList) {
           restaurantItemAdapter.setDataList(bmobObjectList);
		   restaurantItemAdapter.notifyDataSetChanged();

	}

	@Override
	public void Failed() {

	}

	@OnClick(R.id.img_search_restaurant)
	public void onClickImgvSearch() {
		getFragmentManager()
				.beginTransaction()
				.add(android.R.id.content, new SearchFragment(), "fragment_search")
				.addToBackStack("fragment:reveal")
				.commit();
	}

	public class GridViewAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int page;

		public GridViewAdapter(Context context, int page) {
			super();
			this.inflater = LayoutInflater.from(context);
			this.page=page;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(page!=-1){
				return 8;
			}else{
				return Constants.navSort.length;
			}
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder vh=null;
			if(convertView==null){
				vh=new ViewHolder();
				convertView=inflater.inflate(R.layout.index_home_grid_item, null);
				ViewUtils.inject(vh, convertView);
				convertView.setTag(vh);
			}
			else{
				vh=(ViewHolder) convertView.getTag();
			}
			vh.iv_navsort.setImageResource(Constants.navSortImages[position+8*page]);
			vh.tv_navsort.setText(Constants.navSort[position+8*page]);
			if(position==8-1 && page==2){
				vh.iv_navsort.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
					}
				});
			}else{
				vh.iv_navsort.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
					}
				});
			}
			return convertView;
		}
	}
	/**
	 * 实现BDLocationListener接口
	 *
	 * BDLocationListener接口有2个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
	 * 2.接收异步返回的POI查询结果，参数是BDLocation类型参数。
	 *
	 * @author 晨彦
	 *
	 */
	class MyLocationListener implements BDLocationListener {


		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			dialog.cancel();
			int code = location.getLocType();
			String addr = location.getAddrStr();
			addr="海淀";
			if (/*code == 161 && addr != null*/true) {
				// 定位成功
				System.out.println(addr);
				//city = formatCity(addr);
				cityView.setText(addr);
			} else {
				// 定位失败
				builder = new Builder(getActivity());
				builder.setTitle("提示");
				builder.setMessage("自动定位失败。");
				builder.setPositiveButton("重试",
						new DialogInterface.OnClickListener() {


							public void onClick(DialogInterface dialog,
												int which) {
								if (Utils.checkNetwork(getActivity()) == false) {
									Toast.makeText(getActivity(),
											"网络异常,请检查网络设置", Toast.LENGTH_SHORT)
											.show();
									return;
								}

								requestLocation();
							}
						});
				builder.setNegativeButton("取消", null);
				builder.setCancelable(false);
				builder.show();
			}
		}


		public void onReceivePoi(BDLocation poiLocation) {
		}

	}

	/**
	 * 设置定位参数。 定位模式（单次定位，定时定位），返回坐标类型，是否打开GPS等等。
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息

		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(24 * 60 * 60 * 1000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
	}

	/**
	 * 请求位置信息
	 */
	private void requestLocation() {
		if (mLocationClient.isStarted() == false) {
			mLocationClient.start();
		} else {
			mLocationClient.requestLocation();
		}
	}

	/**
	 * 将位置信息转换为城市
	 *
	 * @param addr
	 *            位置
	 * @return 城市名称
	 */
	private String formatCity(String addr) {
		String city = null;
		if (addr.contains("北京市") && addr.contains("区")) {
			city = addr.substring(addr.indexOf("市") + 1, addr.indexOf("区"));
		} else if (addr.contains("县")) {
			city = addr.substring(addr.indexOf("市") + 1, addr.indexOf("县"));
		} else {
			int start = addr.indexOf("市");
			int end = addr.lastIndexOf("市");
			if (start == end) {
				if (addr.contains("省")) {
					city = addr.substring(addr.indexOf("省") + 1,
							addr.indexOf("市"));
				} else if (addr.contains("市")) {
					city = addr.substring(0, addr.indexOf("市"));
				}
			} else {
				city = addr.substring(addr.indexOf("市") + 1,
						addr.lastIndexOf("市"));
			}
		}
		return city;
	}

	private static final int REQUEST_CODE_PICK_CITY = 233;
	public  void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode,resultCode,data);
		if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
			if (data != null){
				String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
				cityView.setText( city);
			}
		}
	}
	private void initCity(){

		cityView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
						REQUEST_CODE_PICK_CITY);
			}
		});
	}

	private class ViewHolder{
		@ViewInject(R.id.index_home_iv_navsort)
        ImageView iv_navsort;
		@ViewInject(R.id.index_home_tv_navsort)
        TextView tv_navsort;
	}
	private void initGridView(){
		gridView1=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView1.setAdapter(new GridViewAdapter(getActivity(), 0));
		gridView2=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView2.setAdapter(new GridViewAdapter(getActivity(), 1));
		gridView3=(GridView) LayoutInflater.from(getActivity()).inflate(R.layout.index_home_gridview, null);
		gridView3.setAdapter(new GridViewAdapter(getActivity(), 2));
	}
	private void initViewPager(){
		List<View> list=new ArrayList<View>();
		list.add(gridView1);
		list.add(gridView2);
		list.add(gridView3);
		viewPager.setAdapter(new MyViewPagerAdapter(list));
		rb1.setChecked(true);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if(position==0){
					rb1.setChecked(true); 
				}else if(position==1){
					rb2.setChecked(true);
				}else if(position==2){
					rb3.setChecked(true);
				}
			}		
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}	
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}
	private class MyViewPagerAdapter extends PagerAdapter {
		List<View> list;
		public MyViewPagerAdapter(List<View> list) {
			this.list=list;
		}
		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));
			return list.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));			
		}
		@Override
		public CharSequence getPageTitle(int position) {
			return "1";
		}		
	}
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("jhd", "onStop");
	}

	public class RestaurantItemAdapter extends BaseRecyclerAdapter<Restaurant> {
		GlideUtil glideUtil;


		public RestaurantItemAdapter() {
			glideUtil = new GlideUtil();
		}

		@Override

		public CommonHolder<Restaurant> setViewHolder(ViewGroup parent) {
			return new RestaurantItemHolder(parent.getContext(), parent);
		}

		class RestaurantItemHolder extends CommonHolder<Restaurant> implements OnClickListener {

			@Bind(R.id.shop_name)
			TextView shopNameText;
			@Bind(R.id.shop_icon)
			ImageView shopIcon;
			@Bind(R.id.listViewStore)
			RecyclerView imageRecyclerView;//图片的recycleList
			@Bind(R.id.shop_sale_infor)
			TextView shopSaleInfor;
			@Bind(R.id.shop_line_up_infor)
			TextView shopLineInfor;
			private Restaurant restaurant;

			public RestaurantItemHolder(Context context, ViewGroup root) {
				super(context, root, R.layout.match_storelist_item);
				itemView.setOnClickListener(RestaurantItemHolder.this);
			}
			@Override
			public void bindData(Restaurant restaurant) {
				this.restaurant = restaurant;
				shopNameText.setText(restaurant.getRestaurantName());
				if(restaurant.getIconfile()!=null && restaurant.getIconfile().getUrl()!=null&&
						(!TextUtils.isEmpty(restaurant.getIconfile().getUrl()))){
					glideUtil.attach(shopIcon).injectImageWithNull(restaurant.getIconfile().getUrl());
				}
				Log.i("FileUrl",restaurant.getIconfile().getUrl());
				shopSaleInfor.setText("月售"+restaurant.getOrder()+ "单 |"+restaurant.getScore()
				+"分 |离我"+restaurant.getDistance()+"米");

				shopLineInfor.setText("当前排队: "+restaurant.getBigtableNum()+"大桌-"+restaurant.getMiddletableNum()
						+"中桌-"+restaurant.getSmalltableNum()+"小桌  ");
				ImageItemAdapter imageItemAdapter = new ImageItemAdapter(restaurant.getBmobFileList());
				imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
				imageRecyclerView.setAdapter(imageItemAdapter);


			}

			@Override
			public void onClick(View v) {
				//Intent intent = OrderMenuActivity.getIntent(getActivity(), shopNameText.getText().toString());
				//startActivity(intent);
				SegmentTabActivity.SegmentTabActivityStart(getActivity(),restaurant);
			}

		}
		class ImageItemAdapter extends  RecyclerView.Adapter<ImageItemHolder>{
			private List<BmobFile> imgurlList=new ArrayList<>();
			public ImageItemAdapter(List<BmobFile> data){
				BmobFile b=null;//存储shop_more bmobFile
				if(data.size()>0){
					for(int i=0;i<data.size();i++){
						if(data.get(i).getFilename().equals("shop_more.jpg")||data.get(i).getFilename().equals("shop_more.png")){
							b = data.get(i);
						}
						else{
							imgurlList.add(data.get(i));
						}
					}
					if(b!=null){
						imgurlList.add(b);
					}
				}
			}
			@Override
			public ImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
				View view = layoutInflater.inflate(R.layout.match_league_round_item,parent,false);
				return new ImageItemHolder(view);
			}
			@Override
			public void onBindViewHolder(ImageItemHolder holder, int position) {
				holder.bindImageItemHolder(imgurlList.get(position).getUrl());
			}
			@Override
			public int getItemCount() {
				return imgurlList.size();
			}
		}

		class ImageItemHolder extends RecyclerView.ViewHolder{
			ImageView imageView;
			public ImageItemHolder(View itemView) {
				super(itemView);
				imageView = (ImageView)itemView.findViewById(R.id.imageView_my);

			}

			public void bindImageItemHolder(String imageUrl){
				glideUtil.attach(imageView).injectImageWithNull(imageUrl);
			}
		}
	}


}
