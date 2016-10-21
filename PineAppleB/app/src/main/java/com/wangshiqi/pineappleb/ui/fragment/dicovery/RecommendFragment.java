package com.wangshiqi.pineappleb.ui.fragment.dicovery;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendHeadAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.MyTransformation;

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
    // 上方自定义3D轮播图
    private int pagerWidth;
    private void startRoll() {
        headAdapter = new RecommendHeadAdapter(context);
        // 1.线程,  ok+线程池
        // 2.在欢迎页请求数据,
        VolleyInstance.getInstance().startRequest("http://m.live.netease.com/bolo/api/index/bannerVideoNew.htm", new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                datas = JSON.parseArray(resultStr, HeadBean.class);
                Toast.makeText(context, "datas.size():" + datas.size(), Toast.LENGTH_SHORT).show();
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<HeadBean>>(){}.getType();
//                datas = gson.fromJson(resultStr, type);
//                Toast.makeText(context, "datas.size():" + datas.size(), Toast.LENGTH_SHORT).show();
                headAdapter.setDatas(datas);
                pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 4.0f / 5.0f);
                ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
                if (lp == null) {
                    lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    lp.width = pagerWidth;
                }
                viewPager.setLayoutParams(lp);
                viewPager.setPageMargin(-85);
                viewPager.setPageTransformer(true, new MyTransformation());
                viewPager.setOffscreenPageLimit(datas.size());
                viewPager.setAdapter(headAdapter);
                viewPager.setCurrentItem(datas.size() * 100);
//                viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
            }
            @Override
            public void failure() {
            }
        });
        startRotate(); //开始轮播
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
