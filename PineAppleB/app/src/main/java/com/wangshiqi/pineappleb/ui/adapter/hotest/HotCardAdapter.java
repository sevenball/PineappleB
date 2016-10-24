package com.wangshiqi.pineappleb.ui.adapter.hotest;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;
import com.wangshiqi.pineappleb.utils.ImageLoaderTool;
import com.wangshiqi.pineappleb.view.LooperFrameLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/21.
 * 最火界面开篇的适配器
 */
public class HotCardAdapter extends BaseCardAdapter implements View.OnClickListener {
    private List<HotCardBean> datas;
    private Context context;
    private boolean favorState = false;
    private SuperVideoPlayer mSuperVideoPlayer;
    private LooperFrameLayout looperFrameLayout;


    public HotCardAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HotCardBean> datas) {
        this.datas = datas;
    }

    @Override
    public List getData() {
        return datas;
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.item_hotest_card;
    }

    @Override
    public void onBindData(int position, View cardview, Object data) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        // 卡片上的布局控件初始化
        ImageView hotCardAvatar = (CircleImageView) cardview.findViewById(R.id.hot_card_avatar);
        TextView hotCardChannelName = (TextView) cardview.findViewById(R.id.hot_card_channelName);
        TextView hotCardChannelIntro = (TextView) cardview.findViewById(R.id.hot_card_channelIntro);
        ImageView hotCardCover = (ImageView) cardview.findViewById(R.id.hot_card_cover);
        TextView hotCardTitle = (TextView) cardview.findViewById(R.id.hot_card_title);
        TextView hotCardPlayCount = (TextView) cardview.findViewById(R.id.hot_card_play_count);
        TextView hotCardTag = (TextView) cardview.findViewById(R.id.hot_card_tag);
        // 评论轮播
        looperFrameLayout = (LooperFrameLayout) cardview.findViewById(R.id.hot_card_looper_frame);
        looperFrameLayout.setTipList(genertateTips());

        /**
         *  播放器控件初始化
         */
        mSuperVideoPlayer = (SuperVideoPlayer) cardview.findViewById(R.id.hot_card_super_video_player);
        ImageView hotCardPlayer = (ImageView) cardview.findViewById(R.id.hoe_card_video_player);
        hotCardPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ImageView hotCardPlayFavor = (ImageView) cardview.findViewById(R.id.hot_card_play_favor);
        ImageView hotCardShare = (ImageView) cardview.findViewById(R.id.hot_card_share);
        // 控件绑定数据
        int newPosition = position % datas.size();
        ImageLoaderTool.loadImage(datas.get(newPosition).getAvatar(), hotCardAvatar);
//        Picasso.with(context).load(datas.get(newPosition).getAvatar()).config(Bitmap.Config.RGB_565).into(hotCardAvatar);
        hotCardChannelName.setText(datas.get(newPosition).getChannelName());
        hotCardChannelIntro.setText(datas.get(newPosition).getChannelIntro());
        ImageLoaderTool.loadImage(datas.get(newPosition).getCover(), hotCardCover);
        hotCardTitle.setText(datas.get(newPosition).getTitle());
        String hotCardTagFormat = datas.get(newPosition).getTag().replace(",", "   #");

        hotCardTag.setText("#" + hotCardTagFormat);
        int playCount = datas.get(newPosition).getPlayCount();
        if (playCount > 10000) {
            String playCountFormat = String.format("%." + 1 + "f", playCount / 10000.0) + context.getResources().getString(R.string.hotest_play_count_unit);
            hotCardPlayCount.setText(playCountFormat);
        } else {
            hotCardPlayCount.setText(playCount + "");
        }
        // 卡片上的点击事件
        hotCardPlayFavor.setOnClickListener(this);
        hotCardShare.setOnClickListener(this);
        // 播放按钮的点击事件
        hotCardPlayer.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
    }

    /**
     * 评论的数据
     */
    private List<String> genertateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tips.add("QTL" + i + "是我老婆");
        }
        return tips;
    }

    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();//关闭VideoView

            mSuperVideoPlayer.setVisibility(View.GONE);
//            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
//            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
//            }
        }

        @Override
        public void onPlayFinish() {

        }
    };

    private void resetPageToPortrait() {

    }

    @Override
    public int getVisibleCardCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_card_play_favor:
                ImageView imageView = (ImageView) v;
                if (favorState == false) {
                    imageView.setImageResource(R.mipmap.video_player_favored);
                    Toast.makeText(context, "点击了心", Toast.LENGTH_SHORT).show();
                    favorState = true;
                } else if (favorState == true) {
                    imageView.setImageResource(R.mipmap.video_player_favor);
                    favorState = false;
                }

                break;
            case R.id.hot_card_share:
                Toast.makeText(context, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.hoe_card_video_player:
//                ImageView imageView1 = (ImageView) v;
//                imageView1.setVisibility(View.GONE);
//                mSuperVideoPlayer.setVisibility(View.VISIBLE);
//                mSuperVideoPlayer.setAutoHideController(false);
//                Uri uri = Uri.parse("http://bobolive.nosdn.127.net/aac_bobo_1471873939069_14732651.mp4");
//                mSuperVideoPlayer.loadAndPlay(uri, 0);
//                break;
        }
    }
}
