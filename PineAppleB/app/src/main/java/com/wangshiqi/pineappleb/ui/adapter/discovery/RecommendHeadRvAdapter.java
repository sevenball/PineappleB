package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.utils.OnRvItemClick;

import java.util.List;

/**
 * Created by dllo on 16/10/19.
 * 上方横向Rv适配器
 */
public class RecommendHeadRvAdapter extends RecyclerView.Adapter<RecommendHeadRvAdapter.ViewHolder> {

    private Context context;
    private List<HeadBean> datas;
    private OnRvItemClick onRvItemClick;

    public RecommendHeadRvAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<HeadBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnRvItemClick(OnRvItemClick onRvItemClick) {
        this.onRvItemClick = onRvItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_head, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRvItemClick != null) {
                    int p = holder.getLayoutPosition();
//                    onRvItemClick.onRvItemClickListener(p, datas);
                    Toast.makeText(context, "点击了" + p, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
