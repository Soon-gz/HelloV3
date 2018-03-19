package com.abings.baby.teacher.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 圆形TextView
 */
public class CircleTextView extends TextView {
  
    private Paint mBgPaint = new Paint();
  
    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
  
    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }
  
    public CircleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }  
  
    public CircleTextView(Context context) {
        this(context, null,-1);
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        int measuredWidth = getMeasuredWidth();  
        int measuredHeight = getMeasuredHeight();  
        int max = Math.max(measuredWidth, measuredHeight);  
        setMeasuredDimension(max, max);  
    }  
  
    @Override  
    public void setBackgroundColor(int color) {
        mBgPaint.setColor(color);  
    }  

    @Override  
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub  
        canvas.setDrawFilter(pfd);  
        canvas.drawCircle(getWidth()/2, getHeight()/2, Math.max(getWidth(), getHeight())/2, mBgPaint);  
        super.draw(canvas);  
    }  
}  