package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.RecommendStrongBean;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/22.
 */
public class RecommendStrongRvAdapter extends RecyclerView.Adapter<RecommendStrongRvAdapter.MyViewHolder> {
    private Context context;
    private List<RecommendStrongBean> datas;
    private OnRvItemClick onRvItemClick;

    public RecommendStrongRvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<RecommendStrongBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnRvItemClick(OnRvItemClick onRvItemClick) {
        this.onRvItemClick = onRvItemClick;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_rv, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecommendStrongBean bean = datas.get(position);
        Glide.with(context).load(bean.getAvatar()).into(holder.avatarImg);
        Glide.with(context).load(bean.getCover()).into(holder.coverImg);
        holder.titleTv.setText(bean.getTitle());
        holder.playCount.setText(bean.getPlayCount() + "次");
        holder.channelName.setText(bean.getChannelName());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImg, coverImg;
        TextView channelName, playCount, titleTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.item_strong_avatar_img);
            coverImg = (ImageView) itemView.findViewById(R.id.item_cover_img);
            channelName = (TextView) itemView.findViewById(R.id.item_strong_channelName_tv);
            playCount = (TextView) itemView.findViewById(R.id.item_strong_playcount);
            titleTv = (TextView) itemView.findViewById(R.id.item_strong_title_tv);
        }
    }
}
