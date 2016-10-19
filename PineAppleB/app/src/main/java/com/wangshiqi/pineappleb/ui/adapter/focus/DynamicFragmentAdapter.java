package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DynamicBean;
import com.wangshiqi.pineappleb.ui.adapter.AbsBaseAdapter;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/19.
 */
public class DynamicFragmentAdapter extends AbsBaseAdapter<DynamicBean,DynamicFragmentAdapter.ViewHolder> {

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
    protected void onBindViewHolder(ViewHolder viewHolder, DynamicBean itemData, int position) {
        viewHolder.titleTv.setText(itemData.getTitle());
    }

    class ViewHolder extends AbsBaseAdapter.BaseHolder{
        TextView channelNameTv,nameTv,titleTv,tagOneTv,tagTwoTv,tagThreeTv,countTv;
        ImageView circleImg,playImg,countImg;
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
        }
    }
}
