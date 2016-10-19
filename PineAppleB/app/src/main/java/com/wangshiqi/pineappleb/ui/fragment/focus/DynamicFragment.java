package com.wangshiqi.pineappleb.ui.fragment.focus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
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
        List<DynamicBean> list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            list.add(new DynamicBean("你好"+i));
        }
        adapter.setmDatas(list);
        listView.setAdapter(adapter);
    }
}
