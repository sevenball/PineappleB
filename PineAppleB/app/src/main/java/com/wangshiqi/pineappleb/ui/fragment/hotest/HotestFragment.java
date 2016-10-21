package com.wangshiqi.pineappleb.ui.fragment.hotest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        VolleyInstance volleyInstance = VolleyInstance.getInstance();
        volleyInstance.startRequest(ValueTool.HOT_CARD, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<HotCardBean>>() {}.getType();
                Log.d("HotestFragment", resultStr);

                List<HotCardBean> bean = gson.fromJson(resultStr, type);
                Log.d("HotestFragment", "bean:" + bean);

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
