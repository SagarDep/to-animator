package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.iwhys.library.animator.AnimatorHolder;
import com.iwhys.library.animator.UiAnimator;

import java.util.List;


/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        6/29/16 09:27
 * Description:
 */
public class ViewDemo extends View {

    private final UiAnimator mAnimator;
    private final List<AnimatorHolder> mAnimatorHolders;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ViewDemo(Context context, List<AnimatorHolder> animatorHolders) {
        this(context, null, animatorHolders);
    }

    public ViewDemo(Context context, AttributeSet attrs, List<AnimatorHolder> animatorHolders) {
        super(context, attrs);
        mAnimator = new UiAnimator(this);
        mAnimatorHolders = animatorHolders;
        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = new Rect(x - 20, y - 20, x + 20, y + 20);
        if (event.getAction() == MotionEvent.ACTION_UP){
            mAnimator.stop();
            for (AnimatorHolder animatorHolder : mAnimatorHolders) {
                mAnimator.start(animatorHolder.originRect(rect));
            }
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAnimator.targetSizeChanged(w, h);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus){
            mAnimator.stop();
        }
    }

    private boolean mHasDrawText = false;
    private final static String TEXT = "Tap the screen to start.";

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mHasDrawText){
            mHasDrawText = true;
            int value = Math.round(mPaint.measureText(TEXT));
            canvas.drawText(TEXT, (getWidth() - value) >> 1, getHeight() >> 1, mPaint);
        }
        mAnimator.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimator.destroy();
        super.onDetachedFromWindow();
    }
}
