package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iwhys.library.animator.AnimatorHolder;

import java.util.List;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/25/16 12:41
 * Description:
 */
public class ViewContainer extends LinearLayout {

    private final Rect mRect = new Rect();
    private ViewDemo mViewDemo;
    private SurfaceViewDemo mSurfaceViewDemo;

    public ViewContainer(Context context) {
        this(context, null);
    }

    public ViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mViewDemo = new ViewDemo(context, attrs);
        mSurfaceViewDemo = new SurfaceViewDemo(context, attrs);
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        addView(mViewDemo, params);
        addView(mSurfaceViewDemo, params);
    }

    public void setAnimatorHolders(List<AnimatorHolder> animatorHolders) {
        mViewDemo.setAnimatorHolders(animatorHolders);
        mSurfaceViewDemo.setAnimatorHolders(animatorHolders);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int h = getHeight() >> 1;
        y = y > h ? y - h : y;
        mRect.set(x - 1, y - 1, x + 1, y + 1);
        if (event.getAction() == MotionEvent.ACTION_UP){
            mViewDemo.start(mRect);
            mSurfaceViewDemo.start(mRect);
        }
        return true;
    }
}
