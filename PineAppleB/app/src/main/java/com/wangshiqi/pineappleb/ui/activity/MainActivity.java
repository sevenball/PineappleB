package com.wangshiqi.pineappleb.ui.activity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.fragment.dicovery.DiscoveryFragment;
import com.wangshiqi.pineappleb.ui.fragment.focus.FocusFragment;
import com.wangshiqi.pineappleb.ui.fragment.hotest.HotestFragment;
import com.wangshiqi.pineappleb.ui.fragment.mine.PlayFragment;

public class MainActivity extends AbsBaseActivity {
    private RadioGroup radioGroup;
    private ImageView loadAnimIv;
    private TextView loadAnimTv;
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        radioGroup = byView(R.id.main_rg);
        loadAnimIv = byView(R.id.load_anim_iv);
        loadAnimTv = byView(R.id.load_anim_tv);
    }

    @Override
    protected void initDatas() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AnimationDrawable drawable = (AnimationDrawable) loadAnimIv.getBackground();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (checkedId) {
                    case R.id.discovery_rb:
                        loadAnimIv.setVisibility(View.VISIBLE);
                        loadAnimTv.setVisibility(View.VISIBLE);
                        drawable.start();
                        transaction.replace(R.id.replace_view, DiscoveryFragment.newInstance());
                        loadAnimIv.setVisibility(View.INVISIBLE);
                        loadAnimTv.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.hotest_rb:
                        loadAnimIv.setVisibility(View.VISIBLE);
                        loadAnimTv.setVisibility(View.VISIBLE);
                        drawable.start();
                        transaction.replace(R.id.replace_view, HotestFragment.newInstance());
                        break;
                    case R.id.focus_rb:
                        loadAnimIv.setVisibility(View.VISIBLE);
                        loadAnimTv.setVisibility(View.VISIBLE);
                        drawable.start();
                        transaction.replace(R.id.replace_view, FocusFragment.newInstance());
                        break;
                    case R.id.mine_rb:
                        loadAnimIv.setVisibility(View.INVISIBLE);
                        loadAnimTv.setVisibility(View.INVISIBLE);
                        transaction.replace(R.id.replace_view, PlayFragment.newInstance());
                        break;
                }
                transaction.commit();
            }
        });
        radioGroup.check(R.id.discovery_rb);
    }
}
