package com.iwhys.demo.animator.items;

import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.OvershootInterpolator;

import com.iwhys.demo.animator.MyApp;
import com.iwhys.demo.animator.R;
import com.iwhys.library.animator.AnimatorHolder;


/**
 * Author:      王洪胜
 * Email:       wanghongsheng@nq.com
 * Time:        6/21/16 21:08
 * Description:
 */
public class SomethingRandom extends AnimatorHolder.AnimatorItem {

    private final static int[] mDrawableList = new int[]{
        R.mipmap.twitter,
        R.mipmap.facebook,
        R.mipmap.linkedin,
        R.mipmap.github
    };

    private final static TimeInterpolator mInterpolator = new OvershootInterpolator();

    private final Path mPath = new Path();
    private final PathMeasure mPathMeasure = new PathMeasure();

    private ColorFilter mColorFilter;
    private final float[] mCurrentPosition = new float[2];
    private Bitmap mDrawable;

    public SomethingRandom() {
        mDrawable = BitmapFactory.decodeResource(MyApp.getInstance().getResources(), mDrawableList[(int) (Math.random() * mDrawableList.length)]);
        int w = mDrawable.getWidth();
        int h = mDrawable.getHeight();
        mCurrentRect.set(-w >> 1, -h >> 1, w >> 1, h >> 1);
        insetRect(w, h);
        mColorFilter = createColorFilter();
    }

    @Override
    protected void onAttached() {
        setDuration(5000);
        setInterpolator(mInterpolator);
        createPath();
        mPathMeasure.setPath(mPath, false);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        setCurrentRect();
        paint.setAlpha(getAlpha());
        paint.setColorFilter(getColorFilter());
        canvas.drawBitmap(mDrawable, null, mCurrentRect, paint);
    }

    @Override
    protected void onDestroy() {
        mDrawable.recycle();
    }

    private float randomX() {
        return (float) Math.random() * mWidth;
    }

    private float randomY() {
        return (float) Math.random() * mHeight;
    }

    private int getAlpha() {
        return (int) (155 * (1 - getProgress()) + 100);
    }

    private void setCurrentRect() {
        float position = mPathMeasure.getLength() * getProgress();
        mPathMeasure.getPosTan(position, mCurrentPosition, null);
        mCurrentRect.offsetTo(mCurrentPosition[0], mCurrentPosition[1]);
    }

    /**
     * 获取颜色滤镜
     *
     * @return 滤镜
     */
    private ColorFilter getColorFilter() {
        return mColorFilter;
    }

    /**
     * 创建颜色滤镜
     *
     * @return 滤镜
     */
    private ColorFilter createColorFilter() {
        float red = (float) Math.random();
        float green = (float) Math.random();
        float blue = (float) Math.random();
        float alpha = 1f;
        float[] colorMatrix = new float[]{
            red, 0, 0, 0, 0,
            0, green, 0, 0, 0,
            0, 0, blue, 0, 0,
            0, 0, 0, alpha, 0
        };
        return new ColorMatrixColorFilter(colorMatrix);
    }

    /**
     * 创建运动路径
     */
    private void createPath() {
        mPath.rewind();
        mPath.moveTo(mOriginRect.centerX(), mOriginRect.centerY());
        /**
         * 末端点应该设置为负值,保证条目自然的移除出屏幕外
         */
        mPath.cubicTo(randomX(), randomY(), randomX(), randomY(), randomX(), -mCurrentRect.height());
    }

    /**
     * 随机缩小尺寸
     * @param width 原始宽度
     * @param height 原始高度
     */
    private void insetRect(int width, int height){
        float factor = (float) (Math.random() * 0.2f);
        int insetX = (int) (width * factor);
        int insetY = (int) (height * factor);
        mCurrentRect.inset(insetX, insetY);
    }
}
