package com.wangshiqi.pineappleb.ui.fragment.dicovery;

import android.os.Bundle;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/17.
 */
public class DiscoveryFragment extends AbsFragment {

    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_dicovery;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
