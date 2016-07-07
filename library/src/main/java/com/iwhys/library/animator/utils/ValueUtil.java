package com.iwhys.library.animator.utils;

import android.util.Pair;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/8/16 06:13
 * Description:
 */
public class ValueUtil {

    /**
     * Random float.
     *
     * @param max the max
     * @return the float
     */
    public static float random(float max){
        return random(max, 0);
    }

    /**
     * Random float.
     *
     * @param max the max
     * @param min the min
     * @return the float
     */
    public static float random(float max, float min){
        return (float) (Math.random() * (max - min) + min);
    }

    /**
     * Get the value into the range
     * @param min the min value
     * @param max the max value
     * @param factor the factor to change the value,should be value[0, 1]
     * @return the result value
     */
    public static float range(float min, float max, float factor){
        return min + (max - min) * factor;
    }

    /**
     * Get the segment relative progress value and the odd-even from the whole animator progress
     * @param progress the value of the progress
     * @param whole the whole value of the segment
     * @return the progress of the segment and the odd-even
     */
    public static Pair<Float, Boolean> segment(float progress, float whole){
        boolean even = ((int) (progress / whole) & 1) == 0;
        float p = (progress % whole) / whole;
        return new Pair<>(p, even);
    }
}
