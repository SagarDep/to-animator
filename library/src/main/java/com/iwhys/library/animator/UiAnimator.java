package com.iwhys.library.animator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * The type Ui animator.
 */
public class UiAnimator implements IAnimator {

    /**
     * The container of the running 'AnimatorHolder'
     */
    private final ArrayList<AnimatorHolder> mAnimatorItemsContainer = new ArrayList<>();

    /**
     * The target
     */
    private Object mTarget;

    private int mWidth;

    private int mHeight;

    /**
     * Is force enable hardware
     */
    private boolean mForceHardware = false;

    private ValueAnimator mValueAnimator;

    /**
     * Instantiates a new Ui animator.
     *
     * @param target the target
     */
    public UiAnimator(Object target){
        mTarget = target;
        assertTarget();
    }

    private void initAnimator(){
        mValueAnimator = ValueAnimator.ofInt(0, 1);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                refreshCanvas();
            }
        });
        mValueAnimator.start();
        if (mForceHardware && mTarget instanceof View){
            ((View) mTarget).setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    public UiAnimator forceHardware(boolean forceHardware) {
        mForceHardware = forceHardware;
        return this;
    }

    @Override
    public void targetSizeChanged(int width, int height){
        mWidth = width;
        mHeight = height;
        for (AnimatorHolder holder : mAnimatorItemsContainer) {
            holder.setSize(mWidth, mHeight);
        }
    }

    @Override
    public void start(AnimatorHolder holder) {
        if (!(mValueAnimator != null && mValueAnimator.isRunning())){
            initAnimator();
        }
        if (!mAnimatorItemsContainer.contains(holder)){
            holder.setSize(mWidth, mHeight);
            mAnimatorItemsContainer.add(holder);
        }
    }

    @Override
    public void stop() {
        /**
         * stop the animator
         */
        if (mValueAnimator != null && mValueAnimator.isRunning()){
            mValueAnimator.removeAllListeners();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        /**
         * if necessary change restore the target's layer type.
         */
        if (mForceHardware && mTarget instanceof View){
            ((View) mTarget).setLayerType(View.LAYER_TYPE_NONE, null);
        }

        /**
         * cancel all holders,and this operation will recycle all holders
         */
        for (AnimatorHolder holder : mAnimatorItemsContainer) {
            holder.cancel();
        }
        /**
         * clear current container, and do the last canvas refresh
         */
        mAnimatorItemsContainer.clear();
        refreshCanvas();
    }

    @Override
    public void destroy(){
        stop();
        /**
         * destroy all holders
         */
        AnimatorHolder.destroyAll();
        mTarget = null;
    }

    /**
     * This method should be invoked in the target's draw method
     * {@link View#onDraw(Canvas)} or
     * {@link ViewGroup#onDraw(Canvas)} or
     * {@link Drawable#draw(Canvas)}
     *
     * @param canvas the canvas
     */
    public void onDraw(Canvas canvas) {
        /**
         * stop when mAnimatorItemsContainer is empty
         */
        if (mAnimatorItemsContainer.isEmpty()){
            stop();
            return;
        }
        final ArrayList<AnimatorHolder> list = (ArrayList<AnimatorHolder>) mAnimatorItemsContainer.clone();
        for (AnimatorHolder holder : list) {
            if (holder.isCanceled() || holder.isFinished()) {
                mAnimatorItemsContainer.remove(holder);
            } else {
                holder.onDraw(canvas);
            }
        }
    }

    private void refreshCanvas() {
        if (mTarget instanceof Drawable) {
            ((Drawable) mTarget).invalidateSelf();
        } else {
            ((View) mTarget).invalidate();
        }
    }

    private void assertTarget() {
        if (!(mTarget instanceof View) && !(mTarget instanceof Drawable)) {
            throw new IllegalArgumentException("The target must be an instance of View/ViewGroup or Drawable");
        }
        if (mTarget instanceof ViewGroup){
            ((ViewGroup) mTarget).setWillNotDraw(false);
        }
    }

}
