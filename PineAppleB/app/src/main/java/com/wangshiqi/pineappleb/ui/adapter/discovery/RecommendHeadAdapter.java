package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;

import java.util.List;

/**
 * Created by dllo on 16/10/19.
 * 上方轮播适配器
 */
public class RecommendHeadAdapter extends PagerAdapter{
    private List<HeadBean> datas;
    private Context context;
    private LayoutInflater inflater;

    public RecommendHeadAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<HeadBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // position是int最大值所以这里可能是几百甚至上千, 因此取余避免数组越界
        final int newPosition = position % datas.size();
        View convertView = inflater.inflate(R.layout.item_recommend_head, container, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_rotate_img);
        final HeadBean bean = datas.get(newPosition);
        Glide.with(context).load(bean.getCover()).into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点击", Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(convertView);
        return convertView;
    }
}
