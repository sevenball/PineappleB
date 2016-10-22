package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.HeadBean;
import com.wangshiqi.pineappleb.ui.activity.discovery.TestActivity;

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
        return Integer.MAX_VALUE / 2;
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
        View convertView = inflater.inflate(R.layout.item_recommend_head, container, false);
        if (datas != null) {
            final int newPosition = position % datas.size();
            ImageView imageView = (ImageView) convertView.findViewById(R.id.item_rotate_img);
            final HeadBean bean = datas.get(newPosition);
            Picasso.with(context).load(bean.getCover()).config(Bitmap.Config.RGB_565).into(imageView);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra("mp4Url", bean.getLinkMp4());
                    context.startActivity(intent);
                }
            });
            container.addView(convertView);
        }
        return convertView;
    }
}
