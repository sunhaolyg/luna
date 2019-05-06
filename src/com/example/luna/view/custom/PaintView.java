package com.example.luna.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sunhao on 17-10-11.
 */

public class PaintView extends View {

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawStrokeCap(canvas);
//        drawStrokeJoin(canvas);
//        drawConrnerPathEffect(canvas);
//        drawDashPathEffectDemo(canvas);
//        drawDiscretePathEffectDemo(canvas);
//        drawArc(canvas);
        drawLayer(canvas);
    }

    private void drawLayer(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        canvas.drawColor(Color.YELLOW);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0,0,800,400,paint);
        canvas.translate(100, 100);
        canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        paint.setAlpha(125);
        canvas.drawColor(Color.RED);

        canvas.drawLine(0,0,400,800,paint);

        canvas.restore();
        canvas.drawLine(0,0,800,800,paint);


    }

    private void drawRegion(Canvas canvas) {
        Region region = new Region(0, 0, 800, 800);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        RectF r = new RectF();
    }

    private void drawArc(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        RectF rectF = new RectF(0, 0, 800, 800);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, 0, 350, false, paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        canvas.drawLine(0, 400, 1000, 400, paint);
    }

    private void drawDiscretePathEffectDemo(Canvas canvas) {
        Paint paint = getPaint();
        Path path = getPath();

        canvas.drawPath(path, paint);
        /**
         * 把原有的路线,在指定的间距处插入一个突刺
         * 第一个这些突出的“杂点”的间距,值越小间距越短,越密集
         * 第二个是突出距离
         */
        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(2, 5));
        canvas.drawPath(path, paint);

        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(6, 5));
        canvas.drawPath(path, paint);


        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(6, 15));
        canvas.drawPath(path, paint);
    }

    private void drawDashPathEffectDemo(Canvas canvas) {
        Paint paint = getPaint();
        Path path = getPath();

        canvas.translate(0, 100);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 20, 15, 15}, 0));//虚线
        canvas.drawPath(path, paint);
    }

    private void drawConrnerPathEffect(Canvas canvas) {
        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path, paint);

        paint.setPathEffect(new CornerPathEffect(200));//拐角圆滑过度
        canvas.save();
        canvas.translate(0, 150);
        canvas.drawPath(path, paint);

        paint.setPathEffect(new CornerPathEffect(20));//拐角圆滑过度
        canvas.save();
        canvas.translate(0, 150);
        canvas.drawPath(path, paint);

//        Paint paint = getPaint();
//        Path path = new Path();
//        path.moveTo(100,600);
//        path.lineTo(400,100);
//        path.lineTo(700,900);
//
//        canvas.drawPath(path,paint);
//        paint.setColor(Color.RED);
//        paint.setPathEffect(new CornerPathEffect(100));
//        canvas.drawPath(path,paint);
//
//        paint.setPathEffect(new CornerPathEffect(200));
//        paint.setColor(Color.YELLOW);
//        canvas.drawPath(path,paint);

    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        return paint;
    }


    private Path getPath() {
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(0, 0);

        // 定义路径的各个点
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150));
        }
        return path;
    }

    private void drawStrokeJoin(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(40);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(200, 200);
        paint.setStrokeJoin(Paint.Join.BEVEL);//折角
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(400, 100);
        path.lineTo(600, 100);
        path.lineTo(600, 600);
        paint.setStrokeJoin(Paint.Join.MITER);//直角
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(800, 100);
        path.lineTo(800, 400);
        path.lineTo(1000, 400);
        paint.setStrokeJoin(Paint.Join.ROUND);//圆角
        canvas.drawPath(path, paint);
    }

    private void drawStrokeCap(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(80);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);//无线帽
        canvas.drawLine(100, 100, 500, 100, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);//圆形线帽
        canvas.drawLine(100, 400, 500, 400, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);//方形线帽
        canvas.drawLine(100, 700, 500, 700, paint);
    }
}
