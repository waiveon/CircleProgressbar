package com.sweetsound.circleprogressbar.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.sweetsound.circleprogressbar.CircleProgressbar;
import com.sweetsound.circleprogressbar.R;

public class SimpleCircleProgressbarActivity extends Activity {
    private static final String TAG = SimpleCircleProgressbarActivity.class.getSimpleName();
    
    private CircleProgressbar mCircleProgressbar;
    
    private int mFirstProgress;
    private int mSecondProgress;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.simple_circleprogressbar_activity);

	mFirstProgress = 70;
	mSecondProgress = 20;
	
	mCircleProgressbar = (CircleProgressbar) findViewById(R.id.circle_progressbar);
	mCircleProgressbar.setProgressbarColor(Color.RED, Color.GREEN);
	mCircleProgressbar.setProgress(mFirstProgress, mSecondProgress);
    }
    
    public void setProgressOnClick(View view) {
	String viewTag = (String) view.getTag();
	
	if (viewTag.equals("1st_plus")) {
	    mFirstProgress++;
	} else if (viewTag.equals("1st_minus")) {
	    mFirstProgress--;
	} else 	if (viewTag.equals("2nd_plus")) {
	    mSecondProgress++;
	} else if (viewTag.equals("2nd_minus")) {
	    mSecondProgress--;
	}
	
	mCircleProgressbar.setProgress(mFirstProgress, mSecondProgress);
    }
}
