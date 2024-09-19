package com.papercanary.freeearn.SpeedXGame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.papercanary.freeearn.R;

public class SpeedXActivity extends AppCompatActivity {

    private ActorIconView actionView;
    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_xactivity);

        actionView = new ActorIconView(this);
        setContentView(actionView);

        actionView.post(new Runnable() {
            @Override
            public void run() {
                // Get the width and height of the view after it's laid out
                x = actionView.getWidth() / 2;
                y = actionView.getHeight() / 2;

                // Start updating coordinates using a Handler or another mechanism (not a while loop)
                startCoordinateUpdate();
            }
        });
    }

    private void startCoordinateUpdate() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Update coordinates and invalidate the view to trigger redraw
                y = y-10;
                actionView.updateCoord(x, y);
                handler.postDelayed(this, 100); // Repeat after 100 milliseconds (adjust as needed)
            }
        }, 100); // Initial delay before the first update
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Get the x-coordinate of the touch event
        x = x + 10;

        // Update the ActorIconView's coordinates based on the touch event
        actionView.updateCoord(x, y);

        return true;
    }
}
