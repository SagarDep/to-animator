package com.iwhys.library.animator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.HandlerThread;
import android.os.Process;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        6/28/16 11:16
 * Description:
 */
public class SurfaceAnimator implements IAnimator {

    private final DrawTask mDrawTask;
    private int mWidth;
    private int mHeight;
    private SurfaceView mSurfaceView;

    /**
     * Instantiates a new Surface animator.
     *
     * @param surfaceView the surface view
     */
    public SurfaceAnimator(SurfaceView surfaceView){
        mSurfaceView = surfaceView;
        mSurfaceView.setZOrderOnTop(true);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);
        mDrawTask = new DrawTask(holder);
        mDrawTask.start();
    }

    @Override
    public void targetSizeChanged(int width, int height){
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void stop(){
        mDrawTask.stopAnimator();
    }

    @Override
    public void destroy(){
        stop();
        mDrawTask.mAnimatorItemsContainer.clear();
        AnimatorHolder.destroyAll();
        mSurfaceView = null;
        mDrawTask.quit();
    }

    @Override
    public void start(AnimatorHolder holder){
        holder.setSize(mWidth, mHeight);
        mDrawTask.start(holder);
    }


    /**
     * The drawing thread
     */
    private static class DrawTask extends HandlerThread {
        /**
         * The M animator items container.
         */
        final ArrayList<AnimatorHolder> mAnimatorItemsContainer = new ArrayList<>();
        /**
         * The M surface holder.
         */
        final SurfaceHolder mSurfaceHolder;
        /**
         * The M value animator.
         */
        ValueAnimator mValueAnimator;

        /**
         * Instantiates a new Draw task.
         *
         * @param surfaceHolder the surface holder
         */
        DrawTask(SurfaceHolder surfaceHolder){
            super("Animator", Process.THREAD_PRIORITY_DISPLAY);
            mSurfaceHolder = surfaceHolder;
        }

        boolean isAnimatorRunning(){
            return mValueAnimator != null && mValueAnimator.isRunning();
        }

        void start(AnimatorHolder holder){
            if (!isAnimatorRunning()){
                startAnimator();
            }
            if (!mAnimatorItemsContainer.contains(holder)){
                mAnimatorItemsContainer.add(holder);
            }
        }

        /**
         * Start animator.
         */
        void startAnimator(){
            mValueAnimator = ValueAnimator.ofInt(0, 1);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    performDraw();
                }
            });
            mValueAnimator.start();
        }

        /**
         * Stop animator.
         */
        void stopAnimator(){
            if (mValueAnimator != null && mValueAnimator.isRunning()){
                mValueAnimator.removeAllListeners();
                mValueAnimator.removeAllUpdateListeners();
                mValueAnimator.cancel();
                mValueAnimator = null;
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
        }

        /**
         * Perform draw.
         */
        void performDraw(){
            synchronized (mSurfaceHolder){
                Canvas canvas = mSurfaceHolder.lockCanvas(null);
                if (canvas != null){
                    onDraw(canvas);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        /**
         * Perform the real drawing action
         *
         * @param canvas canvas
         */
        void onDraw(Canvas canvas) {
            clearCanvas(canvas);
            if (mAnimatorItemsContainer.isEmpty()){
                stopAnimator();
                return;
            }
            ArrayList<AnimatorHolder> list = (ArrayList<AnimatorHolder>) mAnimatorItemsContainer.clone();
            for (AnimatorHolder holder : list) {
                if (holder.isCanceled() || holder.isFinished()){
                    mAnimatorItemsContainer.remove(holder);
                } else {
                    holder.onDraw(canvas);
                }
            }
        }

        /**
         * Clear the canvas,it's very important, because this canvas can't be auto clear
         *
         * @param canvas canvas
         */
        void clearCanvas(Canvas canvas){
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
    }
}
