package com.papercanary.freeearn.SpeedXGame;//package com.papercanary.freeearn.SpeedXGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SpeedXView extends View {

    private Paint mainRectPaint, fallingRectPaint;
    private RectF mainRect;
    private List<RectF> fallingRects;
    private float mainRectX, mainRectY;
    private boolean gameOver;

    public SpeedXView(Context context) {
        super(context);
        init();
    }

    public SpeedXView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init() {
        mainRectPaint = new Paint();
        mainRectPaint.setColor(Color.BLUE);

        fallingRectPaint = new Paint();
        fallingRectPaint.setColor(Color.RED);

        mainRect = new RectF();
        fallingRects = new ArrayList<>();

        mainRectX = getWidth() / 2f; // Initial position at the center horizontally
        mainRectY = getHeight() - 200; // Initial position above the bottom

        startGame();
    }

    private void startGame() {
        gameOver = false;
        fallingRects.clear();
        startFallingRects();
        invalidate();
    }

    private void startFallingRects() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver) {
                    Random random = new Random();
                    float rectX = random.nextInt(getWidth() - 200); // Random x position
                    float rectY = -100; // Start above the view
                    float rectWidth = 100;
                    float rectHeight = 100;
                    RectF fallingRect = new RectF(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
                    fallingRects.add(fallingRect);
                    postInvalidate(); // Refresh the view in UI thread
                }
            }
        }, 1000, 1500); // Delay before the first rectangle, and interval between subsequent rectangles
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mainRect.set(mainRectX - 100, mainRectY - 50, mainRectX + 100, mainRectY + 50);
        canvas.drawRect(mainRect, mainRectPaint);

        for (RectF rect : fallingRects) {
            canvas.drawRect(rect, fallingRectPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameOver) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float touchX = event.getX();
                    if (touchX < getWidth() / 2) {
                        // Move left
                        mainRectX -= 20;
                    } else {
                        // Move right
                        mainRectX += 20;
                    }
                    invalidate();
                    break;
            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        init(); // Reinitialize on size change
    }
}

