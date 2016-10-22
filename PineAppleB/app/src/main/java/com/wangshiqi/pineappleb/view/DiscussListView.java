package com.wangshiqi.pineappleb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by dllo on 16/10/22.
 */
public class DiscussListView extends ListView {
    public DiscussListView(Context context) {
        super(context);
    }
    public DiscussListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DiscussListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
