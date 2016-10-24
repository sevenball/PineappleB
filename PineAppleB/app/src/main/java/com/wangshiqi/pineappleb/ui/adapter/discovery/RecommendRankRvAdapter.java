package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.RecommendRankBean;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by dllo on 16/10/22.
 * 人气周榜适配器
 */
public class RecommendRankRvAdapter extends RecyclerView.Adapter<RecommendRankRvAdapter.MyViewHolder> {
    private Context context;
    private List<RecommendRankBean> datas;
    private OnRvItemClick onRvItemClick;

    public RecommendRankRvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<RecommendRankBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnRvItemClick(OnRvItemClick onRvItemClick) {
        this.onRvItemClick = onRvItemClick;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rank, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecommendRankBean bean = datas.get(position);
        holder.topTv.setText("TOP " + (position + 1));
        Picasso.with(context).load(bean.getCover()).into(holder.coverImg);
        holder.nameTv.setText(bean.getName());
        float degree = bean.getDegree();
        DecimalFormat df = new DecimalFormat("###.0");
        holder.degreeTv.setText("人气值" + df.format(degree / 10000) + "万");
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImg;
        TextView topTv, nameTv, degreeTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            coverImg = (ImageView) itemView.findViewById(R.id.item_rank_cover);
            topTv = (TextView) itemView.findViewById(R.id.item_rank_top);
            nameTv = (TextView) itemView.findViewById(R.id.item_rank_name);
            degreeTv = (TextView) itemView.findViewById(R.id.item_rank_degree);
        }
    }
}
