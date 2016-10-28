package com.wangshiqi.pineappleb.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.dicovery.DiscoveryFragment;
import com.wangshiqi.pineappleb.ui.fragment.focus.FocusFragment;
import com.wangshiqi.pineappleb.ui.fragment.hotest.HotestFragment;
import com.wangshiqi.pineappleb.ui.fragment.mine.PlayFragment;

public class MainActivity extends AbsBaseActivity {
    private RadioGroup radioGroup;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        radioGroup = byView(R.id.main_rg);
    }

    @Override
    protected void initDatas() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
                switch (checkedId) {
                    case R.id.discovery_rb:
                        transaction.replace(R.id.replace_view, DiscoveryFragment.newInstance());
                        break;
                    case R.id.hotest_rb:
                        transaction.replace(R.id.replace_view, HotestFragment.newInstance());
                        break;
                    case R.id.focus_rb:
                        transaction.replace(R.id.replace_view, FocusFragment.newInstance());
                        break;
                    case R.id.mine_rb:
                        transaction.replace(R.id.replace_view, PlayFragment.newInstance());
                        break;
                }
                transaction.commit();
            }
        });
        radioGroup.check(R.id.discovery_rb);
    }
}
