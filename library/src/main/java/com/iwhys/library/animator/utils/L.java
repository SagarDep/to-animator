package com.iwhys.library.animator.utils;

import android.util.Log;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        4/13/16 12:18
 * Description:
 */
public class L {

    private final static String TAG = "animator-to";
    private final static boolean DEBUG = true;

    public static void d(String msg){
        if (DEBUG){
            Log.d(TAG, msg);
        }
    }
}
