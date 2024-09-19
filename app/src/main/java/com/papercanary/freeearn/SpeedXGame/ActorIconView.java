package com.papercanary.freeearn.SpeedXGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ActorIconView extends View {

    public int colorCode = Color.MAGENTA;
    private int xCord;
    private int yCord;

    public ActorIconView(Context context) {
        super(context);
        // Initialize coordinates to 0 here, they will be updated in onSizeChanged
        xCord = 0;
        yCord = 0;
    }

    // Override onSizeChanged to get the actual view dimensions
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        xCord = w / 2; // Set x-coordinate to the center of the view
        yCord = h / 2; // Set y-coordinate to the center of the view
    }

    public void updateCoord(int x, int y){
        xCord = x;
        yCord = y;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(colorCode);
        p.setAntiAlias(true);

        // Ensure xCord and yCord are properly initialized before drawing the rectangle
        canvas.drawRect(xCord - 100, yCord - 100, xCord + 100, yCord + 100, p);
    }
}
