package com.wangshiqi.pineappleb.ui.fragment.dicovery;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.model.bean.dicovery.RecommendRankBean;
import com.wangshiqi.pineappleb.model.bean.dicovery.RecommendStrongBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.activity.focus.DynamicInfoActivity;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendHeadAdapter;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendRankRvAdapter;
import com.wangshiqi.pineappleb.ui.adapter.discovery.RecommendStrongRvAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.MyTransformation;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐
 */
public class RecommendFragment extends AbsFragment {
    private static final int TIME = 5000;
    private ViewPager viewPager;
    private RecommendHeadAdapter headAdapter;
    private List<HeadBean> datas;
    private TextView recommendTv;

    private RecyclerView recyclerView;
    private RecommendStrongRvAdapter recommendStrongRvAdapter;
    private List<RecommendStrongBean> strongBeanList;
    private ImageView refreshIv;

    private RecyclerView rankRv;
    private List<RecommendRankBean> rankBeanList;
    private RecommendRankRvAdapter rankRvAdapter;

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
        recommendTv = byView(R.id.recommend_head_tv);
        recyclerView = byView(R.id.recommend_rv_boluo);
        refreshIv = byView(R.id.refresh_civ);
        rankRv = byView(R.id.recommend_rv_rank);
    }

    int i = 1;

    @Override
    protected void initDatas() {
        startRoll();  // 上方自定义3D轮播图
        strongRv(); // 菠萝力荐
        refreshIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                strongRv();
            }
        });
        rank(); // 人气周榜
    }

    private void rank() {
        rankRvAdapter = new RecommendRankRvAdapter(context);
        GridLayoutManager managerRank = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rankRv.setLayoutManager(managerRank);
        rankRv.setAdapter(rankRvAdapter);
        VolleyInstance.getInstance().startRequest(ValueTool.RANK_RV, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                rankBeanList = JSON.parseArray(resultStr, RecommendRankBean.class);
                List<RecommendRankBean> beanRank = new ArrayList<>();
                for (int i1 = 0; i1 < 6; i1++) {
                    beanRank.add(rankBeanList.get(i1));
                }
                rankRvAdapter.setDatas(beanRank);
            }

            @Override
            public void failure() {

            }
        });
    }

    private void strongRv() {
        recommendStrongRvAdapter = new RecommendStrongRvAdapter(context);
        GridLayoutManager manager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recommendStrongRvAdapter);

        VolleyInstance.getInstance().startRequest(ValueTool.STRONG_RV + i, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                strongBeanList = JSON.parseArray(resultStr, RecommendStrongBean.class);
                recommendStrongRvAdapter.setDatas(strongBeanList);
            }

            @Override
            public void failure() {

            }
        });
        recommendStrongRvAdapter.setOnRvItemClick(new OnRvItemClick() {
            @Override
            public void onRvItemClickListener(int position, Object o) {
                Intent intent = new Intent(context, DynamicInfoActivity.class);
                intent.putExtra(DynamicInfoActivity.TITLE, strongBeanList.get(position).getTitle());
                intent.putExtra(DynamicInfoActivity.INTRO, strongBeanList.get(position).getIntro());
                intent.putExtra(DynamicInfoActivity.TAG, strongBeanList.get(position).getTag());
                intent.putExtra(DynamicInfoActivity.PLAYCOUNT, strongBeanList.get(position).getPlayCount());
                intent.putExtra(DynamicInfoActivity.LINKMP4, strongBeanList.get(position).getLinkMp4());
                intent.putExtra(DynamicInfoActivity.VIDEOID, strongBeanList.get(position).getVideoId());
                startActivity(intent);
            }
        });
    }

    private int pagerWidth;

    private void startRoll() {
        headAdapter = new RecommendHeadAdapter(context);
        VolleyInstance.getInstance().startRequest(ValueTool.HEAD_VP, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                datas = JSON.parseArray(resultStr, HeadBean.class);
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<HeadBean>>(){}.getType();
//                datas = gson.fromJson(resultStr, type);
//                Toast.makeText(context, "datas.size():" + datas.size(), Toast.LENGTH_SHORT).show();
                recommendTv.setText(datas.get(0).getTitle());
                pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 4.0f / 5.0f);
                headAdapter.setDatas(datas);
                ViewGroup.LayoutParams lp = viewPager.getLayoutParams();
                if (lp == null) {
                    lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                } else {
                    lp.width = pagerWidth;
                }
                viewPager.setLayoutParams(lp);
                viewPager.setPageMargin(-85);
                viewPager.setPageTransformer(true, new MyTransformation());
                viewPager.setOffscreenPageLimit(3);
                viewPager.setAdapter(headAdapter);
                viewPager.setCurrentItem(datas.size() * 1000);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        recommendTv.setText(datas.get(position % datas.size()).getTitle());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
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
