package com.iwhys.demo.animator;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.iwhys.library.animator.AnimatorHolder;
import com.iwhys.library.animator.IAnimator;
import com.iwhys.library.animator.SurfaceAnimator;

import java.util.List;


/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        6/22/16 18:51
 * Description:
 */
public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {

    private final IAnimator mAnimator;
    private final List<AnimatorHolder> mAnimatorHolders;

    public SurfaceViewDemo(Context context, List<AnimatorHolder> animatorHolders) {
        this(context, null, animatorHolders);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, List<AnimatorHolder> animatorHolders) {
        super(context, attrs);
        getHolder().addCallback(this);
        mAnimator = new SurfaceAnimator(this);
        mAnimatorHolders = animatorHolders;
    }

    final Rect rect = new Rect();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        rect.set(x - 1, y - 1, x + 1, y + 1);
        if (event.getAction() == MotionEvent.ACTION_UP){
            for (AnimatorHolder animatorHolder : mAnimatorHolders) {
                animatorHolder.reset();
                mAnimator.start(animatorHolder.originRect(rect));
            }
        }
        return true;
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
