/*
 * @(#)MyTreasure.java
 * Date : 2010. 03. 01.
 * Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
 */
package com.nics.mytreasure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.nics.mytreasure.contents.Baby;
import com.nics.mytreasure.contents.EtcMenu;
import com.nics.mytreasure.contents.Setting;
import com.nics.mytreasure.contents.Vaccination;
import com.nics.mytreasure.util.CommonUtil;

/**
 * <PRE>
 * My Treasure Main Page
 * </PRE>
 * 
 * @author ������
 * @version 1.0
 * @since mytreasure1.0
 */
public class MyTreasure extends Activity implements OnClickListener {
	public static final String TAG = "MyTreasure";
	
	public static final String KEY_MYTREASURE_VACCINATION_INIT_PREFERENCE = "my_treasure_vaccination_init";
	
	public boolean bBackKeyPressFlag = false;
	
	/** 
     * Called when the activity is first created. 
     * This is where you should do all of your normal static set up  
     * create views, bind data to lists, and so on.
     * This method is passed a Bundle object containing the activity's previous state,
     * if that state was captured (see Saving Activity State, later). 
     * Always followed by onStart().
     * 
     * Kill able:NO
     * NEXT		:onStart()
     *      
     * Ȱ���� ���� �߷��� �ʱ�ȭ�� ����
     * layout , data binding
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setInit();
	}

	/** 
     * Called just before the activity becomes visible to the user.
     * Followed by onResume() if the activity comes to the foreground, or onStop() 
     * if it becomes hidden.
     * 
     * Kill able:NO
     * NEXT		:onResume() or onStop()
     */
    @Override
	protected void onStart() {
		super.onStart();
	}
    
    /** 
     * Called after the activity has been stopped, just prior to it being started again. 
     * Always followed by onStart()
     * 
     * Kill able:NO
     * NEXT		:onStart()
     */
    @Override
	protected void onRestart() {
		super.onRestart();
	}
    
    /** 
     * Called just before the activity starts interacting with the user. 
     * At this point the activity is at the top of the activity stack, 
     * with user input going to it.
     * Always followed by onPause().
     * 
     * Kill able:NO
     * NEXT		:onPause()
     * 
     * Ȱ�� �ڷ��� �ʱ�ȭ �� �غ� ����
     * Ȱ���� �ʿ��� �ڿ��� �غ� (�����,������,�ִϸ��̼� ���)
     */
    @Override
	protected void onResume() {
		super.onResume();
	}
    
    /** 
     * Called when the system is about to start resuming another activity. 
     * This method is typically used to commit unsaved changes to persistent data,
     * stop animations and other things that may be consuming CPU, and so on.
     * It should do whatever it does very quickly, 
     * because the next activity will not be resumed until it returns. 
     * Followed either by onResume() if the activity returns back to the front, or by onStop()
     * if it becomes invisible to the user.
     * 
     * Kill able:YES
     * NEXT		:onResume() or onStop()
     * 
     * Ȱ�� �ڷ��� ����,����,������ ����
     * �����,������,�ִϸ��̼� ����
     * ����Ÿ ���̽� Ŀ�� ��ü ��Ȱ��
     * Ȱ���� �ʿ����� �ʴ� �ڿ� ����
     * �߿��� �ڷ� ����(���� ���α׷��� �ʼ����� �ڷ�)
     * 
     * onResume()���� ��ȸ�� �ڷ�� �ڿ��� onPause()���� �ݵ�� ����
     * Ȱ���� �ʼ� ������ ���� ���µ� onSaveInstanceState()���� Bundle�� ����
     */
    @Override
	protected void onPause() {
		super.onPause();
	}
    
    /** 
     * Called when the activity is no longer visible to the user.
     * This may happen because it is being destroyed, or because another activity
     * (either an existing one or a new one) has been resumed and is covering it. 
     * Followed either by onRestart() if the activity is coming back to interact with the user,
     * or by onDestroy() if this activity is going away.
     * 
     * Kill able:YES
     * NEXT		:onRestart() or onDestroy
     */
    @Override
	protected void onStop() {
		super.onStop();
	}

    /** 
     * Called before the activity is destroyed. 
     * This is the final call that the activity will receive.
     * It could be called either because the activity is finishing
     * (someone called finish() on it), or because the system is temporarily destroying
     * this instance of the activity to save space.
     * You can distinguish between these two scenarios with the isFinishing() method.
     * 
     * Kill able:YES
     * NEXT		:nothing
     * 
     * Ȱ���� ���� �ڷ� ������ ����
     */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * init()
	 */
	private void setInit() {
		/** vaccination database init */
		SharedPreferences prefSet = getSharedPreferences("SETTING_INFO", MODE_PRIVATE);
		String sVaccinationInit = prefSet.getString(KEY_MYTREASURE_VACCINATION_INIT_PREFERENCE, "N");
		if("N".equals(sVaccinationInit)){
			CommonUtil.createVaccination(this);
		}

		/** Button setOnClickListener. */
		View vMyBaby = this.findViewById(R.id.ivmybaby);
		vMyBaby.setOnClickListener(this);

		View vVaccination = this.findViewById(R.id.ivvaccination);
		vVaccination.setOnClickListener(this);

		View vEtcMenu = this.findViewById(R.id.ivetcmenu);
		vEtcMenu.setOnClickListener(this);

		View vSetting = this.findViewById(R.id.ivsetting);
		vSetting.setOnClickListener(this);
	}

	/**
	 * Main Menu Button click
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivmybaby:
			Intent iMyBaby = new Intent(this, Baby.class);
			startActivity(iMyBaby);
			break;
		case R.id.ivvaccination:
			Intent iVaccination = new Intent(this, Vaccination.class);
			startActivity(iVaccination);
			break;
		case R.id.ivetcmenu:
			Intent iEtcMenu = new Intent(this, EtcMenu.class);
			startActivity(iEtcMenu);
			break;
		case R.id.ivsetting:
			Intent iSetting = new Intent(this, Setting.class);
			startActivity(iSetting);
			break;
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if( keyCode == KeyEvent.KEYCODE_BACK ){
			
				if (!bBackKeyPressFlag) {
					Toast.makeText(MyTreasure.this, getResources().getString(R.string.main_back_finish), Toast.LENGTH_SHORT).show();
					bBackKeyPressFlag = true;
					mKillBackKeyHandler.sendEmptyMessageDelayed(0, 1500);
					return true;
				} else {
					bBackKeyPressFlag = false;
					super.onBackPressed();
				}
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("HandlerLeak")
	Handler mKillBackKeyHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				bBackKeyPressFlag = false;
			}
		}
	};
}