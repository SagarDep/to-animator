package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.iwhys.library.animator.AnimatorHolder;
import com.iwhys.library.animator.IAnimator;
import com.iwhys.library.animator.SurfaceAnimator;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        6/22/16 18:51
 * Description:
 */
public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {

    private final IAnimator mAnimator;
    private final ArrayList<AnimatorHolder> mAnimatorHolders = new ArrayList<>();

    public SurfaceViewDemo(Context context) {
        this(context, null);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        mAnimator = new SurfaceAnimator(this);
    }

    public void start(Rect rect){
        for (AnimatorHolder animatorHolder : mAnimatorHolders) {
            animatorHolder.reset();
            mAnimator.start(animatorHolder.originRect(rect));
        }
    }

    public void setAnimatorHolders(List<AnimatorHolder> animatorHolders) {
        if (animatorHolders.isEmpty()) return;
        mAnimatorHolders.clear();
        mAnimatorHolders.addAll(animatorHolders);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mAnimator.targetSizeChanged(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mAnimator.stop();
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimator.destroy();
        super.onDetachedFromWindow();
    }
}
