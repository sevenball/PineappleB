package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.ui.adapter.AbsBaseAdapter;
import com.wangshiqi.pineappleb.utils.ImageLoaderTool;


import java.io.PipedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicFragmentAdapter extends AbsBaseAdapter<DynamicBean.ListBean,DynamicFragmentAdapter.ViewHolder> {

    public DynamicFragmentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_dynamic;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View convertview) {
        return new ViewHolder(convertview);
    }

    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, DynamicBean.ListBean itemData, int position) {
        // 视频时长格式转换
        long formatTime = itemData.getDuration()*1000;
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String time = formatter.format(formatTime);

        viewHolder.durationBtn.setText(time);
        viewHolder.durationBtn.setTextColor(Color.parseColor("#ffffff"));
        viewHolder.titleTv.setText(itemData.getTitle());
        viewHolder.channelNameTv.setText(itemData.getChannelName());
        viewHolder.nameTv.setText(itemData.getSetName());
        viewHolder.countTv.setText(itemData.getPlayCount()+"次");
        ImageLoaderTool.loadImage(itemData.getAvatar(),viewHolder.circleImg);
        viewHolder.tagOneTv.setText("#"+itemData.getTag());
        ImageLoaderTool.loadImage(itemData.getCover(),viewHolder.coverImg);
    }

    class ViewHolder extends AbsBaseAdapter.BaseHolder{
        TextView channelNameTv,nameTv,titleTv,tagOneTv,tagTwoTv,tagThreeTv,countTv;
        Button durationBtn;
        ImageView circleImg,playImg,countImg,coverImg;

        public ViewHolder(View itemView) {
            super(itemView);
            channelNameTv = (TextView) itemView.findViewById(R.id.dynamic_channel_name);
            nameTv = (TextView) itemView.findViewById(R.id.dynamic_name);
            titleTv = (TextView) itemView.findViewById(R.id.dynamic_title);
            tagOneTv = (TextView) itemView.findViewById(R.id.dynamic_tag_one);
            tagTwoTv = (TextView) itemView.findViewById(R.id.dynamic_tag_two);
            tagThreeTv = (TextView) itemView.findViewById(R.id.dynamic_tag_three);
            countTv = (TextView) itemView.findViewById(R.id.dynamic_play_count);
            circleImg = (CircleImageView) itemView.findViewById(R.id.dynamic_circle_img);
            playImg = (ImageView) itemView.findViewById(R.id.dynamic_play);
            countImg = (ImageView) itemView.findViewById(R.id.dynamic_count_img);
            coverImg = (ImageView) itemView.findViewById(R.id.dynamic_cover);
            durationBtn = (Button) itemView.findViewById(R.id.dynamic_duration);
        }
    }
}
