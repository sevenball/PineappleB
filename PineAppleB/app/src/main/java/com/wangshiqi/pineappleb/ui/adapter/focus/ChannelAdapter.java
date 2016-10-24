package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.ChannelBean;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;

import java.io.PipedInputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dllo on 16/10/24.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>{
    private List<ChannelBean> channelBeen;
    private Context context;

    // ================================
    // 定义点击接口对象
    OnRvItemClick onRvItemClick;
    // 提供set方法
    public void setOnRvItemClick(OnRvItemClick onRvItemClick) {
        this.onRvItemClick = onRvItemClick;
    }
    // ================================

    public ChannelAdapter(Context context) {
        this.context = context;
    }

    public void setChannelBeen(List<ChannelBean> channelBeen) {
        this.channelBeen = channelBeen;
        notifyDataSetChanged();
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel,parent,false);
        ChannelViewHolder holder = new ChannelViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ChannelViewHolder holder, final int position) {
        // 设置recyclerView 的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  判断自定义点击接口不为空
                if(onRvItemClick != null){
                    // 获取当前行布局的position
                    int p = holder.getLayoutPosition();
                    // 获取当前行布局的数据
                    ChannelBean channelBean = channelBeen.get(position);
                    // 将数据存储到接口对象中
                    // 回调接口方法-发出命令-执行OnItemClickListener
                    onRvItemClick.onRvItemClickListener(p,channelBean);
                }
            }
        });

        holder.titleTv.setText(channelBeen.get(position).getNick());
        Picasso.with(context).load(channelBeen.get(position).getAvatar()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return channelBeen == null ? 0 : channelBeen.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        CircleImageView circleImageView;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.channel_tv);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.channel_circle);

        }
    }
}
