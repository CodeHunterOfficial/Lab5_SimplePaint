package com.example.simplepaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class TouchEventView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    Context context;
    String TAG = "DBG";

    GestureDetector gestureDetector;

    public TouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.context = context;

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLACK);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            // clean drawing area on double tap
            path.reset();
            Log.d(TAG, "Position: (" + x + "," + y + ")");

            return true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                //Log.d(TAG, "ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                //Log.d(TAG, "ACTION_MOVE");
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                //Log.d(TAG, "ACTION_UP");
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}