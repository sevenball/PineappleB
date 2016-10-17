package com.wangshiqi.pineappleb.ui.fragment.focus;

import android.os.Bundle;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/17.
 */
public class FocusFragment extends AbsFragment {

    public static FocusFragment newInstance() {

        Bundle args = new Bundle();

        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
