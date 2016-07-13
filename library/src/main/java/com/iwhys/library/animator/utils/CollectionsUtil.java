package com.iwhys.library.animator.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/13/16 13:04
 * Description:
 */
public class CollectionsUtil {

    public static <T> List<T> getSnapshot(Collection<T> src){
        List<T> result = new ArrayList<>(src.size());
        for (T item : src) {
            result.add(item);
        }
        return result;
    }
}
