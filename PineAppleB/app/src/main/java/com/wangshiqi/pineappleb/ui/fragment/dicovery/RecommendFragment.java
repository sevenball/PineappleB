package com.wangshiqi.pineappleb.ui.fragment.dicovery;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendHeadAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

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
        startRoll();
    }

    private void startRoll() {
        headAdapter = new RecommendHeadAdapter(context);
        VolleyInstance.getInstance().startRequest("http://m.live.netease.com/bolo/api/index/bannerVideoNew.htm", new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<HeadBean>>(){}.getType();
                datas = gson.fromJson(resultStr, type);
                headAdapter.setDatas(datas);
                
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
