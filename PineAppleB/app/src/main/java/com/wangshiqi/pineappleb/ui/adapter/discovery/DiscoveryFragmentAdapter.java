package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/10/17.
 */
public class DiscoveryFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles = new String[]{"推荐", "必看"};

    public DiscoveryFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
