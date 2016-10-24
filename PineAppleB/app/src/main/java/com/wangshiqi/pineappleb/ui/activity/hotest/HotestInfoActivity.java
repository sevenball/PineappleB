package com.wangshiqi.pineappleb.ui.activity.hotest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.ui.activity.AbsBaseActivity;

import wkvideoplayer.util.DensityUtil;
import wkvideoplayer.view.MediaController;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/19.
 */
public class HotestInfoActivity extends AbsBaseActivity {
    private SuperVideoPlayer mSuperVideoPlayer;
    private TextView infoTitle;
    private TextView infoIntro;
    private TextView infoPlayCount;
    private TextView infoTag;

    /**
     * 播放器的回调接口
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();//关闭VideoView
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {

        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_hot_info;
    }

    @Override
    protected void initViews() {
        mSuperVideoPlayer = byView(R.id.hot_info_super_video);
        infoTitle = byView(R.id.hot_card_info_title);
        infoIntro = byView(R.id.hot_card_info_intro);
        infoPlayCount = byView(R.id.hot_card_info_play_count);
        infoTag = byView(R.id.hot_card_info_tag);
    }

    @Override
    protected void initDatas() {

        Intent intent = getIntent();
        String mp4Url = intent.getStringExtra("mp4Url");
        String title = intent.getStringExtra("title");
        String intro = intent.getStringExtra("intro");
        String infoTagContent = intent.getStringExtra("tag");
        infoTag.setText("#" + infoTagContent.replace(",", "   #"));
        int playCount = intent.getIntExtra("count", 45244);
        if (playCount > 10000) {
            String playCountFormat = String.format("%." + 1 + "f", playCount / 10000.0) + getResources().getString(R.string.hotest_play_count_unit);
            infoPlayCount.setText(playCountFormat);
        } else {
            infoPlayCount.setText(playCount + "");
        }
        infoTitle.setText(title);
        infoIntro.setText(intro);

        mSuperVideoPlayer.setVisibility(View.VISIBLE);
        mSuperVideoPlayer.setAutoHideController(false);
        Uri uri = Uri.parse(mp4Url);
        mSuperVideoPlayer.loadAndPlay(uri, 0);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
    }

    /***
     * 旋转屏幕之后回调
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }
}
