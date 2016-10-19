package com.wangshiqi.pineappleb.ui.fragment.focus;

import android.os.Bundle;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/19.
 */
public class ChannelFragment extends AbsFragment{
    public static ChannelFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.activity_dynamic_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
