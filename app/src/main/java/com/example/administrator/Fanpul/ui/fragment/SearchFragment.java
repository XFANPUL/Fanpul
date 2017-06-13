package com.example.administrator.Fanpul.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fanpul.R;
import com.example.administrator.Fanpul.model.DB.DBProxy;
import com.example.administrator.Fanpul.model.DB.IDBCallBack.OneObjectCallBack;
import com.example.administrator.Fanpul.model.entity.bmobEntity.Restaurant;
import com.example.administrator.Fanpul.ui.adapter.SearchHistoryAdapter;
import com.example.administrator.Fanpul.ui.component.fab_transformation.animation.SupportAnimator;
import com.example.administrator.Fanpul.ui.component.fab_transformation.animation.ViewAnimationUtils;
import com.example.administrator.Fanpul.ui.component.tagCloudLayout.TagCloudLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.content)
    public View content;
    @Bind(R.id.edit_lay)
    public View editLay;
    @Bind(R.id.edit_search)
    public EditText editSearch;
    @Bind(R.id.img_search)
    public ImageView imgvSearch;
    @Bind(R.id.items)
    public View items;

    @Bind(R.id.tag_history)
    public TagCloudLayout tagHistory;

    private int centerX;
    private int centerY;
    private List<String> datas = new ArrayList<>();
    private Map<String,String> datamap = new HashMap<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_cook_search, container, false);
        ButterKnife.bind(this, rootView);
        sharedPreferences = getActivity().getSharedPreferences("data",Context.MODE_APPEND);
        editor = sharedPreferences.edit();

        editLay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                editLay.getViewTreeObserver().removeOnPreDrawListener(this);
                items.setVisibility(View.INVISIBLE);
                items.setOnClickListener(SearchFragment.this);
                editLay.setVisibility(View.INVISIBLE);

                centerX = imgvSearch.getLeft() + imgvSearch.getWidth() / 2;
                centerY = imgvSearch.getTop() + imgvSearch.getHeight() / 2;

                SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(
                        editLay, centerX, centerY, 20, hypo(editLay.getWidth(), editLay.getHeight()));
                mRevealAnimator.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                        editLay.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onAnimationEnd() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                items.setVisibility(View.VISIBLE);
                                editSearch.requestFocus();
                                if (getActivity()!=null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
                                }
                            }
                        }, 100);
                    }
                    @Override
                    public void onAnimationCancel() {
                    }
                    @Override
                    public void onAnimationRepeat() {
                    }
                });
                mRevealAnimator.setDuration(400);
                mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mRevealAnimator.start();
                return true;
            }
        });
        datamap = (Map<String, String>) sharedPreferences.getAll();
        Iterator<String> iterator = datamap.keySet().iterator();
        while (iterator.hasNext()){
            datas.add(iterator.next());
        }
        searchHistoryAdapter = new SearchHistoryAdapter(getActivity(), datas);
        tagHistory.setAdapter(searchHistoryAdapter);
        tagHistory.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {

                if(position == datas.size()){
                    tagCookSearchHistoryClean();
                }
                else {
                    tagCookSearchHistoryClick(datas.get(position));
                }
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    search(editSearch.getText().toString());
                    return true;
                }

                return false;
            }
        });
        return rootView;

    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public boolean onBackPressed() {
        SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(
                content, centerX, centerY, 20, hypo(content.getWidth(), content.getHeight()));
        mRevealAnimator = mRevealAnimator.reverse();

        if (mRevealAnimator == null)
            return false;

        mRevealAnimator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
                content.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd() {
                content.setVisibility(View.INVISIBLE);
                if (getActivity()!=null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }
            @Override
            public void onAnimationCancel() {
            }
            @Override
            public void onAnimationRepeat() {
            }
        });

        mRevealAnimator.setDuration(400);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();

        return true;
    }

    private float hypo(int a, int b){
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.root:
                onBackPressed();
                break;
        }
    }

    @OnClick(R.id.img_search)
    public void onClickSearch(){
        DBProxy.proxy .queryRestaurantByName(editSearch.getText().toString(),new OneObjectCallBack<Restaurant>() {
            @Override
            public void Success(Restaurant result) {
                editor.putString(editSearch.getText().toString(),result.getRestaurantName());
                editor.apply();
                Toast.makeText(getActivity(),getString(R.string.cache_success),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Failed() {

            }
        });
    }


    private void tagCookSearchHistoryClick(String key){
        search(key);
    }

    private void tagCookSearchHistoryClean(){
        tagHistory.setVisibility(View.GONE);
        editor.clear();
        editor.apply();
    }
   /* private String searchKey = "";*/
    private void search(String text){
      /*  //去除换行符
        searchKey = text.replaceAll("\r|\n", "");
        if(TextUtils.isEmpty(searchKey))
            return ;
        KeyboardUtil.showKeyboard(getActivity(), editSearch, false);*/
    }

}
