package com.iwhys.demo.animator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iwhys.demo.animator.items.CircleWave;
import com.iwhys.demo.animator.items.SomethingRandom;
import com.iwhys.library.animator.AnimatorHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      iwhys
 * Email:       whs008@gmail.com
 * Time:        7/5/16 09:16
 * Description:
 */
public class DetailActivity extends AppCompatActivity {

    private final static String CLASS_PATH = "class_path";

    public static Intent getIntent(Context context, String[] classPaths){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(CLASS_PATH, classPaths);
        return intent;
    }

    public static String joinStrings(@NonNull String[] classPaths){
        StringBuilder builder = new StringBuilder();
        for (String classPath : classPaths) {
            builder.append(" ");
            builder.append(classPath.substring(classPath.lastIndexOf(".") + 1));
        }
        return builder.toString();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] classPaths = getIntent().getStringArrayExtra(CLASS_PATH);
        if (classPaths != null && classPaths.length > 0){
            List<AnimatorHolder> holders = new ArrayList<>();
            try {
                for (String s : classPaths) {
                    Class clazz = Class.forName(s);
                    AnimatorHolder holder = null;
                    if (clazz.equals(CircleWave.class)){
                        holder = AnimatorHolder.obtain(CircleWave.class).speed(250);
                    } else if (clazz.equals(SomethingRandom.class)){
                        holder = AnimatorHolder.obtain(SomethingRandom.class).speed(100);
                    }
                    holders.add(holder);
                }

                if (!holders.isEmpty()){
                    setTitle(joinStrings(classPaths));
                    View view = new ViewDemo(this, holders);
                    view.setBackgroundColor(Color.LTGRAY);
                    View surfaceView = new SurfaceViewDemo(this, holders);
                    LinearLayout container = new LinearLayout(this);
                    container.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = 1;
                    container.addView(view, params);
                    container.addView(surfaceView, params);
                    setContentView(container);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                finish();
            }
        } else {
            finish();
        }
    }


}
