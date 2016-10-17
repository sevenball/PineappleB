package com.wangshiqi.pineappleb.ui.fragment.dicovery;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.adapter.discovery.DiscoveryFragmentAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/17.
 */
public class DiscoveryFragment extends AbsFragment {
    /**
     * 标题栏
     */
    private TabLayout titleTab;
    private TextView titleTv;
    private ImageView titleImg;

    private ViewPager discoveryVp;
    private DiscoveryFragmentAdapter fragmentAdapter;
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
        titleTab = byView(R.id.title_tab);
        titleTv = byView(R.id.title_tv);
        titleImg = byView(R.id.title_img);
        discoveryVp = byView(R.id.dicovery_vp);
    }

    @Override
    protected void initDatas() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(RecommendFragment.newInstance());
        fragments.add(RecommendFragment.newInstance());
        fragmentAdapter = new DiscoveryFragmentAdapter(getChildFragmentManager(), fragments);
        discoveryVp.setAdapter(fragmentAdapter);
        titleTab.setupWithViewPager(discoveryVp);
        String [] titles = getResources().getStringArray(R.array.discovery_tab);
        for (int i = 0; i < 2; i++) {
            titleTab.getTabAt(i).setText(titles[i]);
        }
        titleTv.setVisibility(View.GONE);

    }
}
