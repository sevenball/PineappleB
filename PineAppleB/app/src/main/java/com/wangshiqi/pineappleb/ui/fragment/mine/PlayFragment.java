package com.wangshiqi.pineappleb.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.mine.GPSPlayActivity;
import com.wangshiqi.pineappleb.ui.activity.mine.PayPlayActivity;
import com.wangshiqi.pineappleb.ui.activity.mine.QRPlayActivity;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

/**
 * Created by dllo on 16/10/26.
 */
public class PlayFragment extends AbsFragment implements View.OnClickListener {

    private Button qrBtn, payBtn, GPSBtn;

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
        qrBtn = byView(R.id.QR_code);
        payBtn = byView(R.id.pay_btn);
        GPSBtn = byView(R.id.GPS_btn);
    }

    @Override
    protected void initDatas() {
        qrBtn.setOnClickListener(this);
        payBtn.setOnClickListener(this);
        GPSBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.QR_code:
                startActivity(new Intent(context, QRPlayActivity.class));
                break;
            case R.id.pay_btn:
                startActivity(new Intent(context, PayPlayActivity.class));
                break;
            case R.id.GPS_btn:
                startActivity(new Intent(context, GPSPlayActivity.class));
                break;
        }
    }
}
