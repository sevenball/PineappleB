package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.RecommendMoreBean;
import com.wangshiqi.pineappleb.utils.ImageLoaderTool;

import java.util.List;


/**
 * Created by dllo on 16/10/22.
 */
public class RecmmendMoreAdapter extends RecyclerView.Adapter<RecmmendMoreAdapter.RecommendMoreViewHolder> {

    private List<RecommendMoreBean> recommendMoreBeen;
    private Context context;

    public RecmmendMoreAdapter(Context context) {
        this.context = context;
    }

    public void setRecommendMoreBeen(List<RecommendMoreBean> recommendMoreBeen) {
        this.recommendMoreBeen = recommendMoreBeen;
        notifyDataSetChanged();
    }

    @Override
    public RecommendMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_more,parent,false);
        RecommendMoreViewHolder holder = new RecommendMoreViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecommendMoreViewHolder holder, int position) {
        holder.titleTv.setText(recommendMoreBeen.get(position).getTitle());
        ImageLoaderTool.loadImage(recommendMoreBeen.get(position).getCover(),holder.coverImg);
    }

    @Override
    public int getItemCount() {
        return recommendMoreBeen == null ? 0 :recommendMoreBeen.size();
    }

    class RecommendMoreViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        ImageView coverImg;
        public RecommendMoreViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.item_recommend_title);
            coverImg = (ImageView) view.findViewById(R.id.item_recommend_img);
        }
    }
}
