package com.iwhys.library.animator.utils;

import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/8/16 06:13
 * Description:
 */
public class RectUtil {

    /**
     * Keep the size of the rect, and move the center to the target position
     * @param rect the rect to move
     * @param toX the target position's x value
     * @param toY the target position's y value
     */
    public static void centerTo(@NonNull Rect rect, int toX, int toY){
        rect.offset(toX - rect.centerX(), toY - rect.centerY());
    }

    /**
     * Keep the size of the rect, and move the center to the target position
     * @param rectF the rect to move
     * @param toX the target position's x value
     * @param toY the target position's y value
     */
    public static void centerTo(@NonNull RectF rectF, float toX, float toY){
        rectF.offset(toX - rectF.centerX(), toY - rectF.centerY());
    }

    /**
     * Scale the rect
     * @param rect the rect to scale
     * @param factorX the scale factorX
     * @param factorY the scale factorY
     * @param pivotX the pivotX[0, 1]
     * @param pivotY the pivotY[0, 1]
     */
    public static void scale(@NonNull Rect rect, float factorX, float factorY, @FloatRange(from = 0, to = 1f) float pivotX, @FloatRange(from = 0, to = 1f) float pivotY){
        int newWidth = (int) (rect.width() * factorX);
        int newHeight = (int) (rect.height() * factorY);
        float px = rect.left + rect.width() * pivotX;
        float py = rect.top + rect.height() * pivotY;
        int newLeft = (int) (px - newWidth * pivotX);
        int newTop = (int) (py - newHeight * pivotY);
        rect.set(newLeft, newTop, newLeft + newWidth, newTop + newHeight);
    }

    /**
     * Scale the rect
     * @param rect the rect to scale
     * @param factor the scale factor
     */
    public static void scale(@NonNull Rect rect, float factor){
        scale(rect, factor, factor, 0.5f, 0.5f);
    }

    /**
     * Scale the rectF
     * @param rect the rect to scale
     * @param factorX the scale factorX
     * @param factorY the scale factorY
     * @param pivotX the pivotX[0, 1]
     * @param pivotY the pivotY[0, 1]
     */
    public static void scale(@NonNull RectF rect, float factorX, float factorY, @FloatRange(from = 0, to = 1f) float pivotX, @FloatRange(from = 0, to = 1f) float pivotY){
        float newWidth = rect.width() * factorX;
        float newHeight = rect.height() * factorY;
        float px = rect.left + rect.width() * pivotX;
        float py = rect.top + rect.height() * pivotY;
        float newLeft = px - newWidth * pivotX;
        float newTop = py - newHeight * pivotY;
        rect.set(newLeft, newTop, newLeft + newWidth, newTop + newHeight);
    }

    /**
     * Scale the rect
     * @param rect the rect to scale
     * @param factor the scale factor
     */
    public static void scale(@NonNull RectF rect, float factor){
        scale(rect, factor, factor, 0.5f, 0.5f);
    }

}
