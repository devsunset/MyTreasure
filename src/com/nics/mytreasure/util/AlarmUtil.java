/*
* @(#)AlarmUtil.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * <PRE>
 * My Treasure AlarmUtil
 * </PRE>
 * 
 * @author ∞≠∞Ê»Ò
 * @version 1.0
 * @since mytreasure1.0
 */
public class AlarmUtil {

	private static final int ALARM_REQUEST = 105170545;
	
	private static final String INTENT_FILTER_NAME = "com.nics.mytreasure.alarm.message";
	
	
	public static void cancel( Context context ){
		AlarmManager mAlarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pintent = PendingIntent.getBroadcast(context, ALARM_REQUEST, new Intent(INTENT_FILTER_NAME), PendingIntent.FLAG_CANCEL_CURRENT);
		mAlarm.cancel(pintent);
	}
	
	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings({ "unused", "static-access" })
	public static void alram(Context context){
        Intent intent = new Intent(context, AlarmUtil.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, ALARM_REQUEST, new Intent(INTENT_FILTER_NAME), PendingIntent.FLAG_CANCEL_CURRENT);
        try
        {
        	final Calendar c = Calendar.getInstance();       
            String sCurrentDay = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
            
            Date tomorrow = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(sCurrentDay+" 09:00:00");
            AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            am.setInexactRepeating(AlarmManager.RTC, tomorrow.getTime(), 24 * 60 * 60 * 1000, sender);
        } catch (ParseException e){
            e.printStackTrace();
        }
	}
}
