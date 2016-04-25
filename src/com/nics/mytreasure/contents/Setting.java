/*
* @(#)Setting.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.nics.mytreasure.R;
import com.nics.mytreasure.util.AlarmUtil;
import com.nics.mytreasure.util.MessageHelper;

public class Setting extends Activity {
	private static final String KEY_MYTREASURE_ALARMUSE_PREFERENCE = "my_treasure_alarm_use";
	private static final String KEY_MYTREASURE_ALARMDAY_PREFERENCE = "my_treasure_alarm_day";
    
	private RadioButton rbAlarmYes = null;
	private RadioButton rbAlarmNo = null;
	private Spinner mSpMenusAlarm = null;
	private Button bthelpvc = null;
	private Button bthelpetc = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);  
        
        setInit();
    }

	private void setInit() {
		rbAlarmYes = (RadioButton) findViewById(R.id.rbyes);
        rbAlarmNo = (RadioButton) findViewById(R.id.rbno);
        
        final Button btSave = (Button) findViewById(R.id.btsave);
        btSave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	MessageHelper.confirm(v.getContext(), R.string.save_confirm, mHandler);
            }
        });
        
        
        bthelpvc = (Button) findViewById(R.id.bthelpvc);
        bthelpvc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(v.getId() == R.id.bthelpvc){
					MessageHelper.webViewMessageBox(v.getContext(), "file:///android_asset/helpVacc.html", getResources().getString(R.string.help));
				}
			}        	
        });
        
        bthelpetc = (Button) findViewById(R.id.bthelpetc);
        bthelpetc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(v.getId() == R.id.bthelpetc){
					MessageHelper.webViewMessageBox(v.getContext(), "file:///android_asset/helpEtc.html", getResources().getString(R.string.help));
				}
			}        	
        });
        
        mSpMenusAlarm = (Spinner) findViewById(R.id.spmenus_alarm);
        ArrayAdapter<?> alarmAdapter = ArrayAdapter.createFromResource(this, 
        		R.array.menus_alarm_array, 
        		android.R.layout.simple_spinner_item);
        
        alarmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpMenusAlarm.setAdapter(alarmAdapter);
        
        SharedPreferences prefSet = getSharedPreferences("SETTING_INFO", MODE_PRIVATE);
        String sAlarmUse = prefSet.getString(KEY_MYTREASURE_ALARMUSE_PREFERENCE, "N");
        String sAlarmDay = prefSet.getString(KEY_MYTREASURE_ALARMDAY_PREFERENCE, "0");
        
        if("Y".equals(sAlarmUse)){
        	rbAlarmYes.setChecked(true);
        }else{
        	rbAlarmNo.setChecked(true);
        }
        
        mSpMenusAlarm.setSelection(Integer.parseInt(sAlarmDay));
	}
    
    
    @SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageHelper.YES:
				saveSetting();
				break;
			case MessageHelper.NO:
				break;
		 	 default:
	    		 break;
			}
		};
	};
	
	private void saveSetting(){
		 SharedPreferences prefs = getSharedPreferences("SETTING_INFO", MODE_PRIVATE); 
         SharedPreferences.Editor ed = prefs.edit();
         
         if(rbAlarmYes.isChecked()){
        	 ed.putString(KEY_MYTREASURE_ALARMUSE_PREFERENCE, "Y");
        	 AlarmUtil.cancel(getApplicationContext());
        	 AlarmUtil.alram(getApplicationContext());
         }else{
        	 ed.putString(KEY_MYTREASURE_ALARMUSE_PREFERENCE, "N");
        	 AlarmUtil.cancel(getApplicationContext());
         }
         
         ed.putString(KEY_MYTREASURE_ALARMDAY_PREFERENCE, mSpMenusAlarm.getSelectedItemPosition()+"");
         ed.commit(); 
         MessageHelper.alert(this, getString(R.string.save_success));
	}
}
