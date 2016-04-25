/*
 * @(#)VaccAlarmReceiver.java
 * Date : 2010. 04. 01.
 * Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
 */
package com.nics.mytreasure.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nics.mytreasure.R;
import com.nics.mytreasure.contents.Vaccination;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;

/**
 * <PRE>
 * My Treasure VaccAlarmReceiver
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class VaccAlarmReceiver extends BroadcastReceiver {
	private Runnable onAlarmListener;
	
	public static final String TAG = "VaccAlarmReceiver";

	private static final String KEY_MYTREASURE_ALARMUSE_PREFERENCE = "my_treasure_alarm_use";
	private static final String KEY_MYTREASURE_ALARMDAY_PREFERENCE = "my_treasure_alarm_day";
	
	private SharedPreferences prefSet = null;
	

	@SuppressLint("SimpleDateFormat")
	@SuppressWarnings("static-access")
	@Override public void onReceive( Context context, Intent intent )
     {
		
		Cursor vaccinDayCursor = null;
		MyTreasureDatabaseHelper dbHelper = null;
        if(null != (context.getSharedPreferences("SETTING_INFO", context.MODE_PRIVATE))){
    		final Calendar c = Calendar.getInstance();       
            String sCurrentDay = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
			prefSet = context.getSharedPreferences("SETTING_INFO", context.MODE_PRIVATE);
			String sAlarmUse = prefSet.getString(KEY_MYTREASURE_ALARMUSE_PREFERENCE, "N");
			String sAlarmDay = prefSet.getString(KEY_MYTREASURE_ALARMDAY_PREFERENCE, "0");
			
			StringBuffer sbQuery = new StringBuffer();		
			sbQuery.append("SELECT T_BABY_MST._ID, T_BABY_MST.NAME,T_BABY_MST.BIRTH, \n");
			sbQuery.append("	T_VACCINATION_MST._ID AS VACCIN_ID, T_VACCINATION_MST.VACCIN_NAME, T_VACCINATION_MST.VACCIN_PERIOD_S, T_VACCINATION_MST.VACCIN_PERIOD_E,\n");
			sbQuery.append("	T_BABY_VACCINATION_MST._ID BV_ID, T_BABY_VACCINATION_MST.V_FLAG, T_BABY_VACCINATION_MST.DAY_VACCIN \n");
			sbQuery.append("FROM T_BABY_VACCINATION_MST INNER JOIN T_BABY_MST ON T_BABY_MST._ID = T_BABY_VACCINATION_MST.BABY_ID \n");
			sbQuery.append("INNER JOIN T_VACCINATION_MST ON T_VACCINATION_MST._ID = T_BABY_VACCINATION_MST.VACCIN_ID \n");
			sbQuery.append("WHERE T_BABY_VACCINATION_MST.V_FLAG = 'N' \n");
			sbQuery.append("AND T_BABY_VACCINATION_MST._ID > ? \n");
			sbQuery.append("ORDER BY T_BABY_MST._ID ASC, T_VACCINATION_MST._ID ASC");

	    	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    		@SuppressWarnings("deprecation")
			String strCheckDate = df.format(CommonUtil.getNextDay(new Date(sCurrentDay), + Integer.parseInt(sAlarmDay)));
    		
    		boolean isUpdate = false;
    		
    		if("Y".equals(sAlarmUse)){
				try{
					dbHelper = new MyTreasureDatabaseHelper(context);
					dbHelper.open();
					
					String[] saVaccinDay = new String[]{"1"};
	    			vaccinDayCursor = dbHelper.getQueryData(sbQuery.toString(), saVaccinDay);
	    			if(vaccinDayCursor.moveToFirst() && vaccinDayCursor.getCount() > 0){
	    				if(vaccinDayCursor.moveToFirst()){
	    					do{
	    						if("".equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("VACCIN_PERIOD_S"))) && "".equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("VACCIN_PERIOD_E")))){
	    							if(strCheckDate.equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("DAY_VACCIN")))){
	    								isUpdate = true;
	    							}
	    						}else{
	    							if("N".equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("DAY_VACCIN")))){
	    								String sStartDay = CommonUtil.getFutureMonthDay(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("VACCIN_PERIOD_S")),vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("BIRTH")));
    									String sEndDay = CommonUtil.getFutureMonthDay(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("VACCIN_PERIOD_E")),vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("BIRTH")));
    									
	    								// 종료 기간이 있는 경우 
	    								if(!"".equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("VACCIN_PERIOD_E")))){
	    									if((Integer.parseInt(sStartDay.replaceAll("/","")) <= Integer.parseInt(strCheckDate.replaceAll("/","")))  
	    											&&  (Integer.parseInt(strCheckDate.replaceAll("/","")) <=Integer.parseInt(sEndDay.replaceAll("/","")))){
	    										isUpdate = true;
	    									}
	    								// 시작 기간만 있는 경우
	    								}else{
	    									if(strCheckDate.equals(sStartDay)){
	    										isUpdate = true;
	    									}
	    								}
	    							}else{
	    								if(strCheckDate.equals(vaccinDayCursor.getString(vaccinDayCursor.getColumnIndex("DAY_VACCIN")))){
		    								isUpdate = true;
		    							}
	    							}
	    						}
	    					}while(vaccinDayCursor.moveToNext());
	    				}
	    			}
	    			if(isUpdate){
						setNotification(context);
	    			}
				}catch(Exception e){
					Log.e(TAG, e.getMessage());
					e.printStackTrace();
				}finally{
					if(vaccinDayCursor != null){
						vaccinDayCursor.close();
					}
		        	dbHelper.close();
				}
    		}
		}
        
     	if (onAlarmListener != null) {
			onAlarmListener.run();
		 }
     }
	
	 /**
     * 
     * @param sMsg
     */
	private void setNotification(Context context){
		try {
//			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//			Notification notification = new Notification(R.drawable.icon, "My Treasure Notification",System.currentTimeMillis());
//			notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//			Intent intentv = new Intent(context, MyTreasure.class);
//			PendingIntent activity = PendingIntent.getActivity(context, 0, intentv,	0);
//			notification.setLatestEventInfo(context, context.getString(R.string.vaccin_info),context.getString(R.string.vaccin_info_confirmation), activity);
//			notification.number += 1;
//			notificationManager.notify(0, notification);
			
			NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, Vaccination.class), PendingIntent.FLAG_UPDATE_CURRENT);
			 
			Notification.Builder mBuilder = new Notification.Builder(context);
			mBuilder.setSmallIcon(R.drawable.icon);
			mBuilder.setTicker(context.getString(R.string.vaccin_info));
			mBuilder.setWhen(System.currentTimeMillis());
			//mBuilder.setNumber(10);
			mBuilder.setContentTitle(context.getString(R.string.vaccin_info));
			mBuilder.setContentText(context.getString(R.string.vaccin_info_confirmation));
			mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
			mBuilder.setContentIntent(pendingIntent);
			mBuilder.setAutoCancel(true);
			 
			mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
			nm.notify(111, mBuilder.build());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
