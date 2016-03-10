package com.sweetsound.circleprogressbar;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;

public class CircleProgressbar extends ImageView {
    private static final String TAG = CircleProgressbar.class.getSimpleName();
    
    private final int START_POINT = 270;
    
    private Paint mBasePaint;
    
    protected RectF mCircleRectf;

    private ArrayList<Paint> mProgressPaintList = new ArrayList<Paint>();
    private ArrayList<Float> mProgressAngleList = new ArrayList<Float>();
    
    private int[] mProgressColorArr;
    
    private float mBorderSize;
    
    public CircleProgressbar(Context context) {
	super(context);
	
	init();
    }
    
    public CircleProgressbar(Context context, AttributeSet attrs) {
	super(context, attrs);
	
	init();
    }
    
    public CircleProgressbar(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	
	init();
    }

    @Override
    public void draw(Canvas canvas) {
	super.draw(canvas);
	
	MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
	
	float radius = 0;
	
	float left 	= -1;
	float top 	= -1;
	float right 	= -1;
	float bottom 	= -1;
	float margin	= -1;
	float width = getWidth() - getPaddingLeft() - getPaddingRight();
	float height = getHeight() - getPaddingTop() - getPaddingBottom();
	
	// 높이와 너비 중 작은 것을 기준으로 원을 그린다.
	if (getWidth() > getHeight()) {
	    Log.e(TAG, "LJS== Height ==");
	    // 높이가 기준
	    top = getTop();
	    bottom = getBottom();
	    
	    margin = (getWidth() - getHeight()) / 2;
	    
	    left = getLeft() + margin;
	    right = getRight() - margin;
	} else {
	    Log.e(TAG, "LJS== Width ==");
	    // 너비가 기준
	    left = getLeft();
	    right = getRight();
	    
	    margin = (getHeight() - getWidth()) / 2;
	    
	    top = getTop() + margin;
	    bottom = getBottom() - margin;
	}
	
//	// 높이와 너비 중 작은 것을 기준으로 원을 그린다.
//	if (width > height) {
//	    Log.e(TAG, "LJS== Height ==");
//	    // 높이가 기준
//	    top = getTop();
//	    bottom = getBottom();
//	    
//	    margin = (width - height) / 2;
//	    
//	    left = getLeft() + margin;
//	    right = getRight() - margin;
//	} else {
//	    Log.e(TAG, "LJS== Width ==");
//	    // 너비가 기준
//	    left = getLeft();
//	    right = getRight();
//	    
//	    margin = (height - width) / 2;
//	    
//	    top = getTop() + margin;
//	    bottom = getBottom() - margin;
//	}
	
	// 원의 크기를 설정
	if (mCircleRectf == null) {
	    mCircleRectf = new RectF(
		    left + mBorderSize - mlp.leftMargin,
		    top + mBorderSize - mlp.topMargin,
		    right - mBorderSize - mlp.leftMargin,
		    bottom - mBorderSize - mlp.topMargin
		    );
	}
	
	radius =  mCircleRectf.width() / 2;
	
	// 기본 원
	canvas.drawCircle((getWidth() / 2), (getHeight() / 2), radius, mBasePaint);
	
//	canvas.drawRect(mCircleRectf, mBasePaint);
	
	// 사용자 Progress
	// 12시가 기준
	if (mProgressPaintList.isEmpty() == false) {
	    for (int i = 0; i < mProgressAngleList.size(); i++) {
		canvas.drawArc(mCircleRectf, START_POINT, mProgressAngleList.get(i), false, mProgressPaintList.get(i));
	    }
	}
    }
    
    /**
     * 기본적인 사항 초기화
     */
    private void init() {
	mBorderSize = 4.0f  * getResources().getDisplayMetrics().density + 0.5f;
	
	mBasePaint = new Paint();
	mBasePaint.setAntiAlias(true);
	mBasePaint.setStyle(Paint.Style.STROKE);
	mBasePaint.setStrokeJoin(Join.ROUND);
	mBasePaint.setStrokeWidth(mBorderSize);
	mBasePaint.setColor(Color.GRAY);
    }
    
    /** Progressbar의 색을 설정
     * @param colorsStr 설정할 색
     */
    public void setProgressbarColor(String... colorsStr) {
	int[] colors = new int[colorsStr.length];
	
	for (int i = 0; i < colorsStr.length; i++) {
	    colors[i] = Color.parseColor(colorsStr[i]);
	}
	
	setProgressbarColor(colors);
    }
    
    /** Progressbar의 색을 설정한다.
     * @param colors 설정할 색
     */
    public void setProgressbarColor(int... colors) {
	mProgressColorArr = colors;
	
	Paint tempPaint = null;
	
	mProgressPaintList.clear();
	
	for (int progressbarColor : mProgressColorArr) {
	    tempPaint = new Paint(mBasePaint);
	    tempPaint.setColor(progressbarColor);
	    tempPaint.setStrokeCap(Cap.ROUND);
	    
	    mProgressPaintList.add(tempPaint);
	}
	
	invalidate();
    }
    
    /** Progressbar의 값을 설정한다.
     * @param value 설정할 값 (퍼센트 기준)
     */
    public void setProgress(float... values) {
	mProgressAngleList.clear();
	
	for (float value : values) {
	    mProgressAngleList.add(toAngle(value));
	}
	
	invalidate();
    }
    
    /** 퍼센트로 받은 값을 각도로 변경한다.
     * @param inputPercents 퍼센트 입력값
     * @return 각도로 변경한 값
     */
    private float toAngle(float inputPercents) {
	return (inputPercents / 100) * 360;
    }
}
