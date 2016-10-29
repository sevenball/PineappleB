package com.wangshiqi.pineappleb.ui.fragment.focus;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.activity.focus.DynamicInfoActivity;
import com.wangshiqi.pineappleb.ui.adapter.focus.DynamicFragmentAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.util.List;

import okhttp3.Call;

/**
 * 动态界面Fragment
 */
public class DynamicFragment extends AbsFragment {
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DynamicFragmentAdapter adapter;

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
        swipeRefreshLayout = byView(R.id.dynamic_sr);
        listView = byView(R.id.dynamic_listview);
        adapter = new DynamicFragmentAdapter(context);

    }

    @Override
    protected void initDatas() {
        // 动态界面数据获取
        getDatas();
        // listview监听事件
        itemListener();
        // 刷新设置
        refreshDatas();
    }

    // 刷新设置方法
    private void refreshDatas() {
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //
        // 设置下拉刷新距离
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        // 设置进度条背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#F09800"));
        // 设置动画的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.WHITE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LongTimeOperationTask task = new LongTimeOperationTask();
                task.execute();
            }
        });

    }

    // 异步任务方法
    private class LongTimeOperationTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            SystemClock.sleep(2000);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    // listview监听事件和传值
    private void itemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DynamicBean.ListBean listBean = (DynamicBean.ListBean) parent.getItemAtPosition(position);
                if(listBean != null){
                    String title = listBean.getTitle();
                    String linkMp4 = listBean.getLinkMp4();
                    String intro = listBean.getIntro();
                    int  playCount = listBean.getPlayCount();
                    String tag = listBean.getTag();
                    long videoId = listBean.getVideoId();
                    Intent intent = new Intent(context, DynamicInfoActivity.class);
                    intent.putExtra(DynamicInfoActivity.TITLE,title);
                    intent.putExtra(DynamicInfoActivity.LINKMP4,linkMp4);
                    intent.putExtra(DynamicInfoActivity.INTRO,intro);
                    intent.putExtra(DynamicInfoActivity.TAG,tag);
                    intent.putExtra(DynamicInfoActivity.VIDEOID,videoId);
                    intent.putExtra(DynamicInfoActivity.PLAYCOUNT,playCount);
                    context.startActivity(intent);
                    // 页面切换动画
//                    getActivity().overridePendingTransition(R.anim.anim_enter_translate,R.anim.anim_exit_translate);



                }
            }
        });
    }

    // 动态界面数据获取
    private void getDatas() {
        OkHttpInstance.getAsyn(ValueTool.DYNAMICURL, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                DynamicBean dynamicBean = gson.fromJson(response.toString(),DynamicBean.class);
                List<DynamicBean.ListBean> list = dynamicBean.getList();
                adapter.setmDatas(list);
            }
        });
        listView.setAdapter(adapter);
    }


}
