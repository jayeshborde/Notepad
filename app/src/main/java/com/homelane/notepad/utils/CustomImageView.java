package com.homelane.notepad.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Jayesh on 1/3/2018.
 */

public class CustomImageView extends ImageView {

    private static final String TAG = CustomImageView.class.getSimpleName();
    boolean touchOn;
    boolean mDownTouch = false;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        touchOn = false;
    }

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        *//*setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));*//*
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }*/

    /*@Override
    protected void onDraw(Canvas canvas) {
        if (touchOn) {
            canvas.drawColor(Color.RED);
        } else {
            canvas.drawColor(Color.GRAY);
        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // Listening for the down and up touch events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchOn = !touchOn;
                invalidate();
                grayScale();
                mDownTouch = true;
                return true;

            case MotionEvent.ACTION_UP:
                if (mDownTouch) {
                    mDownTouch = false;
                    grayScaleClear();
                    performClick(); // Call this method to handle the response, and
                    // thereby enable accessibility services to
                    // perform this action for a user who cannot
                    // click the touchscreen.
                    return true;
                }
        }
        return false; // Return false for other touch events
    }

    @Override
    public boolean performClick() {
        // Calls the super implementation, which generates an AccessibilityEvent
        // and calls the onClick() listener on the view, if any
        super.performClick();

        // Handle the action for the custom click here

        return true;
    }

    private void grayScale() {
        long start = System.currentTimeMillis();
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        long end = System.currentTimeMillis();
        setColorFilter(filter);
        Log.d(TAG, ((end - start) + "ms"));
    }

    private void grayScaleClear() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.reset();
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        setColorFilter(filter);
    }

}
