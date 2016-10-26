package com.wangshiqi.pineappleb.ui.adapter.hotest;

import android.content.Context;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;
import com.wangshiqi.pineappleb.utils.ImageLoaderTool;
import com.wangshiqi.pineappleb.view.LooperFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import wkvideoplayer.view.SuperVideoPlayer;

/**
 * Created by dllo on 16/10/21.
 * 最火界面开篇的适配器
 */
public class HotCardAdapter extends BaseCardAdapter implements View.OnClickListener {
    private List<HotCardBean> datas;
    private Context context;
    private SuperVideoPlayer mSuperVideoPlayer;
    private LooperFrameLayout looperFrameLayout;
    Map<Integer, Boolean> favorState;
    Map<Integer, Boolean> playerState;
    private List<DiscussBean.DataBean> discussBeen;
    private int newPosition;


    public HotCardAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HotCardBean> datas) {
        this.datas = datas;
        favorState = new ArrayMap<>();
        playerState = new ArrayMap<>();
        for (int i = 0; i < datas.size() * 1000; i++) {
            favorState.put(i, false);
            playerState.put(i, false);
        }
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
    public void onBindData(final int position, View cardview, Object data) {
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
        looperFrameLayout = (LooperFrameLayout) cardview.findViewById(R.id.hot_card_looper_frame);


        newPosition = position % datas.size();
        // 评论轮播


        looperFrameLayout.setTipList(genertateTips());
//        looperFrameLayout.setTipList(getDiscussBeen().get(newPosition));

        /**
         *  播放器控件初始化
         */
        mSuperVideoPlayer = (SuperVideoPlayer) cardview.findViewById(R.id.hot_card_super_video_player);
        ImageView hotCardPlayer = (ImageView) cardview.findViewById(R.id.hoe_card_video_player);

        final ImageView hotCardPlayFavor = (ImageView) cardview.findViewById(R.id.hot_card_play_favor);
        ImageView hotCardShare = (ImageView) cardview.findViewById(R.id.hot_card_share);
        // 控件绑定数据
        ImageLoaderTool.loadImage(datas.get(newPosition).getAvatar(), hotCardAvatar);
        hotCardChannelName.setText(datas.get(newPosition).getChannelName());
        hotCardChannelIntro.setText(datas.get(newPosition).getChannelIntro());
        ImageLoaderTool.loadImage(datas.get(newPosition).getCover(), hotCardCover);
        hotCardTitle.setText(datas.get(newPosition).getTitle());
        String hotCardTagFormat = datas.get(newPosition).getTag().replace(",", "   #");

        hotCardTag.setText("#" + hotCardTagFormat);
        final int playCount = datas.get(newPosition).getPlayCount();
        if (playCount > 10000) {
            String playCountFormat = String.format("%." + 1 + "f", playCount / 10000.0) + context.getResources().getString(R.string.hotest_play_count_unit);
            hotCardPlayCount.setText(playCountFormat);
        } else {
            hotCardPlayCount.setText(playCount + "");
        }
        // 卡片上心的点击事件
        hotCardPlayFavor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                if (favorState.get(newPosition) == false) {
                    Log.d("HotCardAdapter", "position:" + position);
                    imageView.setImageResource(R.mipmap.video_player_favored);
                    Toast.makeText(context, "点击了心", Toast.LENGTH_SHORT).show();
                    favorState.put(newPosition, true);
                } else if (favorState.get(newPosition) == true) {
                    imageView.setImageResource(R.mipmap.video_player_favor);
                    favorState.put(newPosition, false);
                }
            }
        });
        // 播放按钮的点击事件
        hotCardPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerState.get(newPosition) == false) {
                    hotCardPlayFavor.setVisibility(View.GONE);
                    mSuperVideoPlayer.setVisibility(View.VISIBLE);
                    mSuperVideoPlayer.setAutoHideController(false);
                    Uri uri = Uri.parse(datas.get(newPosition).getLinkMp4());
                    mSuperVideoPlayer.loadAndPlay(uri, 0);
                    playerState.put(position, true);
                } else if (playerState.get(newPosition) == true) {

                    playerState.put(newPosition, false);
                }
            }
        });

        // 点击分享按钮
        hotCardShare.setOnClickListener(this);

        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
    }

    /**
     * 评论的数据
     */
    private List<String> genertateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tips.add("这个评论的数据太难了....");
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
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {

        }

        @Override
        public void onPlayFinish() {

        }
    };

    @Override
    public int getVisibleCardCount() {
        return 4;
    }

    /**
     * 卡片上的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_card_share:
                Toast.makeText(context, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
