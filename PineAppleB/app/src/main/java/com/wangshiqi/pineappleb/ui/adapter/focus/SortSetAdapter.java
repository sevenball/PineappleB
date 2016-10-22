package com.wangshiqi.pineappleb.ui.adapter.focus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.focus.SortSetBean;


import java.util.List;


/**
 * Created by dllo on 16/10/22.
 */
public class SortSetAdapter extends RecyclerView.Adapter<SortSetAdapter.SortSetViewHolder> {

    private List<SortSetBean> sortSetBean;
    private Context context;

    public SortSetAdapter(Context context) {
        this.context = context;
    }


    public void setSortSetBean(List<SortSetBean> sortSetBean) {
        this.sortSetBean = sortSetBean;
        notifyDataSetChanged();
    }

    @Override
    public SortSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_set,parent,false);
        SortSetViewHolder holder = new SortSetViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SortSetViewHolder holder, int position) {
        holder.titleTv.setText(sortSetBean.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return sortSetBean == null ? 0 :sortSetBean.size();
    }

    class SortSetViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        public SortSetViewHolder(View view) {
            super(view);
            titleTv = (TextView) view.findViewById(R.id.item_sort_set_tv);
        }
    }
}
