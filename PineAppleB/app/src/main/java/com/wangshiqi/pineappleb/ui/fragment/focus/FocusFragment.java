package com.wangshiqi.pineappleb.ui.fragment.focus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.adapter.focus.FocusFragmentAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/17.
 */
public class FocusFragment extends AbsFragment {
    /**
     * 标题栏
     */
    private TabLayout titleTab;
    private TextView titleTv;
    private ImageView titleImg;
    private ViewPager focusVp;
    private FocusFragmentAdapter fragmentAdapter;

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
        titleTab = byView(R.id.title_tab);
        titleTv = byView(R.id.title_tv);
        titleImg = byView(R.id.title_img);
        focusVp = byView(R.id.focus_vp);

    }

    @Override
    protected void initDatas() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(DynamicFragment.newInstance());
        fragments.add(ChannelFragment.newInstance());
        fragmentAdapter = new FocusFragmentAdapter(getChildFragmentManager(),fragments);
        focusVp.setAdapter(fragmentAdapter);
        titleTab.setupWithViewPager(focusVp);

        String [] titles = getResources().getStringArray(R.array.focus_tab);
        for (int i = 0; i < 2; i++) {
            titleTab.getTabAt(i).setText(titles[i]);
        }
        titleTv.setVisibility(View.GONE);
        titleImg.setVisibility(View.GONE);

    }
}
