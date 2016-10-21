package com.wangshiqi.pineappleb.ui.adapter.hotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxq17.swipecardsview.BaseCardAdapter;
import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.hotest.HotCardBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/21.
 * 最火界面开篇的适配器
 */
public class HotCardAdapter extends BaseCardAdapter implements View.OnClickListener {
    private List<HotCardBean> datas;
    private Context context;
    private boolean favorState = false;
    private ImageView hotCardPlayFavor;
    private ImageView hotCardShare;


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

        hotCardPlayFavor = (ImageView) cardview.findViewById(R.id.hot_card_play_favor);
        hotCardShare = (ImageView) cardview.findViewById(R.id.hot_card_share);
        // 控件绑定数据
        hotCardChannelName.setText(datas.get(position).getChannelName());
        hotCardChannelIntro.setText(datas.get(position).getChannelIntro());
        Picasso.with(context).load(datas.get(position).getCover()).config(Bitmap.Config.RGB_565).into(hotCardCover);
        hotCardTitle.setText(datas.get(position).getTitle());
//        hotCardPlayCount.setText(datas.get(position).getPlayCount());
        // 卡片上的点击事件
        hotCardPlayFavor.setOnClickListener(this);
        hotCardShare.setOnClickListener(this);
    }


    @Override
    public int getVisibleCardCount() {
        return 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_card_play_favor:
                if (!favorState) {
                    hotCardPlayFavor.setImageResource(R.mipmap.video_player_favored);
                    favorState = true;
                } else {
                    hotCardPlayFavor.setImageResource(R.mipmap.video_player_favor);
                    favorState = false;
                }

                break;
            case R.id.hot_card_share:
                Toast.makeText(context, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
