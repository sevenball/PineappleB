package com.wangshiqi.pineappleb.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.mine.GPSPlayActivity;
import com.wangshiqi.pineappleb.ui.activity.mine.PayPlayActivity;
import com.wangshiqi.pineappleb.ui.activity.mine.QRPlayActivity;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/26.
 */
public class PlayFragment extends AbsFragment implements ArcMenu.onMenuItemClickListner {

    // 卫星菜单
    private View center;
    private ArcMenu centerArcMenu;

    public static PlayFragment newInstance() {
        Bundle args = new Bundle();
        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_play;
    }

    @Override
    protected void initView() {
        // 卫星菜单
        center=(View)byView(R.id.center);
        centerArcMenu = (ArcMenu) center.findViewById(R.id.arcMenu);

    }

    @Override
    protected void initDatas() {
        centerArcMenu.setOnMenuItemClickListner(this);
    }
    @Override
    public void onClick(View childView, int position) {
        switch (position){
            case 0:
                startActivity(new Intent(context, QRPlayActivity.class));
                break;
            case 1:
                startActivity(new Intent(context, PayPlayActivity.class));
                break;
            case 2:
                startActivity(new Intent(context, GPSPlayActivity.class));
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

}
