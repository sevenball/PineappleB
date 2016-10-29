package com.wangshiqi.pineappleb.ui.activity.focus;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.model.bean.focus.RecommendMoreBean;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;
import com.wangshiqi.pineappleb.model.net.NetHttpJudge;
import com.wangshiqi.pineappleb.model.net.OkHttpInstance;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;
import com.wangshiqi.pineappleb.ui.adapter.focus.DiscussAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.RecmmendMoreAdapter;
import com.wangshiqi.pineappleb.ui.adapter.focus.SortSetAdapter;
import com.wangshiqi.pineappleb.utils.ValueTool;
import com.wangshiqi.pineappleb.view.DiscussListView;

import java.util.List;

import okhttp3.Call;
import wkvideoplayer.view.MediaController;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicInfoActivity extends AbsBaseActivity implements View.OnClickListener {
    // 下载相关
    private TextView downLoadTv;
    DownloadManager downManager;
    long id;
    private DownLoadCompleteReceiver receiver;

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
    public static final String VIDEOID = "videoId"; // 评论
    private long videoId;
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

    // 横竖屏保存高度

    private int saveHeight;
    private int saveWidth;

    //
    List<SortSetBean> sortSetBeen;
    List<RecommendMoreBean> recommendMoreBeen;
    private ScrollView scrollView;


    //网络判断
    private NetHttpJudge judge;
    private int netState;



    @Override
    protected int setLayout() {
        return R.layout.activity_dynamic_info;

    }

    @Override
    protected void initViews() {
        // 手势相关
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(false)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false);//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
        titleTv = byView(R.id.dynamic_info_title);
        introTv = byView(R.id.dynamic_info_intro);
        tagTv = byView(R.id.dynamic_info_tag);
        playCount = byView(R.id.dynamic_info_play);
        setTv = byView(R.id.dynamic_set_tv);
        scrollView = byView(R.id.dynamic_sv);
        sortSetRl = byView(R.id.sort_set_rl);
        recommendMoreRl = byView(R.id.recommend_more_rl);

        listView = byView(R.id.dynamic_info_lv);
        discussCount = byView(R.id.discuss_count);
        // 视频播放相关
        player = byView(R.id.dynamic_info_video);
        // 视频下载相关
        downLoadTv = byView(R.id.dynamic_info_download);
        downLoadTv.setOnClickListener(this);
        downManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        receiver = new DownLoadCompleteReceiver();
        registerReceiver(receiver, filter);


        // 返回键点击
        backImg = byView(R.id.dynamic_info_back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void initDatas() {
        // 接收跳转时传来的值
        receiveData();
        // 分集数据的设置
//        sortSetData();
        // 更多推荐数据的设置
//        recommendData();
        // 评论区数据的设置
//        discussData();
        // 视频播放相关
//        mp4Play();
        // 网络判断
        netState = judge.GetNetype(this);
        checkNet(netState);
    }

    //
    private void checkNet(int netState) {
        switch (netState) {
            case -1:
                Toast.makeText(this, "没有可用的网络", Toast.LENGTH_SHORT).show();
                break;
            case 1: // WIFI网络
                sortSetData();
                // 更多推荐数据的设置
                recommendData();
                // 评论区数据的设置
                discussData();
                // 视频播放相关
                mp4Play();
                break;
            case 2: // wap网络
                showNetStateDialog();
                break;
            case 3: // net网络
                showNetStateDialog();
                break;
        }
    }

    // 网络状态的Dialog
    private void showNetStateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.logo);
        builder.setTitle("网络状态判定");
        builder.setMessage("现在的网络状态是手机移动数据, 继续将可能产生格外的费用, 是否继续观看?");
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sortSetData();
                // 更多推荐数据的设置
                recommendData();
                // 评论区数据的设置
                discussData();
                // 视频播放相关
                mp4Play();
            }
        });
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setNeutralButton("想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create().show();
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
        OkHttpInstance.getAsyn(ValueTool.DISCUSSURLLEFT + videoId + ValueTool.DISCUSSURLRIGHT, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                DiscussBean bean = gson.fromJson(response.toString(), DiscussBean.class);
                List<DiscussBean.DataBean> listBean = bean.getData();
                discussCount.setText("(" + listBean.size() + ")");
                discussCount.setTextColor(Color.parseColor("#F09800"));
                discussAdapter.setmDatas(listBean);
            }
        });
        listView.setAdapter(discussAdapter);
    }

    // 更多推荐数据的设置
    private void recommendData() {
        recommendMoreAdapter = new RecmmendMoreAdapter(this);
        recommendMoreRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        OkHttpInstance.getAsyn(ValueTool.RECOMMENDMOREURLLEFT + videoId, new OkHttpInstance.ResultCallback() {
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
        sortSetRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        OkHttpInstance.getAsyn(ValueTool.SORTSETURL, new OkHttpInstance.ResultCallback() {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Object response) {
                sortSetBeen = JSON.parseArray(response.toString(), SortSetBean.class);
                setTv.setText("(" + sortSetBeen.size() + ")");
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
        finalTag = formatTag.replace(",", "   #  ");
        linkMp4 = intent.getStringExtra("linkMp4");
        tagTv.setText("#  " + finalTag);
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
            Log.d("MainActivity", "完成");
        }
    };

    /***
     * 旋转屏幕之后回调
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
            ViewGroup.LayoutParams params = player.getLayoutParams();
            params.height = height;
            player.setLayoutParams(params);
            scrollView.setOnTouchListener(new View.OnTouchListener() {
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
            scrollView.setOnTouchListener(new View.OnTouchListener() {
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        unregisterReceiver(receiver);
    }

    // 视频下载点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamic_info_download:
                dialogCreate();
                break;
        }
    }

    // 弹出Dialog
    private void dialogCreate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 对话框的标题和图标
        builder.setTitle("网易菠萝为您服务");
        builder.setIcon(R.drawable.ic_toast_bolo);
        // 设置信息内容
        builder.setMessage("您确定要下载吗?");
        // 设置按钮
        // 确定按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 下载视频方法
                downLoadVideo();
            }
        });
        // 忽略按钮
        builder.setNeutralButton("忽略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DynamicInfoActivity.this, "下次再说", Toast.LENGTH_SHORT).show();
            }
        });

        // 不确认的
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DynamicInfoActivity.this, "已取消下载", Toast.LENGTH_SHORT).show();
            }
        });


        // 对话框的显示需要  builder(创建者)生成并显示
        builder.create().show();
    }

    // 视频下载相关
    private void downLoadVideo() {
        DownloadManager.Request request =
                new DownloadManager.Request(Uri.parse(linkMp4));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("正在下载");
        request.setDescription(getIntent().getStringExtra("title"));
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, getIntent().getStringExtra("title"));
        //将下载请求添加至downManager,注意enqueue方法的编号为当前
        id = downManager.enqueue(request);

    }

    // 视频下载广播接收者
    private class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                Toast.makeText(DynamicInfoActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
//                Toast.makeText(MainActivity.this, "别瞎点!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
