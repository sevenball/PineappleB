package com.wangshiqi.pineappleb.ui.fragment.dicovery;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendHeadAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.MyTransformation;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 推荐
 */
public class RecommendFragment extends AbsFragment {
    private static final int TIME = 5000;
    private ViewPager viewPager;
    private RecommendHeadAdapter headAdapter;
    private List<HeadBean> datas;
    public static RecommendFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView() {
        viewPager = byView(R.id.recommend_head_vp);
    }

    @Override
    protected void initDatas() {
//        startRoll();
    }
    private int pagerWidth;
    private void startRoll() {
        headAdapter = new RecommendHeadAdapter(context);
        VolleyInstance.getInstance().startRequest("http://m.live.netease.com/bolo/api/index/bannerVideoNew.htm", new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Log.d("RecommendFragment", resultStr);
                Gson gson = new Gson();
                Type type = new TypeToken<List<HeadBean>>(){}.getType();
                datas = gson.fromJson(resultStr, type);
                headAdapter.setDatas(datas);
                // ViewPager的页数为int最大值,设置当前页多一些,可以上来就向前滑动
                // 为了保证第一页始终为数据的第0条 取余要为0,因此设置数据集合大小的倍数
                viewPager.setCurrentItem(datas.size() * 100);
                pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 4.0f / 5.0f);
                ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
                if (lp == null) {
                    lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    lp.width = pagerWidth;
                }
                viewPager.setLayoutParams(lp);
                viewPager.setPageMargin(-85);
                byView(R.id.recommend).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return viewPager.dispatchTouchEvent(motionEvent);
                    }
                });
                viewPager.setPageTransformer(true, new MyTransformation());
                viewPager.setOffscreenPageLimit(5);
                viewPager.setAdapter(headAdapter);
                viewPager.setCurrentItem((Integer.MAX_VALUE - datas.size()) / 2 - 1);
            }

            @Override
            public void failure() {

            }
        });
        startRotate();
    }

    private Handler handler;
    private boolean isRotate = false;
    private Runnable rotateRunnable;
    private boolean flag = true;

    private void startRotate() {
        handler = new Handler();
        if (flag) {
            rotateRunnable = new Runnable() {
                @Override
                public void run() {
                    int nowIndex = viewPager.getCurrentItem();
                    viewPager.setCurrentItem(++nowIndex);
                    handler.postDelayed(rotateRunnable, TIME);
                }
            };
            handler.postDelayed(rotateRunnable, TIME);
            flag = false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        isRotate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRotate = true;
    }

}
