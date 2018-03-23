package com.csrbrantford.csrbrantfordapp.buttonCanvases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Jonathan on 7/7/2016.
 */
public class PauseButtonDrawer {

    int totalWidth = 0;
    int totalHeight = 0;
    Bitmap bitmap = null;

    public PauseButtonDrawer(int totalWidth, int totalHeight, Bitmap bitmap) {

        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.bitmap = bitmap;

    }

    public Canvas drawPauseButton(int totalWidth, int totalHeight, Bitmap bitmap) {
        int ranchBlue = Color.argb(254,0,109,146);
        Canvas canvas = new Canvas(bitmap);

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(totalWidth * 0.05f);
        circlePaint.setColor(ranchBlue);

        RectF circleRect = new RectF(totalWidth * 0.03f, totalHeight * 0.03f, totalWidth * 0.94f, totalHeight * 0.94f);
        canvas.drawOval(circleRect, circlePaint);

        Paint rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(ranchBlue);

        RectF rect1 = new RectF(totalWidth * 0.2f, totalHeight * 0.2f, totalWidth * 0.4f, totalHeight * 0.8f);
        canvas.drawRect(rect1, rectPaint);

        RectF rect2 = new RectF(totalWidth * 0.6f, totalHeight * 0.2f, totalWidth * 0.8f, totalHeight * 0.8f);
        canvas.drawRect(rect2, rectPaint);
        return canvas;
    }
}
