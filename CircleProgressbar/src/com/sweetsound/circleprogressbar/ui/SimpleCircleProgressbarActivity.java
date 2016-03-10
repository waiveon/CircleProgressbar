package com.sweetsound.circleprogressbar.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import com.sweetsound.circleprogressbar.CircleProgressbar;
import com.sweetsound.circleprogressbar.R;

public class SimpleCircleProgressbarActivity extends Activity implements OnTouchListener {
    private static final String TAG = SimpleCircleProgressbarActivity.class.getSimpleName();
    
    private CircleProgressbar mCircleProgressbar;
    
    private int mFirstProgress;
    private int mSecondProgress;
    
    private Handler mSetProgressValueHandler = new Handler() {
	
	@Override
	public void handleMessage(Message msg) {
	    
	    switch(msg.arg1) {
	    case R.id.first_plus_button:
		mFirstProgress = plusValue(mFirstProgress);
		break;
	    case R.id.first_minus_button:
		mFirstProgress = minusValue(mFirstProgress);
		break;
	    case R.id.second_plus_button:
		mSecondProgress = plusValue(mSecondProgress);
		break;
	    case R.id.second_minus_button:
		mSecondProgress = minusValue(mSecondProgress);
		break;
	    }
	    
	    mCircleProgressbar.setProgress(mFirstProgress, mSecondProgress);
	    
	    switch(msg.what) {
	    case MotionEvent.ACTION_DOWN:
		Message newMsg = mSetProgressValueHandler.obtainMessage();
		newMsg.arg1 = msg.arg1;
		newMsg.what = msg.what;
		
		mSetProgressValueHandler.sendMessageDelayed(newMsg, 50);
		break;
	    case MotionEvent.ACTION_UP:
		mSetProgressValueHandler.removeMessages(MotionEvent.ACTION_DOWN);
		break;
	    }
	}
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.simple_circleprogressbar_activity);

	mFirstProgress = 70;
	mSecondProgress = 20;
	
	mCircleProgressbar = (CircleProgressbar) findViewById(R.id.circle_progressbar);
	mCircleProgressbar.setProgressbarColor(Color.RED, Color.GREEN);
	mCircleProgressbar.setProgress(mFirstProgress, mSecondProgress);
	
	findViewById(R.id.first_plus_button).setOnTouchListener(this);
	findViewById(R.id.first_minus_button).setOnTouchListener(this);
	findViewById(R.id.second_plus_button).setOnTouchListener(this);
	findViewById(R.id.second_minus_button).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
	Message msg = null;
	
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:
	    msg = mSetProgressValueHandler.obtainMessage();
	    msg.arg1 = view.getId();
	    msg.what = event.getAction();
	    break;
	case MotionEvent.ACTION_UP:
	    msg = mSetProgressValueHandler.obtainMessage();
	    msg.arg1 = view.getId();
	    msg.what = event.getAction();
	    break;
	}
	
	if (msg != null) {
	    mSetProgressValueHandler.sendMessage(msg);
	}
	
	return false;
    }
    
    private int plusValue(int currentValue) {
	if (currentValue < 100) {
	    currentValue++;
	}
	
	return currentValue;
    }
    
    private int minusValue(int currentValue) {
	if (currentValue > 0) {
	    currentValue--;
	}
	
	return currentValue;
    }
}
