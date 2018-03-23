package com.csrbrantford.csrbrantfordapp.buttonCanvases;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by Jonathan on 7/7/2016.
 */
public class PlayButtonDrawer {

    public PlayButtonDrawer() {

    }

    public Canvas drawPlayButton(int totalWidth, int totalHeight, Bitmap bitmap){
        int heightCenter = totalHeight/2;
        int ranchBlue = Color.argb(254,0,109,146);
        Canvas canvas = new Canvas(bitmap);
        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(totalWidth * 0.05f);
        circlePaint.setColor(ranchBlue);


        RectF circleRect = new RectF(totalWidth * 0.03f, totalHeight * 0.03f, totalWidth * 0.94f, totalHeight * 0.94f);
        canvas.drawOval(circleRect, circlePaint);

        Paint trianglePaint = new Paint();
        trianglePaint.setStyle(Paint.Style.FILL);
        trianglePaint.setColor(ranchBlue);
        trianglePaint.setAntiAlias(true);

        float startOfTriangle = totalWidth * 0.9f;
        float distanceOfTriangleLine = totalWidth * 0.65f;

        Path trianglePath = new Path();
        trianglePath.setFillType(Path.FillType.EVEN_ODD);
        trianglePath.moveTo(startOfTriangle, heightCenter);
        trianglePath.lineTo(startOfTriangle - distanceOfTriangleLine, heightCenter + (distanceOfTriangleLine/2));
        trianglePath.lineTo(startOfTriangle - distanceOfTriangleLine, heightCenter - (distanceOfTriangleLine/2));
        trianglePath.close();

        canvas.drawPath(trianglePath, trianglePaint);
        return canvas;
    }
}