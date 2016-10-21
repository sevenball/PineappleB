package com.wangshiqi.pineappleb.ui.fragment.focus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.focus.DynamicFragmentAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends AbsFragment {
    private ListView listView;
    private DynamicFragmentAdapter adapter;
    public static DynamicFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DynamicFragment fragment = new DynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.dynamic_listview);
        adapter = new DynamicFragmentAdapter(context);

    }

    @Override
    protected void initDatas() {
        VolleyInstance.getInstance().startRequest("http://m.live.netease.com/bolo/api/user/timeLine.htm?pageNum=1&lastTime=2016-10-15%2010%3A59%3A46&encryptToken=dfc23870c7ad025e735f8a76859d1a0d&random=0.2582823928479111&userId=-2798972347206426236&pageSize=20&timeStamp=1476446520350", new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                DynamicBean dynamicBean = gson.fromJson(resultStr,DynamicBean.class);
                List<DynamicBean.ListBean> list = dynamicBean.getList();
                adapter.setmDatas(list);
//                adapter.setmDatas(beanList);
                listView.setAdapter(adapter);


            }

            @Override
            public void failure() {

            }
        });
//        adapter.setmDatas(list);
//        listView.setAdapter(adapter);
    }
}
