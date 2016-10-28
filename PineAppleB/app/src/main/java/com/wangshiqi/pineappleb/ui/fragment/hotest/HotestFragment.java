package com.wangshiqi.pineappleb.ui.fragment.hotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.ui.activity.focus.DynamicInfoActivity;
import com.wangshiqi.pineappleb.ui.adapter.hotest.HotCardAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dllo on 16/10/17.
 */
public class HotestFragment extends AbsFragment {

    private TextView hotestTitle;
    private HotCardAdapter cardAdapter;
    private SwipeCardsView cardsView;
    private List<HotCardBean> bean;
    private boolean isState = false;
    private int index;
    private List<DiscussBean.DataBean> data;
    private CoordinatorLayout hotCardCrd;


    public static HotestFragment newInstance() {
        Bundle args = new Bundle();
        HotestFragment fragment = new HotestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_hotest;
    }

    @Override
    protected void initView() {
        hotestTitle = byView(R.id.title_tv);
        cardsView = byView(R.id.hot_swipe_card_view);
//        hotCardCrd = byView(R.id.hot_card_crd);

    }

    @Override
    protected void initDatas() {
        hotestTitle.setText(getResources().getString(R.string.hotest_tv));

        cardAdapter = new HotCardAdapter(context);
        // 卡片的数据请求
        cardDataRequest();
        // 卡片的滑动监听和点击事件
        cardsSlideListener();
        // 卡片评论数据请求

    }

    /**
     * 卡片的滑动监听和点击事件
     */
    private void cardsSlideListener() {
        cardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {

            @Override
            public void onShow(int index) {
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                switch (type) {
                    case LEFT:
                        if (index == bean.size() - 4) {
                            bean.addAll(bean);
                            Toast.makeText(context, "向左滑动了图片", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case RIGHT:
                        if (index == bean.size() - 4) {
                            bean.addAll(bean);
                            Toast.makeText(context, "向右滑动了图片", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                HotestFragment.this.index = index;
                Intent intent = new Intent(context, DynamicInfoActivity.class);
                if (index < 4) {
                    intent.putExtra("linkMp4", bean.get(index % bean.size()).getLinkMp4());
                    intent.putExtra("title", bean.get(index % bean.size()).getTitle());
                    intent.putExtra("intro", bean.get(index % bean.size()).getIntro());
                    intent.putExtra("playCount", bean.get(index % bean.size()).getPlayCount());
                    intent.putExtra("tag", bean.get(index % bean.size()).getTag());
                    intent.putExtra("videoId", bean.get(index % bean.size()).getVideoId());
                } else {
                    intent.putExtra("linkMp4", bean.get(index % bean.size() + 1).getLinkMp4());
                    intent.putExtra("title", bean.get(index % bean.size() + 1).getTitle());
                    intent.putExtra("intro", bean.get(index % bean.size() + 1).getIntro());
                    intent.putExtra("playCount", bean.get(index % bean.size() + 1).getPlayCount());
                    intent.putExtra("tag", bean.get(index % bean.size() + 1).getTag());
                    intent.putExtra("videoId", bean.get(index % bean.size() + 1).getVideoId());
                }
                Log.d("HotestFragment", "index%15:" + index + "---->" + (index % 15) + "");
                context.startActivity(intent);
                Toast.makeText(context, "卡片的点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 卡片的数据请求
     */
    private void cardDataRequest() {

        OkHttpInstance okHttpInstance = OkHttpInstance.getInstance();
        okHttpInstance.getAsyn(ValueTool.HOT_CARD, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<HotCardBean>>() {
                }.getType();
                bean = gson.fromJson(String.valueOf(response), type);
                // 把这个集合每一个元素放一个新集合里
                cardAdapter.setDatas(bean);
                cardsView.setAdapter(cardAdapter);
                cardsView.notifyDatasetChanged(bean);
            }
        });
    }

}
