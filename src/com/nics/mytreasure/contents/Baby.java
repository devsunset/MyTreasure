/*
* @(#)Baby.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nics.mytreasure.R;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyMst;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyVaccinationMst;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;
import com.nics.mytreasure.util.CommonUtil;
import com.nics.mytreasure.util.MessageHelper;


/**
 * <PRE>
 * My Treasure Baby Page
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class Baby extends Activity {
	public static final String TAG = "Baby";
	public static final int GET_CODE =0;
	
	private MyTreasureDatabaseHelper dbHelper = null;
	private Spinner mSpBabyName = null;
	private TextView mTvSex = null;
	private TextView mTvBirthDay = null;
	private TextView mTvBlood = null;
	private TextView mTvConstellation = null;
	private TextView mTvBirthStones = null;
	private TextView mTvAgeTitle = null;
	private TextView mTvDayAlarm= null;
	
	private String[] mSaBabyNames = null;
	
	Cursor cBabyData = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby);     
        setInit("Y");
    }
    
    @Override
	protected void onRestart() {
		super.onRestart();

		if(mSpBabyName != null){
        	if(mSpBabyName.getSelectedItemId() < 0){
        		 this.finish();
        	}
        }
	}
	
	/**
	 * Baby Page Init
	 * @param sFlag 삭제 처리 구분 값
	 */
    @SuppressWarnings("unchecked")
	private void setInit(String sFlag){
    	mTvSex = (TextView) findViewById(R.id.tvsex);
        mTvBirthDay = (TextView) findViewById(R.id.tvbirthday);
        mTvBlood = (TextView) findViewById(R.id.tvblood);
        mTvConstellation = (TextView) findViewById(R.id.tvconstellation);
        mTvBirthStones = (TextView) findViewById(R.id.tvbirthstones);
        mTvAgeTitle = (TextView) findViewById(R.id.tvagetitle);
        mTvDayAlarm = (TextView) findViewById(R.id.tvdayalarm);
        
        /**
         * 아기 추가 - 아기 등록 화면으로 전환
         */
        final Button btAdd = (Button) findViewById(R.id.btadd);
        btAdd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              Intent intent = new Intent(Baby.this, BabyReg.class);
              intent.putExtra("BABY_ID", "baby_id");
              startActivityForResult(intent, GET_CODE);
            }
        });
        
        /**
         * 아기 삭제- 삭제처리
         */
        final Button btDelete = (Button) findViewById(R.id.btdelete);
        btDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	if(mSpBabyName != null && mSpBabyName.getSelectedItemId() > 0){
            		MessageHelper.confirm(v.getContext(), R.string.delete_confirm, mHandler);
                }else{
                	MessageHelper.alert(v.getContext(), getString(R.string.delete_no_baby));
                }
            }
        });
        
        /**
         * Spinner 아기 정보 설정
         */
        try{ 
        	 dbHelper = new MyTreasureDatabaseHelper(this, TBabyMst.TABLE_NAME);
             dbHelper.open();
             String [] saColumns = {TBabyMst.NAME,TBabyMst._ID};
             cBabyData = dbHelper.getAllData(saColumns,TBabyMst.DEFAULT_SORT_ORDER);
     		 //startManagingCursor(cBabyData);
     		 
     		mSpBabyName = (Spinner) findViewById(R.id.spmenus_baby_name);
     		if(cBabyData !=null && cBabyData.getCount() > 0){
    			@SuppressWarnings("deprecation")
				SimpleCursorAdapter babyNameAdapter = new SimpleCursorAdapter(this, 
    					android.R.layout.simple_spinner_item, 
    					cBabyData,
    					new String[] {TBabyMst.NAME}, 		
    					new int[] {android.R.id.text1}); 
    			
    			babyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    			mSpBabyName.setAdapter(babyNameAdapter);
    			mSpBabyName.setOnItemSelectedListener(spListener);
     		}else{
     			mSaBabyNames = new String[1];
    			mSaBabyNames[0] = getString(R.string.babynotfound);
    			@SuppressWarnings("rawtypes")
				ArrayAdapter babyNameAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mSaBabyNames);
    	        babyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	        mSpBabyName.setAdapter(babyNameAdapter);
    	        
				 mTvSex.setText("");
			     mTvBirthDay.setText("");
			     mTvBlood.setText("");
			     mTvConstellation.setText("");
			     mTvBirthStones.setText("");
			     mTvAgeTitle.setText("");
			     //mTvDayAlarm.setText(getString(R.string.babybirthperiod)+" 0"+getString(R.string.day));
			     mTvDayAlarm.setText("");
     		}
        }catch(Exception e){
        	Log.e(TAG,e.getMessage());
        }finally{
        	dbHelper.close();
        }
    }
    
    @SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageHelper.YES:
				deleteBaby();
				break;
			case MessageHelper.NO:
				break;
		 	 default:
	    		 break;
			}
		};
	};
    
    public void deleteBaby(){
    	if(mSpBabyName != null){
        	if(mSpBabyName.getSelectedItemId() > 0){
				boolean isSuccess = false;
				boolean isSuccessSub = false;
				ContentValues cvBaby = new ContentValues();
            	cvBaby.put(TBabyMst._ID, mSpBabyName.getSelectedItemId());

            	try{
        	    	dbHelper = new MyTreasureDatabaseHelper(Baby.this, TBabyMst.TABLE_NAME);
        	    	dbHelper.open();
        	    	
        	    	dbHelper.beginTransaction();
        	    	isSuccess = dbHelper.deleteData(mSpBabyName.getSelectedItemId());
        	    	

        	    	if(isSuccess){
        	    		String sWhereClause = " BABY_ID = ?";
        	    		String [] saWhereArgs = new String[1];
        	    		saWhereArgs[0] = mSpBabyName.getSelectedItemId()+"";
        	    		
        	    		int iResult = dbHelper.deleteData(sWhereClause, saWhereArgs,TBabyVaccinationMst.TABLE_NAME);
        	    		if(iResult > -1){
        	    			isSuccessSub = true;
        	    		}
        	    	}

        	    	if(isSuccess && isSuccessSub){
        	    		dbHelper.setTransactionSuccessful();
        	    		isSuccess = true;
        	    	}
        	    	
            	}catch(Exception e){
            		isSuccess = false;
            		Log.e(TAG,e.getMessage());
            	}finally{
            		dbHelper.endTransaction();
            		dbHelper.close();
            	}
            	
        		if(isSuccess){
        			MessageHelper.alert(this, getString(R.string.save_success));
            		setInit("N");
        		}else{
        			MessageHelper.alert(this, getString(R.string.save_fail));
        		}
        	}
        }
    }
	
    /**
     * 아기 정보 저장 후 출력 처리 
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   if (requestCode == GET_CODE) {
           if (resultCode == RESULT_OK){ 
        	   setInit("Y");
        	   Cursor cData = null;
        	   try{ 
        		   dbHelper = new MyTreasureDatabaseHelper(this, TBabyMst.TABLE_NAME);
                   dbHelper.open();
        		   String [] saColumns = {TBabyMst.NAME,TBabyMst._ID};
                   cData = dbHelper.getAllData(saColumns,TBabyMst.DEFAULT_SORT_ORDER);
                   //startManagingCursor(cData);
		     		 
		     		if(cData !=null && cData.getCount() > 0){
		 				cData.moveToFirst();
		    			for (int i = 0; i < cData.getCount(); i++) {
		    				if(cData.getString(cData.getColumnIndex(TBabyMst._ID)).equals(data.getAction())){
		    					mSpBabyName.setSelection(i);
		    					break;
		    				}
		    				cData.moveToNext();
		    			}
		     		}
		        }catch(Exception e){
		        	Log.e(TAG,e.getMessage());
		        }finally{
		        	if(cData !=null){
		        		cData.close();
		        	}
		        	dbHelper.close();
		        }
           }
	   	}
	}
	
	/**
	 * BabyName Spinner OnItemClickListener
	 */
    OnItemSelectedListener spListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			  Cursor cData = null;
			
			  try{ 
		        	 dbHelper = new MyTreasureDatabaseHelper(Baby.this, TBabyMst.TABLE_NAME);
		             dbHelper.open();
		             String [] saColumns = {TBabyMst.SEX,TBabyMst.BIRTH,TBabyMst.BLOOD,TBabyMst.CONSTELLATION,TBabyMst.BIRTHSTONES,TBabyMst.AGETITLE};
		             cData = dbHelper.getData(mSpBabyName.getSelectedItemId(), saColumns);
		             
		     		 //startManagingCursor(cData);
		     		 
		     		if(cData !=null && cData.getCount() > 0){
		 				cData.moveToFirst();
		    			for (int i = 0; i < cData.getCount(); i++) {
		    				 mTvSex.setText(cData.getString(cData.getColumnIndex(TBabyMst.SEX)));
		    			     mTvBirthDay.setText(cData.getString(cData.getColumnIndex(TBabyMst.BIRTH)));
		    			     mTvBlood.setText(cData.getString(cData.getColumnIndex(TBabyMst.BLOOD)));
		    			     mTvConstellation.setText(cData.getString(cData.getColumnIndex(TBabyMst.CONSTELLATION)));
		    			     mTvBirthStones.setText(cData.getString(cData.getColumnIndex(TBabyMst.BIRTHSTONES)));
		    			     mTvAgeTitle.setText(cData.getString(cData.getColumnIndex(TBabyMst.AGETITLE)));
		    			     
		    			     	String sBirth = cData.getString(cData.getColumnIndex(TBabyMst.BIRTH));
		    					int iYear =Integer.parseInt(sBirth.substring(0,4));
		    					int iMonth = Integer.parseInt(sBirth.substring(5,7));
		    					int iDate = Integer.parseInt(sBirth.substring(8,10));   	
		    					
		    			    	int mIYear = 0;
		    			    	int mIMonth = 0;
		    			    	int mIDay = 0;
		    			        final Calendar c = Calendar.getInstance();        
		    			        mIYear = c.get(Calendar.YEAR);
		    			        mIMonth = c.get(Calendar.MONTH);
		    			        mIDay = c.get(Calendar.DAY_OF_MONTH);
		    					
		    			     mTvDayAlarm.setText(getString(R.string.babybirthperiod)+" "+CommonUtil.getDateCompare(mIYear,mIMonth+1,mIDay,iYear,iMonth,iDate)+getString(R.string.day));
		    			     
		    				cData.moveToNext();
		    			}
		     		}
		        }catch(Exception e){
		        	Log.e(TAG,e.getMessage());
		        }finally{
		        	if(cData !=null){
		        		cData.close();
		        	}
		        	dbHelper.close();
		        }
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
    };
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(cBabyData !=null){
    		cBabyData.close();
    	}
	}
}
