package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.DiscussBean;
import com.wangshiqi.pineappleb.ui.adapter.AbsBaseAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/22.
 */
public class DiscussAdapter extends AbsBaseAdapter<DiscussBean.DataBean,DiscussAdapter.DiscussViewHolder>{


    public DiscussAdapter(Context context) {
        super(context);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_discuss;
    }

    @Override
    protected DiscussViewHolder onCreateViewHolder(View convertview) {
        return new DiscussViewHolder(convertview);
    }

    @Override
    protected void onBindViewHolder(DiscussViewHolder discussViewHolder, DiscussBean.DataBean itemData, int position) {
        discussViewHolder.nickTv.setText(itemData.getNick());
        discussViewHolder.contentTv.setText(itemData.getContent());
        Picasso.with(context).load(itemData.getAvatar()).into(discussViewHolder.avatarImg);
    }

    class DiscussViewHolder extends AbsBaseAdapter.BaseHolder{
        TextView nickTv;
        TextView contentTv;
        CircleImageView avatarImg;

        public DiscussViewHolder(View itemView) {
            super(itemView);
            nickTv = (TextView) itemView.findViewById(R.id.discuss_nick);
            contentTv = (TextView) itemView.findViewById(R.id.discuss_content);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.discuss_circle);
        }
    }
}
