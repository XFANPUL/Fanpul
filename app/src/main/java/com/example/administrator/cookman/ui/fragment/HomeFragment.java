package com.example.administrator.cookman.ui.fragment;
import android.content.Context;
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
import com.example.administrator.cookman.R;
import com.example.administrator.cookman.model.bmob.BmobQueryCallback;
import com.example.administrator.cookman.model.bmob.BmobUtil;
import com.example.administrator.cookman.model.entity.bmobEntity.Restaurant;
import com.example.administrator.cookman.ui.activity.OrderMenuActivity;
import com.example.administrator.cookman.ui.adapter.BaseRecyclerAdapter;
import com.example.administrator.cookman.ui.adapter.CommonHolder;
import com.example.administrator.cookman.ui.view.ImageCycleView;
import com.example.administrator.cookman.ui.view.WrapContentHeightViewPager;
import com.example.administrator.cookman.constants.MyConstant;
import com.example.administrator.cookman.utils.GlideUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import cn.bmob.v3.datatype.BmobFile;

public class HomeFragment extends Fragment  implements BmobQueryCallback<Restaurant> {
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
        restaurantItemAdapter = new RestaurantItemAdapter();
        List<Restaurant> restaurantList;restaurantList = new ArrayList<>();
        restaurantItemAdapter.setDataList(restaurantList);
        initGridView();
        initStoreList();
        initImageCycleView();
		String bql = "select * from Restaurant";
		BmobUtil.queryBmobObject(bql,this);
		Log.e("jhd", "onCreateView");
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("jhd", "onResume");
	}
	@Override
	public void onDestroy() {
		Log.e("jhds", "onDestroy");
		super.onDestroy();

	}

	@Override
	public void Success(List<Restaurant> bmobObjectList) {
           restaurantItemAdapter.setDataList(bmobObjectList);
		   restaurantItemAdapter.notifyDataSetChanged();

	}



	@Override
	public void Failed() {

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
				return MyConstant.navSort.length;
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
			vh.iv_navsort.setImageResource(MyConstant.navSortImages[position+8*page]);
			vh.tv_navsort.setText(MyConstant.navSort[position+8*page]);
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
			Log.e("jhd", "���--"+position);
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

			public RestaurantItemHolder(Context context, ViewGroup root) {
				super(context, root, R.layout.match_storelist_item);
				itemView.setOnClickListener(RestaurantItemHolder.this);
			}
			@Override
			public void bindData(Restaurant restaurant) {
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
				Intent intent = OrderMenuActivity.getIntent(getActivity(), shopNameText.getText().toString());
				startActivity(intent);
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

	/*	@Override
		public RestaurantItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater.inflate(R.layout.match_storelist_item,parent,false);
			return new RestaurantItemHolder(view);
		}
		@Override
		public void onBindViewHolder(RestaurantItemHolder holder, int position) {
			holder.bindrestaurantItemHolder(MyConstant.shopName[position]);
		}
		@Override
		public int getItemCount() {
			return MyConstant.shopName.length;
		}*/
		/*public RestaurantItemHolder(View itemView) {
			super(itemView);
			shopNameText = (TextView)itemView.findViewById(R.id.shop_name);
			imageRecyclerView = (RecyclerView)itemView.findViewById(R.id.listViewStore);
			itemView.setOnClickListener(this);
		}*/

		/*public void bindrestaurantItemHolder(String shopnameText){
			//shopName = shopnameText;
			shopNameText.setText(shopnameText);
			imageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
		    imageRecyclerView.setAdapter(new ImageItemAdapter(getData()));
		}*/




/*	private List<Map<String, Object>> getData() {

		int[] pic = {R.drawable.shop_preshow_jiyejia_1,R.drawable.shop_preshow_jiyejia_2,R.drawable.shop_preshow_jiyejia_3, R.mipmap.shop_4,
				R.mipmap.shop_5, R.mipmap.shop_6, R.mipmap.shop_7, R.mipmap.shop_8,
				 R.mipmap.shop_9, R.mipmap.shop_10, R.mipmap.shop_11, R.mipmap.shop_12
				, R.mipmap.shop_13, R.mipmap.shop_14, R.mipmap.shop_15, R.mipmap.shop_16,
		R.drawable.shop_more};
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map;
		for (int i = 0; i < pic.length; i++) {
			map = new HashMap<String, Object>();
			map.put("page", "第" + (i + 1) + "张");
			map.put("pic", pic[i]);
			list.add(map);
			System.out.println("add" + i);
		}
		return list;
	}*/

}
