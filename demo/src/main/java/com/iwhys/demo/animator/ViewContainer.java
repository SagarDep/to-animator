package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
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
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ViewContainer(Context context) {
        this(context, null);
    }

    public ViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mViewDemo = new ViewDemo(context, attrs);
        mViewDemo.setBackgroundColor(Color.BLACK);
        mSurfaceViewDemo = new SurfaceViewDemo(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics()));
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

    private final static String VIEW = "View";
    private final static String SURFACE_VIEW = "SurfaceView";

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int view = Math.round(mPaint.measureText(VIEW));
        canvas.drawText(VIEW, (getWidth() - view) >> 1, getHeight() >> 2, mPaint);
        int surfaceView = Math.round(mPaint.measureText(SURFACE_VIEW));
        canvas.drawText(SURFACE_VIEW, (getWidth() - surfaceView) >> 1, getHeight() - (getHeight() >> 2), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int h = getHeight() / getChildCount();
        y = y % h;
        mRect.set(x - 1, y - 1, x + 1, y + 1);
        if (event.getAction() == MotionEvent.ACTION_UP){
            mViewDemo.start(mRect);
            mSurfaceViewDemo.start(mRect);
        }
        return true;
    }
}
