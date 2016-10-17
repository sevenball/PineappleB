package com.wangshiqi.pineappleb.ui.fragment.hotest;

import android.os.Bundle;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/17.
 */
public class HotestFragment extends AbsFragment {

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

    }

    @Override
    protected void initDatas() {

    }
}
