package com.wangshiqi.pineappleb.ui.fragment.hotest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huxq17.swipecardsview.SwipeCardsView;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.hotest.HotCardAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dllo on 16/10/17.
 */
public class HotestFragment extends AbsFragment {

    private TextView hotestTitle;
    private HotCardAdapter cardAdapter;
    private SwipeCardsView cardsView;
    private List<HotCardBean> bean;

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
    }

    @Override
    protected void initDatas() {
        hotestTitle.setText(getResources().getString(R.string.hotest_tv));

        cardAdapter = new HotCardAdapter(context);
        /**
         *  卡片数据的请求
         */
        cardDataRequest();

        cardsSlideListener();
    }

    private void cardsSlideListener() {
        cardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {

            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                switch (type) {
                    case LEFT:

                        Log.d("zzz", "index:" + index);
                        if (index == bean.size() - 3) {
                            bean.addAll(bean);
                            Toast.makeText(context, "向左滑动了图片", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case RIGHT:
                        if (index == bean.size() - 3) {
                            bean.addAll(bean);
                            Toast.makeText(context, "向右滑动了图片", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                Toast.makeText(context, "卡片的点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cardDataRequest() {
        VolleyInstance volleyInstance = VolleyInstance.getInstance();
        volleyInstance.startRequest(ValueTool.HOT_CARD, new IVolleyResult() {

            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<HotCardBean>>() {
                }.getType();
                bean =  gson.fromJson(resultStr, type);
                // 把这个集合每一个元素放一个新集合里
                cardAdapter.setDatas(bean);
                cardsView.setAdapter(cardAdapter);
                cardsView.notifyDatasetChanged(bean);
            }

            @Override
            public void failure() {

            }
        });
    }
}
