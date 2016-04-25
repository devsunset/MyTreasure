/*
* @(#)StartReceiver.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * <PRE>
 * My Treasure StartReceiver
 * </PRE>
 * 
 * @author ∞≠∞Ê»Ò
 * @version 1.0
 * @since mytreasure1.0
 */
public class StartReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("android.intent.action.BOOT_COMPLETED")) {
			try {
				AlarmUtil.cancel(context);
				AlarmUtil.alram(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}