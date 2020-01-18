package com.addkey.keylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author Created by ligaoyuan
 * <p>
 * 处理左右上下滑动冲突
 * @date 2019/4/12
 */
public class MyScrollView extends ScrollView {
    private static final String TAG = "MyScrollView";
    private int mPreX;
    private int mPreY;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mPreX;
                int deltaY = y - mPreY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = false;
                } else {
                    intercepted = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;

        }
        mPreY = y;
        mPreX = x;
        return intercepted;
    }
}
