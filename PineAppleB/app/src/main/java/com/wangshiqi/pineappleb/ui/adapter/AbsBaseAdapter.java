package com.wangshiqi.pineappleb.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/10/18.
 * BaseAdapter的基类
 */
public abstract class AbsBaseAdapter<D, VH extends AbsBaseAdapter.BaseAdaper> extends BaseAdapter {

    protected List<D> mDatas;
    protected Context context;
    protected LayoutInflater inflater;

    public AbsBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setmDatas(List<D> newList) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public D getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(setItemLayout(), parent, false);
            vh = onCreateViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        D itemData = getItem(position);

        onBindViewHolder(vh, itemData, position);
        return convertView;
    }

    protected abstract int setItemLayout();

    protected abstract VH onCreateViewHolder(View convertview);

    protected abstract void onBindViewHolder(VH vh, D itemData, int position);

    protected static class BaseAdaper {
        View itemView;
        public BaseAdaper(View itemView) {
            this.itemView = itemView;
        }
    }
}
