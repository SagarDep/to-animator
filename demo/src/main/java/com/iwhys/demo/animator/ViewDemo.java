package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.iwhys.library.animator.AnimatorHolder;
import com.iwhys.library.animator.UiAnimator;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        6/29/16 09:27
 * Description:
 */
public class ViewDemo extends View {

    private final UiAnimator mAnimator;
    private final ArrayList<AnimatorHolder> mAnimatorHolders = new ArrayList<>();

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public ViewDemo(Context context) {
        this(context, null);
    }

    public ViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimator = new UiAnimator(this);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics()));
    }

    public void setAnimatorHolders(List<AnimatorHolder> animatorHolders) {
        if (animatorHolders.isEmpty()) return;
        mAnimatorHolders.clear();
        mAnimatorHolders.addAll(animatorHolders);
    }

    public void start(Rect rect){
        for (AnimatorHolder animatorHolder : mAnimatorHolders) {
            animatorHolder.reset();
            mAnimator.start(animatorHolder.originRect(rect));
        }
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

    private final static String TEXT = "View";

    @Override
    protected void onDraw(Canvas canvas) {
        mAnimator.onDraw(canvas);
        int value = Math.round(mPaint.measureText(TEXT));
        canvas.drawText(TEXT, (getWidth() - value) >> 1, getHeight() >> 1, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimator.destroy();
        super.onDetachedFromWindow();
    }
}
