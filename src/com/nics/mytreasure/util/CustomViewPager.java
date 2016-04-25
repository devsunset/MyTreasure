/*
* @(#)CustomViewPager.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * <PRE>
 * My Treasure CustomViewPager
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class CustomViewPager extends ViewPager {

	
	private boolean	enabled;
	
	public CustomViewPager(Context context) {
		super(context);
		init();
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		enabled = true;
	}	
	
	public void setPagingEnabled( boolean isEnabled ) {
		this.enabled = isEnabled;
	}
	

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		try {
			if (this.enabled) {
				return super.onTouchEvent(event);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsStrting = sw.toString();
			Log.e("INFO", exceptionAsStrting);
		}
		
		return false;
	}
	
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		try {
			
			if (this.enabled) {
				return super.onInterceptTouchEvent(event);
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsStrting = sw.toString();
			Log.e("INFO", exceptionAsStrting);
		}
		
		return false;
	}

}
