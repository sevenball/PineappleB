package com.wangshiqi.pineappleb.ui.fragment.hotest;

import android.os.Bundle;
import android.widget.TextView;

import com.huxq17.swipecardsview.SwipeCardsView;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;
import com.wangshiqi.pineappleb.ui.adapter.hotest.HotCardAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.List;

/**
 * Created by dllo on 16/10/17.
 */
public class HotestFragment extends AbsFragment {

    private TextView hotestTitle;
    private HotCardAdapter cardAdapter;
    private List<HotCardBean> cardBeens;
    private SwipeCardsView cardsView;

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

    }
}
