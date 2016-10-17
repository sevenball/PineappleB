package com.wangshiqi.pineappleb.ui.fragment.dicovery;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends AbsFragment {

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

    }

    @Override
    protected void initDatas() {

    }
}
