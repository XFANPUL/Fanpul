package com.example.administrator.cookman.ui.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.cookman.R;
import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class ImageCycleView extends LinearLayout {
	private Context mContext;
	private ViewPager mAdvPager = null;
	private ImageCycleAdapter mAdvAdapter;
	private ViewGroup mGroup;
	private TextView viewGroup2;
	private ImageView mImageView = null;
	private TextView mTitleView = null;
	private ImageView[] mImageViews = null;
	private TextView[] mTitleViews = null;
	private boolean isStop;
	public int stype=1;
	public ImageCycleView(Context context) {
		super(context);
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("Recycle")
	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.ad_cycle_view, this);
		mAdvPager = (ViewPager) findViewById(R.id.adv_pager);
		mAdvPager.setOnPageChangeListener(new GuidePageChangeListener());
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
		viewGroup2  = (TextView) findViewById(R.id.viewGroup2);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if(event.getAction()== MotionEvent.ACTION_UP){
			startImageTimerTask();
		}else{
			stopImageTimerTask();
		}
		return super.dispatchTouchEvent(event);
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void setImageResources(ArrayList<String> imageUrlList , ArrayList<String> imageTitle, ImageCycleViewListener imageCycleViewListener, int stype){
		this.stype=stype;
		mGroup.removeAllViews();
		final int imageCount = imageUrlList.size();
		mImageViews = new ImageView[imageCount];
		mTitleViews  = new TextView[imageCount];
		for (int i = 0; i < imageCount; i++) {
			mImageView = new ImageView(mContext);
			mTitleView = new TextView(mContext);
			LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin=30; 
			mImageView.setScaleType(ScaleType.CENTER_CROP);
			mImageView.setLayoutParams(params);

			mImageViews[i] = mImageView;
			mTitleViews[i] = mTitleView;

			if (i == 0) {
				if(this.stype==1)
					mImageViews[i].setBackgroundResource(R.drawable.banner_dian_focus);
				else
					mImageViews[i].setBackgroundResource(R.drawable.cicle_banner_dian_focus);
			} else {
				if(this.stype==1)
					mImageViews[i].setBackgroundResource(R.drawable.banner_dian_blur);
				else
					mImageViews[i].setBackgroundResource(R.drawable.cicle_banner_dian_blur);
			}
			mGroup.addView(mImageViews[i]);
		}

		mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList ,imageTitle,imageCycleViewListener);
		mAdvPager.setAdapter(mAdvAdapter);
		mAdvPager.setCurrentItem(Integer.MAX_VALUE/2);
		startImageTimerTask();
	}

	public void startImageCycle() {
		startImageTimerTask();
	}

	public void pushImageCycle() {
		stopImageTimerTask();
	}

	private void startImageTimerTask() {
		stopImageTimerTask();
		mHandler.postDelayed(mImageTimerTask, 3000);
	}
	private void stopImageTimerTask() {
		isStop=true;
		mHandler.removeCallbacks(mImageTimerTask);
	}

	private Handler mHandler = new Handler();

	private Runnable mImageTimerTask = new Runnable() {
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void run() {
			if (mImageViews != null) {
				mAdvPager.setCurrentItem(mAdvPager.getCurrentItem()+1);
				if(!isStop){
					mHandler.postDelayed(mImageTimerTask, 3000);
				}

			}
		}
	};

	private final class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE)
				startImageTimerTask(); 
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int index) {
			index=index%mImageViews.length;
			if(stype==1)
				mImageViews[index].setBackgroundResource(R.drawable.banner_dian_focus);
			else
				mImageViews[index].setBackgroundResource(R.drawable.cicle_banner_dian_focus);
			for (int i = 0; i < mImageViews.length; i++) {
				if (index != i) {
					if(stype==1)
						mImageViews[i].setBackgroundResource(R.drawable.banner_dian_blur);
					else
						mImageViews[i].setBackgroundResource(R.drawable.cicle_banner_dian_blur);
				}
				
			}
		}
	}

	private class ImageCycleAdapter extends PagerAdapter {
		private ArrayList<SmartImageView> mImageViewCacheList;
		private ArrayList<String> mAdList = new ArrayList<String>();
		private ArrayList<String> mATitle = new ArrayList<String>();
		private ImageCycleViewListener mImageCycleViewListener;
		private Context mContext;
		public ImageCycleAdapter(Context context, ArrayList<String> adList , ArrayList<String> title, ImageCycleViewListener imageCycleViewListener) {
			this.mContext = context;
			this.mAdList = adList;
			this.mATitle = title;
			mImageCycleViewListener = imageCycleViewListener;
			mImageViewCacheList = new ArrayList<SmartImageView>();
		}
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		@Override
        public Object instantiateItem(ViewGroup container, final int position) {
                String imageUrl = mAdList.get(position%mAdList.size());
                String title = mATitle.get(position%mATitle.size());
                Log.i("imageUrl",imageUrl+title);
            	viewGroup2.setText(title);
                SmartImageView imageView = null;
                if (mImageViewCacheList.isEmpty()) {
                        imageView = new SmartImageView(mContext);
                        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } 
                else {
                        imageView = mImageViewCacheList.remove(0);
                }
                imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                mImageCycleViewListener.onImageClick(position%mAdList.size(), v);
                        }
                });
                imageView.setTag(imageUrl);
                container.addView(imageView);
                imageView.setImageUrl(imageUrl);
                return imageView;
        }
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			SmartImageView view = (SmartImageView) object;
			mAdvPager.removeView(view);
			mImageViewCacheList.add(view);
		}

	}
	public static interface ImageCycleViewListener {
		public void onImageClick(int position, View imageView);
	}

}
