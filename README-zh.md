# to-animator
A simple android library for multi elements animator.

## Demo
* Simple - with only one animator item.<br>
![image](https://github.com/iwhys/to-animator/blob/master/screenshot/random.gif) <br>
![image](https://github.com/iwhys/to-animator/blob/master/screenshot/wave.gif)

* Compose - with multi animator items.<br>
![image](https://github.com/iwhys/to-animator/blob/master/screenshot/wave_random.gif)<br>
This demo relates to a project of the company, can not be open source for the time being.<br>
![image](https://github.com/iwhys/to-animator/blob/master/screenshot/starry.gif)
## Usage

This repository can be found on JitPack:

https://jitpack.io/#iwhys/to-animator

1) Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

2) Add the dependency:
```
dependencies {
    compile 'com.github.iwhys:to-animator:v1.0'
}
```

3) Implement your animator item extends from the abstract class AnimatorHolder$AnimatorItem:
```
public class MyAnimatorItem extends AnimatorHolder.AnimatorItem {

    @Override
    protected void onAttached() {
        /**
         * Initialize or reset all properties here, not required.
         * eg:
         * {@link #setDuration(long)}
         * {@link #setInterpolator(TimeInterpolator)}
         * Set the value of {@link #mOriginRect}
         */
    }

    @Override
    protected void onDraw(Canvas canvas, Paint paint) {
        /**
         * Do the real drawing task, required.
         * The canvas is the reference of the target(View/ViewGroup/Drawable),
         * all {@link AnimatorHolder} share with the canvas.
         * The paint is the reference of the {@link AnimatorHolder},
         * all {@link AnimatorHolder.AnimatorItem} share with the paint.
         * This method will be called by the System's VSync signal,
         * and you can get the linear progress by the method {@link #getInputValue()},
         * get the interpolator progress by the method {@link #getProgress()},
         * and then you can set the specify property value with the progress value,
         * and finally easy to implement the animation.
         */
    }
}
```

4) Custom a View or Drawable to start your animator like this:
```
public class MyView extends View {

    ......

    private UiAnimator mUiAnimator;

    private void init(){
        mUiAnimator = new UiAnimator(this);
    }
    
    public void start(){
        AnimatorHolder holder = AnimatorHolder.obtain(CircleWave.class).speed(200)
        mUiAnimator.start(holder)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mUiAnimator.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mUiAnimator.targetSizeChanged(w, h);
    }

    @Override
    protected void onDetachedFromWindow() {
        mUiAnimator.destroy();
        super.onDetachedFromWindow();
    }
    
    ......
}
```

5) See the demo for more details, and good luck.

## License
```
The MIT License (MIT)

Copyright (c) 2016 

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```