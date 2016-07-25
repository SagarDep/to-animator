package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
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



    public ViewDemo(Context context) {
        this(context, null);
    }

    public ViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimator = new UiAnimator(this);
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

    @Override
    protected void onDraw(Canvas canvas) {
        mAnimator.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimator.destroy();
        super.onDetachedFromWindow();
    }
}
