package com.addkey.keylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author Created by ligaoyuan
 * @date 2019-05-20
 */
public class ObservableScrollView extends ScrollView {
    private ScrollViewListener mScrollViewListener;
    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollViewListener!=null){
            mScrollViewListener.onScrollChanged(this,l,t,oldl,oldt);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        mScrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
