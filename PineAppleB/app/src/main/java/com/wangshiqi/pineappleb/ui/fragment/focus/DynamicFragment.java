package com.wangshiqi.pineappleb.ui.fragment.focus;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends AbsFragment {

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

    }

    @Override
    protected void initDatas() {

    }
}
