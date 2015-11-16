package com.zongyou.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.zongyou.library.R;

/**
 * 倾斜TextView
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time Jan 9, 2015 1:49:39 PM
 */
public class RotateTextView extends TextView {
	private static final int DEFAULT_DEGREES = 0;  
	  
    @SuppressWarnings("unused")
	private int mDegrees,mTransY; 
  
    public RotateTextView(Context context) {  
        super(context, null);  
    }  
  
    public RotateTextView(Context context, AttributeSet attrs) {  
        super(context, attrs, android.R.attr.textViewStyle);  
  
        this.setGravity(Gravity.CENTER);  
  
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RotateTextView);  
  
        mDegrees=a.getInt(R.styleable.RotateTextView_degree, DEFAULT_DEGREES);  
       // mDegrees = a.getDimensionPixelSize(R.styleable.RotateTextView_degree, DEFAULT_DEGREES);  
        mTransY = a.getDimensionPixelSize(R.styleable.RotateTextView_transY, DEFAULT_DEGREES);  
        a.recycle();  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {  
        canvas.save();  
        //canvas.translate(getCompoundPaddingLeft(), getex);  
        canvas.translate(getMeasuredWidth()*1, getMeasuredHeight()*0.5f);
        canvas.rotate(mDegrees, getMeasuredWidth() / 2f, getMeasuredHeight() / 2f);  
        super.onDraw(canvas);  
        canvas.restore();  
    }  
  
    public void setDegrees(int degrees) {  
        mDegrees = degrees;  
    }  
}
