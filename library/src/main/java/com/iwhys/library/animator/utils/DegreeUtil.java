package com.iwhys.library.animator.utils;

import android.graphics.PathMeasure;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/8/16 06:29
 * Description:
 */
public class DegreeUtil {

    /**
     * Get the degree from the tan value,
     * it's useful to the {@link PathMeasure#getPosTan(float, float[], float[])} tool
     * @param tan the tan value
     * @return the degree
     */
    public static float degreeFromTan(float[] tan){
        if (tan.length < 2){
            throw new IllegalArgumentException("the length of the argument tan should be at least 2");
        }
        double angel = Math.atan2(tan[1], tan[0]);
        float degree = (float) (angel * (180 / Math.PI));
        /**
         * the degree value is relative from the time 00:00,
         * but the android's coordinate is from the time 03:00,
         * so we should amend the value by add 90
         */
        return degree + 90;
    }

}
