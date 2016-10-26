package com.wangshiqi.pineappleb.ui.fragment.dicovery;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.MustWatchBean;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.MustWatchLvAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.List;

import okhttp3.Call;

/**
 * Created by dllo on 16/10/24.
 */
public class MustWatchFragment extends AbsFragment {
    private ListView watchLv;
    private MustWatchLvAdapter mustWatchLvAdapter;
    private List<MustWatchBean> datas;

    public static MustWatchFragment newInstance(String mustWatch) {
        Bundle args = new Bundle();
        args.putString("url", mustWatch);
        MustWatchFragment fragment = new MustWatchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mustwatch;
    }

    @Override
    protected void initView() {
        watchLv = byView(R.id.mustwatch_lv);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getArguments();
        String string = bundle.getString("url");
        mustWatchLvAdapter = new MustWatchLvAdapter(context);
        OkHttpInstance.getAsyn(string, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                datas = JSON.parseArray(response.toString(), MustWatchBean.class);
                mustWatchLvAdapter.setmDatas(datas);
            }
        });
        watchLv.setAdapter(mustWatchLvAdapter);

    }

}
