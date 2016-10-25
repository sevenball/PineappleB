package com.wangshiqi.pineappleb.ui.fragment.dicovery;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.MustWatchBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.MustWatchLvAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.List;

/**
 * Created by dllo on 16/10/24.
 */
public class MustWatchFragment extends AbsFragment implements IVolleyResult {
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
        VolleyInstance.getInstance().startRequest(string, this);
        watchLv.setAdapter(mustWatchLvAdapter);

    }

    @Override
    public void success(String resultStr) {
        datas = JSON.parseArray(resultStr, MustWatchBean.class);
//        for (int i = 0; i < datas.size() - 1; i++) {
//            if (datas.get(i).getBannerName().equals(datas.get(i + 1).getBannerName())) {
//                datas.remove(i);
//            }
//        }
        mustWatchLvAdapter.setmDatas(datas);
    }

    @Override
    public void failure() {

    }
}
