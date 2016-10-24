package com.wangshiqi.pineappleb.ui.activity.focus;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

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
import com.wangshiqi.pineappleb.utils.ValueTool;
import com.wangshiqi.pineappleb.view.DiscussListView;

import java.lang.reflect.Type;
import java.util.List;

import wkvideoplayer.util.DensityUtil;
import wkvideoplayer.view.MediaController;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicInfoActivity extends AbsBaseActivity {
    // 要接收的值

    private TextView titleTv;
    private TextView introTv;
    private TextView tagTv;
    private TextView playCount;
    private TextView setTv;
    // 接收暂存
    public static final String TITLE = "title";// 标题
    public static final String INTRO = "intro";// 内容
    public static final String TAG = "tag";// 标签
    public static final String PLAYCOUNT = "playCount";// 评论数
    public static final String LINKMP4 = "linkMp4";// Mp4  url
    public static final String VIDEOID= "videoId"; // 评论
    private long videoId ;
    private String linkMp4;
    private String formatTag;
    private String finalTag;

    // 视频播放
    private SuperVideoPlayer player;

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
    // CollapsingToolbarLayout的声明
    private CollapsingToolbarLayout ctl;

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
        // 视频播放相关
        player = byView(R.id.dynamic_info_video);

        ctl = byView(R.id.dynamic_coolapsing);


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
        // 视频播放相关
        mp4Play();

    }
    // 视频播放相关
    private void mp4Play() {
        player.setVideoPlayCallback(mVideoPlayCallback);
        player.setVisibility(View.VISIBLE);
        player.setAutoHideController(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("linkMp4");
        Uri uri = Uri.parse(url);
        player.loadAndPlay(uri, 0);
    }

    // 评论区数据的设置
    private void discussData() {
        discussAdapter = new DiscussAdapter(this);
        VolleyInstance.getInstance().startRequest(ValueTool.DISCUSSURLLEFT+videoId+ValueTool.DISCUSSURLRIGHT, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                DiscussBean bean = gson.fromJson(resultStr,DiscussBean.class);
                List<DiscussBean.DataBean> listBean = bean.getData();
                discussCount.setText("("+listBean.size()+")");
                discussCount.setTextColor(Color.parseColor("#F09800"));
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
        VolleyInstance.getInstance().startRequest(ValueTool.RECOMMENDMOREURLLEFT+videoId, new IVolleyResult() {
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
        VolleyInstance.getInstance().startRequest(ValueTool.SORTSETURL, new IVolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<SortSetBean>>(){}.getType();
                List<SortSetBean> bean= gson.fromJson(resultStr,type);
                sortSetAdapter.setSortSetBean(bean);
                setTv.setText("("+bean.size()+")");
                setTv.setTextColor(Color.parseColor("#F09800"));
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
        formatTag = intent.getStringExtra("tag");
        finalTag = formatTag.replace(",","   #  ");
        tagTv.setText("#  "+ finalTag);
        videoId = intent.getLongExtra("videoId", 0);
        playCount.setText(intent.getIntExtra("playCount", 0) + "次");
        playCount.setTextColor(Color.parseColor("#000000"));


    }


    //===========视频播放相关============
    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
            player.close();//关闭VideoView
            player.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                player.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                player.setPageType(MediaController.PageType.EXPAND);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {
            Log.d("MainActivity", "是");
        }
    };


    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == player) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            player.getLayoutParams().height = (int) width;
            player.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            player.getLayoutParams().height = (int) height;
            player.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            player.setPageType(MediaController.PageType.SHRINK);
        }
    }
}
