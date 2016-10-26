package com.wangshiqi.pineappleb.ui.fragment.focus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.ChannelBean;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.adapter.focus.ChannelAdapter;
import com.wangshiqi.pineappleb.ui.fragment.AbsFragment;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;
import com.wangshiqi.pineappleb.utils.ValueTool;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;

/**
 * Created by dllo on 16/10/19.
 */
public class ChannelFragment extends AbsFragment{
    private ChannelAdapter channelAdapter;
    private RecyclerView recyclerView;

    public static ChannelFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void initView() {
        recyclerView = byView(R.id.channel_rv);
    }

    @Override
    protected void initDatas() {
        // 加载数据
        loadData();
        // RecyclerView的监听事件
        itemListenner();
    }
    // RecyclerView的监听事件
    private void itemListenner() {
        channelAdapter.setOnRvItemClick(new OnRvItemClick() {
            @Override
            public void onRvItemClickListener(int position, Object o) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
                
            }
        });
    }

    // 加载数据
    private void loadData() {
        channelAdapter = new ChannelAdapter(context);
        recyclerView.setLayoutManager(new GridLayoutManager(context,4));
        OkHttpInstance.getAsyn(ValueTool.CHANNELURL, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Type type= new TypeToken<List<ChannelBean>>(){}.getType();
                List<ChannelBean> bean = gson.fromJson(response.toString(),type);
                channelAdapter.setChannelBeen(bean);

            }
        });
        recyclerView.setAdapter(channelAdapter);

    }
}
