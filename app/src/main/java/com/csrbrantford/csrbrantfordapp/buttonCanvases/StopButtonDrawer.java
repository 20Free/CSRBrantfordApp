package com.csrbrantford.csrbrantfordapp.buttonCanvases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

/**
 * Created by Jonathan on 7/7/2016.
 */
public class StopButtonDrawer {

    int totalWidth = 0;
    int totalHeight = 0;
    Bitmap bitmap = null;

    public StopButtonDrawer(int totalWidth, int totalHeight, Bitmap bitmap) {

        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.bitmap = bitmap;

    }

    public Canvas drawStopButton(int totalWidth, int totalHeight, Bitmap bitmap) {

        int ranchBlue = Color.argb(254,0,109,146);
        Canvas canvas = new Canvas(bitmap);

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Style.STROKE);
        circlePaint.setStrokeWidth(totalWidth * 0.05f);
        circlePaint.setColor(ranchBlue);

        RectF circleRect = new RectF(totalWidth * 0.03f, totalHeight * 0.03f, totalWidth * 0.94f, totalHeight * 0.94f);
        canvas.drawOval(circleRect, circlePaint);

        Paint squarePaint = new Paint();
        squarePaint.setStyle(Style.FILL);
        squarePaint.setColor(ranchBlue);
        RectF square = new RectF(totalWidth * 0.2f, totalHeight * 0.2f, totalWidth * 0.8f, totalHeight * 0.8f);
        canvas.drawRect(square, squarePaint);

        return canvas;
    }
}
