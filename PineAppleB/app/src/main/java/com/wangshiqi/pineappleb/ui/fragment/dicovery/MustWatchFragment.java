package com.wangshiqi.pineappleb.ui.fragment.dicovery;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.MustWatchBean;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.ui.adapter.discovery.MustWatchLvAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;

import java.util.List;

import okhttp3.Call;

/**
 * Created by dllo on 16/10/24.
 */
public class MustWatchFragment extends AbsFragment {
    private ListView watchLv;
    private MustWatchLvAdapter mustWatchLvAdapter;
    private List<MustWatchBean> datas;
    private SwipeRefreshLayout mustSr;

    public static MustWatchFragment newInstance(String mustWatch) {
        Bundle args = new Bundle();
        args.putString("url", mustWatch);
        MustWatchFragment fragment = new MustWatchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mustwatch;
    }

    @Override
    protected void initView() {
        watchLv = byView(R.id.mustwatch_lv);
        mustSr = byView(R.id.must_sr);
    }

    @Override
    protected void initDatas() {
        mustSr.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉刷新距离
        mustSr.setDistanceToTriggerSync(100);
        // 设置进度条背景颜色
        mustSr.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorClassic));
        // 设置动画的颜色
        mustSr.setColorSchemeColors(Color.WHITE);
        mustSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LongTimeOperationTask task = new LongTimeOperationTask();
                task.execute();
            }
        });
        Bundle bundle = getArguments();
        String string = bundle.getString("url");
        mustWatchLvAdapter = new MustWatchLvAdapter(context);
        OkHttpInstance.getAsyn(string, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                datas = JSON.parseArray(response.toString(), MustWatchBean.class);
                mustWatchLvAdapter.setmDatas(datas);

            }
        });
        watchLv.setAdapter(mustWatchLvAdapter);

    }
    private class LongTimeOperationTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            mustSr.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            SystemClock.sleep(2000);
            Bundle bundle = getArguments();
            String string = bundle.getString("url");
            OkHttpInstance.getAsyn(string, new OkHttpInstance.ResultCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Object response) {
                    datas = JSON.parseArray(response.toString(), MustWatchBean.class);
                    mustWatchLvAdapter.setmDatas(datas);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            mustWatchLvAdapter.notifyDataSetChanged();
            mustSr.setRefreshing(false);
        }
    }

}
