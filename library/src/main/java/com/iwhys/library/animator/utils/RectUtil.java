package com.iwhys.library.animator.utils;

import android.graphics.Rect;
import android.graphics.RectF;
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
}
