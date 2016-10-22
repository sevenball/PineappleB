package com.wangshiqi.pineappleb.ui.activity.focus;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangshiqi.pineappleb.R;

import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.model.bean.focus.RecommendMoreBean;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;
import com.wangshiqi.pineappleb.ui.adapter.focus.DiscussAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.RecmmendMoreAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.SortSetAdapter;
import com.wangshiqi.pineappleb.view.DiscussListView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicInfoActivity extends AbsBaseActivity {
    private TextView titleTv;
    private TextView introTv;
    private TextView tagTv;
    private TextView playCount;
    private TextView setTv;
    private String linkMp4;
    // 分集RecyclerView
    private RecyclerView sortSetRl;
    private SortSetAdapter sortSetAdapter;
    // 更多推荐RecyclerView
    private RecyclerView recommendMoreRl;
    private RecmmendMoreAdapter recommendMoreAdapter;
    // 评论区ListView
    private DiscussListView listView;
    private DiscussAdapter discussAdapter;
    private TextView discussCount;


    private String recommmendMoreUrl = "http://m.live.netease.com/bolo/api/video/relations.htm?videoId=14760157184861";
    private String sortSetUrl = "http://m.live.netease.com/bolo/api/channel/setVideoList.htm?pageNum=1&sid=14642625033311&pageSize=-1";
    private String discussUrl = "http://m.live.netease.com/bolo/api/video/commentList.htm?userId=-2798972347206426236&pageSize=15&pageNum=1&videoId=14760157196091&encryptToken=5f724098912f342454785185628447bc&random=0.02496582080295462&type=0&timeStamp=1476793248225";
    @Override
    protected int setLayout() {
        return R.layout.activity_dynamic_info;
    }

    @Override
    protected void initViews() {
        titleTv = byView(R.id.dynamic_info_title);
        introTv = byView(R.id.dynamic_info_intro);
        tagTv = byView(R.id.dynamic_info_tag);
        playCount = byView(R.id.dynamic_info_play);
        setTv = byView(R.id.dynamic_set_tv);

        sortSetRl = byView(R.id.sort_set_rl);
        recommendMoreRl = byView(R.id.recommend_more_rl);

        listView =byView(R.id.dynamic_info_lv);
        discussCount = byView(R.id.discuss_count);

    }

    @Override
    protected void initDatas() {
        // 接收跳转时传来的值
        receiveData();
        // 分集数据的设置
        sortSetData();
        // 更多推荐数据的设置
        recommendData();
        // 评论区数据的设置
        discussData();


    }
    // 评论区数据的设置
    private void discussData() {
        discussAdapter = new DiscussAdapter(this);
        VolleyInstance.getInstance().startRequest(discussUrl, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                DiscussBean bean = gson.fromJson(resultStr,DiscussBean.class);
                List<DiscussBean.DataBean> listBean = bean.getData();
                discussCount.setText("("+listBean.size()+")");
                discussAdapter.setmDatas(listBean);
                listView.setAdapter(discussAdapter);
            }

            @Override
            public void failure() {

            }
        });
    }

    // 更多推荐数据的设置
    private void recommendData() {
        recommendMoreAdapter = new RecmmendMoreAdapter(this);
        recommendMoreRl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        VolleyInstance.getInstance().startRequest(recommmendMoreUrl, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<RecommendMoreBean>>(){}.getType();
                List<RecommendMoreBean> bean = gson.fromJson(resultStr,type);
                recommendMoreAdapter.setRecommendMoreBeen(bean);
                recommendMoreRl.setAdapter(recommendMoreAdapter);

            }

            @Override
            public void failure() {

            }
        });

    }

    // 分集数据的设置
    private void sortSetData() {
        sortSetAdapter = new SortSetAdapter(this);
        sortSetRl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        VolleyInstance.getInstance().startRequest(sortSetUrl, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SortSetBean>>(){}.getType();
                List<SortSetBean> bean= gson.fromJson(resultStr,type);
                sortSetAdapter.setSortSetBean(bean);
                setTv.setText("("+bean.size()+")");
                sortSetRl.setAdapter(sortSetAdapter);
            }

            @Override
            public void failure() {

            }
        });
    }

    // 接收传来的值设置给视频播放下面的内容
    private void receiveData() {

        Intent intent = getIntent();
        titleTv.setText(intent.getStringExtra("title"));
        introTv.setText(intent.getStringExtra("intro"));
        String formatTag = intent.getStringExtra("tag");
        String finalTag = formatTag.replace(",","   #  ");
        tagTv.setText("#  "+finalTag);
        playCount.setText(intent.getIntExtra("playCount", 0) + "");


    }
}
