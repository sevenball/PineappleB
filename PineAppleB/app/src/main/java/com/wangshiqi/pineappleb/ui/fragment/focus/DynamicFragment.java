package com.wangshiqi.pineappleb.ui.fragment.focus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.activity.focus.DynamicInfoActivity;
import com.wangshiqi.pineappleb.ui.adapter.focus.DynamicFragmentAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.util.List;

/**
 * 动态界面Fragment
 */
public class DynamicFragment extends AbsFragment {
    private ListView listView;
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
        listView = byView(R.id.dynamic_listview);
        adapter = new DynamicFragmentAdapter(context);

    }

    @Override
    protected void initDatas() {
        // 动态界面数据获取
        getDatas();
        // listview监听事件
        itemListener();


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
                }
            }
        });
    }

    // 动态界面数据获取
    private void getDatas() {
//        "http://m.live.netease.com/bolo/api/user/timeLine.htm?pageNum=1&lastTime=2016-10-15%2010%3A59%3A46&encryptToken=dfc23870c7ad025e735f8a76859d1a0d&random=0.2582823928479111&userId=-2798972347206426236&pageSize=20&timeStamp=1476446520350"
        VolleyInstance.getInstance().startRequest(ValueTool.DYNAMICURL, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                DynamicBean dynamicBean = gson.fromJson(resultStr,DynamicBean.class);
                List<DynamicBean.ListBean> list = dynamicBean.getList();
                adapter.setmDatas(list);
                listView.setAdapter(adapter);


            }

            @Override
            public void failure() {

            }
        });
    }
}
