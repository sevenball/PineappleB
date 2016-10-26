package com.wangshiqi.pineappleb.ui.adapter.discovery;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangshiqi.pineappleb.R;
import com.wangshiqi.pineappleb.model.bean.dicovery.MustWatchBean;
import com.wangshiqi.pineappleb.ui.adapter.AbsBaseAdapter;
import com.wangshiqi.pineappleb.utils.ScreenSizeUtil;

/**
 * Created by dllo on 16/10/24.
 */
public class MustWatchLvAdapter extends AbsBaseAdapter<MustWatchBean, MustWatchLvAdapter.MustWatchViewHolder> {
    public MustWatchLvAdapter(Context context) {
        super(context);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.item_watch;
    }

    @Override
    protected MustWatchViewHolder onCreateViewHolder(View convertview) {
        return new MustWatchViewHolder(convertview);
    }


    @Override
    protected void onBindViewHolder(MustWatchViewHolder mustWatchViewHolder, MustWatchBean itemData, int position) {
        mustWatchViewHolder.bannerName.setText(itemData.getBannerName());
        if (!itemData.getBannerTag().equals("")) {
            mustWatchViewHolder.bannerTag.setVisibility(View.VISIBLE);
            mustWatchViewHolder.bannerTag.setText(itemData.getBannerTag());
            mustWatchViewHolder.bannerTag.setBackground(context.getDrawable(R.drawable.fuli_shape));
        } else {
            mustWatchViewHolder.bannerTag.setVisibility(View.INVISIBLE);
        }
        Picasso.with(context).load(itemData.getBannerPic()).resize(ScreenSizeUtil.getScreenWidth(context), ScreenSizeUtil.getScreenHeight(context) / 4).into(mustWatchViewHolder.bannerPic);
    }



    class MustWatchViewHolder extends AbsBaseAdapter.BaseHolder {
        ImageView bannerPic;
        TextView bannerName, bannerTag;
        public MustWatchViewHolder(View itemView) {
            super(itemView);
            bannerTag = (TextView) itemView.findViewById(R.id.banner_tag);
            bannerName = (TextView) itemView.findViewById(R.id.banner_name);
            bannerPic = (ImageView) itemView.findViewById(R.id.banner_pic);
        }
    }
}
