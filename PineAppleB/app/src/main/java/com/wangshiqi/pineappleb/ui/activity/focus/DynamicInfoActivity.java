package com.wangshiqi.pineappleb.ui.activity.focus;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangshiqi.pineappleb.R;

import com.wangshiqi.pineappleb.model.bean.dicovery.MustWatchBean;
import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.model.bean.focus.RecommendMoreBean;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;
import com.wangshiqi.pineappleb.model.net.IVolleyResult;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.model.net.VolleyInstance;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;
import com.wangshiqi.pineappleb.ui.adapter.focus.DiscussAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.RecmmendMoreAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.SortSetAdapter;
import com.wangshiqi.pineappleb.utils.ValueTool;
import com.wangshiqi.pineappleb.view.DiscussListView;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import wkvideoplayer.util.DensityUtil;
import wkvideoplayer.view.MediaController;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicInfoActivity extends AbsBaseActivity {

    private MediaController mediaController;

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

    private ImageView backImg;

    // 加手势退出界面
    private GestureDetectorCompat detectorCompat;
    // 横竖屏保存高度

    private int saveHeight;
    private int saveWidth;

    //
    List<SortSetBean> sortSetBeen;
    List<RecommendMoreBean> recommendMoreBeen;
    private ScrollView scrollView;


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
        scrollView = byView(R.id.dynamic_sv);
        sortSetRl = byView(R.id.sort_set_rl);
        recommendMoreRl = byView(R.id.recommend_more_rl);

        listView =byView(R.id.dynamic_info_lv);
        discussCount = byView(R.id.discuss_count);
        // 视频播放相关
        player = byView(R.id.dynamic_info_video);
        // 横竖屏切换布局


        backImg = byView(R.id.dynamic_info_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                overridePendingTransition(R.anim.anim_enter_translate,R.anim.anim_exit_translate);
            }
        });




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
        OkHttpInstance.getAsyn(ValueTool.DISCUSSURLLEFT+videoId+ValueTool.DISCUSSURLRIGHT, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                DiscussBean bean = gson.fromJson(response.toString(),DiscussBean.class);
                List<DiscussBean.DataBean> listBean = bean.getData();
                discussCount.setText("("+listBean.size()+")");
                discussCount.setTextColor(Color.parseColor("#F09800"));
                discussAdapter.setmDatas(listBean);
            }
        });
        listView.setAdapter(discussAdapter);
    }

    // 更多推荐数据的设置
    private void recommendData() {
        recommendMoreAdapter = new RecmmendMoreAdapter(this);
        recommendMoreRl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        OkHttpInstance.getAsyn(ValueTool.RECOMMENDMOREURLLEFT+videoId, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                recommendMoreBeen = JSON.parseArray(response.toString(), RecommendMoreBean.class);
                recommendMoreAdapter.setRecommendMoreBeen(recommendMoreBeen);
            }
        });
        recommendMoreRl.setAdapter(recommendMoreAdapter);

    }

    // 分集数据的设置
    private void sortSetData() {
        sortSetAdapter = new SortSetAdapter(this);
        sortSetRl.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        OkHttpInstance.getAsyn(ValueTool.SORTSETURL, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                sortSetBeen = JSON.parseArray(response.toString(), SortSetBean.class);
                setTv.setText("("+sortSetBeen.size()+")");
                setTv.setTextColor(Color.parseColor("#F09800"));
                sortSetAdapter.setSortSetBean(sortSetBeen);
            }
        });
        sortSetRl.setAdapter(sortSetAdapter);
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
                saveWidth = player.getMeasuredWidth();
                saveHeight = player.getMeasuredHeight();

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
        if (null == player)
            return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            WindowManager wm1 = this.getWindowManager();
            int height = wm1.getDefaultDisplay().getHeight();
            Log.d("MainActivity", "height:" + height);
            ViewGroup.LayoutParams params =player.getLayoutParams();
            params.height = height;
            player.setLayoutParams(params);
            scrollView.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return true;
                }
            });

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            WindowManager wm1 = this.getWindowManager();
            int width = wm1.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams params = player.getLayoutParams();
            params.height = saveHeight;
            player.setLayoutParams(params);
            scrollView.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return false;
                }
            });
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
