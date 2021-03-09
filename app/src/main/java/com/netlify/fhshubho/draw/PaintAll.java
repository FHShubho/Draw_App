package com.netlify.fhshubho.draw;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

public class PaintAll extends View {

    public ViewGroup.LayoutParams params;
    private Path path = new Path();
    private Path finalPath = new Path();
    private Path tempPath = new Path();
    private Path temp2 = new Path();
    private android.graphics.Paint brush = new Paint();
    private android.graphics.Paint brush2 = new Paint();
    private int lineColor = Color.RED;
    private int lineColor2 = Color.BLUE;
    private int drawMode;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float pointX;
    private float pointY;
    private ArrayList<float[]> lines = new ArrayList<float[]>();
    private ArrayList<float[]> triangles = new ArrayList<float[]>();
    private ArrayList<Float> allX = new ArrayList<Float>();
    private ArrayList<Float> allY = new ArrayList<Float>();

    public PaintAll(Context context) {
        super(context);

        brush.setAntiAlias(true);
        brush.setColor(lineColor);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);
        brush2.setAntiAlias(true);
        brush2.setColor(lineColor2);
        brush2.setStyle(Paint.Style.STROKE);
        brush2.setStrokeJoin(Paint.Join.ROUND);
        brush2.setStrokeWidth(8f);

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();

        if(drawMode == 4){
            float[] tempTriangle = new float[6];
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tempPath.moveTo(pointX, pointY);
                    allX.add(pointX);
                    allY.add(pointY);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    tempPath.lineTo(pointX, pointY);
                    allX.add(event.getX());
                    allY.add(event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    tempTriangle[0] = pointX;
                    tempTriangle[1] = pointY;
                    tempTriangle[2] = Collections.max(allX);
                    tempTriangle[3] = Collections.max(allY);
                    tempTriangle[4] = Collections.min(allX);
                    tempTriangle[5] = Collections.max(allY);
                    //Log.v("ssdjasdhsd", Collections.max(allY).toString());
                    triangles.add(tempTriangle);
                    allX.clear();
                    allY.clear();
                    tempPath.reset();
                    postInvalidate();

                default:
                    return false;
            }
        }

        else if(drawMode == 3) {
            float x, y, t, s, radius;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tempPath.moveTo(pointX, pointY);
                    allX.add(pointX);
                    allY.add(pointY);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    tempPath.lineTo(pointX, pointY);
                    allX.add(event.getX());
                    allY.add(event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    startX = Collections.min(allX);
                    startY = Collections.min(allY);
                    endX = Collections.max(allX);
                    endY = Collections.max(allY);
                    x = (startX + endX) / 2;
                    y = (startY + endY) / 2;
                    t = y - startY;
                    s = x -startX;
                    radius = Math.min(t, s);
                    //radius = (float) (Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((startY - endY), 2)) / 2);
                    Log.v("ssdjasdhsd", String.valueOf(radius));
                    temp2.addCircle(x, y, radius, Path.Direction.CW);
                    finalPath.addPath(temp2);
                    allX.clear();
                    allY.clear();
                    tempPath.reset();
                    temp2.reset();
                    postInvalidate();

                default:
                    return false;
            }
        }

        else if(drawMode == 2) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempPath.moveTo(pointX, pointY);
                allX.add(pointX);
                allY.add(pointY);
                return true;

            case MotionEvent.ACTION_MOVE:
                tempPath.lineTo(pointX, pointY);
                allX.add(event.getX());
                allY.add(event.getY());
                break;

            case MotionEvent.ACTION_UP:
                startX = Collections.min(allX);
                startY = Collections.min(allY);
                endX = Collections.max(allX);
                endY = Collections.max(allY);
                //Log.v("ssdjasdhsd", Collections.max(allY).toString());
                temp2.addRect(startX, startY, endX, endY, Path.Direction.CW);
                finalPath.addPath(temp2);
                allX.clear();
                allY.clear();
                tempPath.reset();
                temp2.reset();
                postInvalidate();

            default:
                return false;
             }
        }

        else if(drawMode == 1){
            float [] tempLines = new float[4];
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tempPath.moveTo(pointX, pointY);
                    startX = pointX;
                    startY = pointY;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    tempPath.lineTo(pointX, pointY);
                    endX = event.getX();
                    endY = event.getY();
                    break;

                case MotionEvent.ACTION_UP:
                    tempLines[0] = startX;
                    tempLines[1] = startY;
                    tempLines[2] = endX;
                    tempLines[3] = endY;
                    lines.add(tempLines);
                    tempPath.reset();
                    postInvalidate();

                default:
                    return false;
             }
        }

        else if(drawMode == 0){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(pointX, pointY);
                    return true;

                case MotionEvent.ACTION_MOVE:
                    path.lineTo(pointX, pointY);
                    break;

                default:
                    return false;
            }
        }

        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(tempPath, brush);
        canvas.drawPath(path, brush);
        if(lines.size() > 0) {
            for (int i=0; i< lines.size(); i++){
                float[] temp = lines.get(i);
                canvas.drawLines(temp, brush2);
            }
        }
        if(triangles.size() > 0) {
            for(int i=0; i < triangles.size(); i++) {
                float[] temp = triangles.get(i);
                temp2.moveTo(temp[0], temp[1]);
                temp2.lineTo(temp[2], temp[3]);
                temp2.lineTo(temp[4],temp[5]);
                temp2.lineTo(temp[0], temp[1]);
            }
            canvas.drawPath(temp2, brush2);
            temp2.reset();
        }
        canvas.drawPath(finalPath, brush2);
        //canvas.drawRect(startX, startY, endX, endY, brush);
        //Log.v("path gula", finalPath.toString());
    }

    public void freeHandDraw() {
        drawMode = 0;
    }

    public void lineDraw() {
        drawMode = 1;
    }

    public void rectangleDraw() {
        drawMode = 2;
    }

    public void circleDraw() {
        drawMode = 3;
    }

    public void triangleDraw() {
        drawMode = 4;
    }

    public void clearCanvas() {
        this.setBackgroundColor(Color.WHITE);
        path.reset();
        finalPath.reset();
        lines.clear();
        triangles.clear();
        invalidate();
    }

}