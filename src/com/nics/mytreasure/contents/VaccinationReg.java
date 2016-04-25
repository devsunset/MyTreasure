/*
* @(#)VaccinationReg.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nics.mytreasure.R;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyVaccinationMst;
import com.nics.mytreasure.database.MyTreasureDatabase.TVaccinationMst;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;
import com.nics.mytreasure.util.CommonUtil;
import com.nics.mytreasure.util.MessageHelper;

public class VaccinationReg extends Activity implements OnClickListener, OnCheckedChangeListener {
	public static final String TAG = "VaccinationReg";
	
	private MyTreasureDatabaseHelper dbHelper = null;
	
	private EditText mEtTitle = null;
	private RadioGroup radio = null;
	private RadioButton mRbVaccinationYes  = null;
	private RadioButton mRbVaccinationNo  = null;
	private EditText mEtMemo  = null;
	private EditText mEtDayVaccin = null;
	private String sBabyID = null;
	private String sVaccinID = null;
	private String sPosition = null;
	private Button btCal = null;
	private TextView vacc_title = null;
	private TextView vacc_title_must=null;
	private Button bthelpvc = null;
	
     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinationreg);    
        
		setInit();
    }

	private void setInit() {
        radio = (RadioGroup)findViewById(R.id.rbVacc);
		radio.setOnCheckedChangeListener(this);
		mEtTitle = (EditText) findViewById(R.id.title);
		mRbVaccinationYes = (RadioButton) findViewById(R.id.rbyes);
		mRbVaccinationNo = (RadioButton) findViewById(R.id.rbno);
		mEtMemo= (EditText) findViewById(R.id.memo);
		mEtDayVaccin= (EditText) findViewById(R.id.etdayvaccin);
		btCal = (Button)findViewById(R.id.btcal);
		btCal.setOnClickListener(this);
		vacc_title = (TextView) findViewById(R.id.vacc_title);
		vacc_title_must = (TextView) findViewById(R.id.vacc_title_must);
		
		Intent intent = getIntent();
        sBabyID = intent.getStringExtra("BABY_ID");
        sVaccinID = intent.getStringExtra("VACCIN_ID");
        sPosition = intent.getStringExtra("POSITION");
        String sBabyBirth = intent.getStringExtra("BABY_BIRTH");
        
        bthelpvc = (Button) findViewById(R.id.bthelpvc);
        bthelpvc.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(v.getId() == R.id.bthelpvc){
					MessageHelper.webViewMessageBox(v.getContext(), "file:///android_asset/helpVacc.html", getResources().getString(R.string.help));
				}
			}        	
        });
        
        Cursor cData = null;
		try{ 
        	dbHelper = new MyTreasureDatabaseHelper(this);
        	dbHelper.open();
        	
        	StringBuilder sSql = new StringBuilder();
        	String [] sSelectionArgs = null;
        		 sSql.append(" SELECT T_VACCINATION_MST.VACCIN_GROUP,");
        		 sSql.append(" T_VACCINATION_MST.VACCIN_TYPE,");
        		 sSql.append(" T_VACCINATION_MST.VACCIN_NAME,");
        		 sSql.append(" T_VACCINATION_MST.VACCIN_DEGREE,");
        		 sSql.append(" T_VACCINATION_MST.VACCIN_DESC,");
        		 sSql.append(" T_VACCINATION_MST.VACCIN_PERIOD_S,");
	        	 sSql.append(" T_VACCINATION_MST.VACCIN_PERIOD_E,");
	        	 sSql.append(" T_VACCINATION_MST.VACCIN_ETC,");
	        	 sSql.append(" T_BABY_VACCINATION_MST.V_FLAG,");
	        	 sSql.append(" T_BABY_VACCINATION_MST.MEMO,");
	        	 sSql.append(" T_BABY_VACCINATION_MST.DAY_VACCIN");
	        	 sSql.append(" FROM T_VACCINATION_MST INNER JOIN T_BABY_VACCINATION_MST ");
	        	 sSql.append(" ON T_VACCINATION_MST._ID = T_BABY_VACCINATION_MST.VACCIN_ID");
	        	 sSql.append(" WHERE T_BABY_VACCINATION_MST.BABY_ID = ? ");
	        	 sSql.append(" AND T_BABY_VACCINATION_MST.VACCIN_ID = ? ");
	        	 
	    	 sSelectionArgs = new String[2];
	    	 sSelectionArgs[0] = sBabyID;
	    	 sSelectionArgs[1] = sVaccinID;
	        	 
        	 cData = dbHelper.getQueryData(sSql.toString(),sSelectionArgs);
    		 
    		 final Calendar c = Calendar.getInstance();        
             int iYear = c.get(Calendar.YEAR);
             int iMonth = c.get(Calendar.MONTH);
             int iDay = c.get(Calendar.DAY_OF_MONTH);
             
             String sToday = iYear+"/"+String.format("%02d", iMonth+1)+"/"+String.format("%02d", iDay);
    		 
    		if(cData !=null && cData.getCount() > 0){
				cData.moveToFirst();
	   				 String sTemp = "";
	   				 if(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_DEGREE)).indexOf("추") > -1){
	   					sTemp ="\n추가접종 -"+cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_DEGREE))+"";
	   				 }else{
	   					 
	   					if(!"".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_DEGREE)))){
	   						sTemp ="\n기초접종 -"+cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_DEGREE))+"";
	   					}
	   				 }
	   				 StringBuilder sbTitle = new StringBuilder();
	   				 sbTitle.append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_TYPE)));
	   				 sbTitle.append(sTemp);
	   				 sbTitle.append("\n[대상전염병]\n");
	   				 sbTitle.append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_NAME)));
	   				 sbTitle.append("\n[백신종류및방법]\n");
	   				 sbTitle.append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_DESC)));
	   				 
	   				 
		   				if("".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_S))) && "".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_E)))){
		   					if(!"N".equals(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN)))){
		   						String sDay = cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN));
								sbTitle.append("\n[접종기간]\n").append(sDay);
								if(sToday.equals(sDay)){
									sbTitle.append("\n현재 접종 기간임");
								}
		   					}
		   					sbTitle.append("\n").append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_ETC)));
						}else{
							if(!"".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_E)))){
								
								if(!"N".equals(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN)))){
			   						String sDay = cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN));
									sbTitle.append("\n[접종기간]\n").append(sDay);
									if(sToday.equals(sDay)){
										sbTitle.append("\n현재 접종 기간임");
									}
			   					}else{
			   						String sStartDay = CommonUtil.getFutureMonthDay(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_S)),sBabyBirth);
									String sEndDay = CommonUtil.getFutureMonthDay(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_E)),sBabyBirth);
									
									sbTitle.append("\n[접종기간]\n").append(sStartDay);
									sbTitle.append(" ~ ").append(sEndDay);
									
									if((Integer.parseInt(sStartDay.replaceAll("/","")) <= Integer.parseInt(sToday.replaceAll("/","")))  &&  (Integer.parseInt(sToday.replaceAll("/","")) <=Integer.parseInt(sEndDay.replaceAll("/","")))){
										sbTitle.append("\n현재 접종 기간임");
									}
			   					}

								if(!"N".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_ETC)))){
									sbTitle.append("\n").append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_ETC)));	
								}
								
							}else{
								if(!"N".equals(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN)))){
			   						String sDay = cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN));
									sbTitle.append("\n[접종기간]\n").append(sDay);
									if(sToday.equals(sDay)){
										sbTitle.append("\n현재 접종 기간임");
									}
			   					}else{
			   						String sDay = CommonUtil.getFutureMonthDay(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_PERIOD_S)),sBabyBirth);
									sbTitle.append("\n[접종기간]\n").append(sDay);
									if(sToday.equals(sDay)){
										sbTitle.append("\n현재 접종 기간임");
									}
			   					}
								
								if(!"N".equals(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_ETC)))){
									sbTitle.append("\n").append(cData.getString(cData.getColumnIndex(TVaccinationMst.VACCIN_ETC)));	
								}
							}
						} 
	   				 
	   				 mEtTitle.setText(sbTitle.toString());
	   				 
	   			     if("N".equals(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.V_FLAG)))){
	   			    	mRbVaccinationNo.setChecked(true);
	   	    			vacc_title.setText(R.string.vaccination_reg_day);
	   	    			vacc_title_must.setVisibility(View.GONE);
	   	    			mEtDayVaccin.setHint(R.string.vaccination_reg_click);
	   			     }else{
	   			    	mRbVaccinationYes.setChecked(true);
		   			 	vacc_title.setText(R.string.vaccination_day);
		    			vacc_title_must.setVisibility(View.VISIBLE);
		    			mEtDayVaccin.setHint(R.string.vaccination_click);
	   			     }
	   			     mEtMemo.setText(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.MEMO)));
	   			     mEtDayVaccin.setText("N".equals(cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN)))?"":cData.getString(cData.getColumnIndex(TBabyVaccinationMst.DAY_VACCIN)));
    		}
    		
       }catch(Exception e){
    	   Log.e(TAG,e.getMessage());
       }finally{
    	    if(cData !=null){
    	    	cData.close();
    	    }
       		dbHelper.close();
       }
		
		/** 
         * 아기  백신 정보 업데이트 처리
         */ 
        final Button btSave = (Button) findViewById(R.id.btsave);
        btSave.setOnClickListener(this);
        
        /**
         * 접종일자 공백 처리 
         */ 
        final Button btDelete = (Button) findViewById(R.id.btdelete);
        btDelete.setOnClickListener(this);
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(radio.getCheckedRadioButtonId() == R.id.rbyes){
			vacc_title.setText(R.string.vaccination_day);
			vacc_title_must.setVisibility(View.VISIBLE);
			mEtDayVaccin.setHint(R.string.vaccination_click);
		}else{
			vacc_title.setText(R.string.vaccination_reg_day);
			vacc_title_must.setVisibility(View.GONE);
			mEtDayVaccin.setHint(R.string.vaccination_reg_click);
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btcal:
			Calendar c = Calendar.getInstance();    
			 DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, c.get(Calendar.YEAR),  c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
     		 dialog.show();
			break;
		case R.id.btsave:
			save();
			break;
		case R.id.btdelete:
			delete();
			break;
		default:
			break;
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mSaveHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageHelper.YES:
				saveConfirm();
				break;
			case MessageHelper.NO:
				break;
			default:
				break;
			}
		};
	};
	
	@SuppressLint("HandlerLeak")
	Handler mDeleteHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageHelper.YES:
				deleteConfirm();
				break;
			case MessageHelper.NO:
				break;
			default:
				break;
			}
		};
	};
	
	@SuppressWarnings("static-access")
	private void save(){
		if (this != null && this.getCurrentFocus() != null) {
			((InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
		}
		
		if(mRbVaccinationYes.isChecked()){
			String sVaccDate = mEtDayVaccin.getText().toString();
	    	if(sVaccDate !=null && !"".equals(sVaccDate)){
	    		MessageHelper.confirm(this, R.string.save_confirm, mSaveHandler);
	    	}else{
	    	    MessageHelper.alert(this, getString(R.string.vaccination_text));
	    	}
		}else{
			MessageHelper.confirm(this, R.string.save_confirm, mSaveHandler);
		}
	}
	
	private void saveConfirm(){
		boolean isSuccess = false;
		ContentValues cvBabyVaccination = new ContentValues();
		if(mRbVaccinationYes.isChecked()){
			cvBabyVaccination.put(TBabyVaccinationMst.V_FLAG,"Y");
		}else{
			cvBabyVaccination.put(TBabyVaccinationMst.V_FLAG,"N");
		}
		cvBabyVaccination.put(TBabyVaccinationMst.MEMO,mEtMemo.getText().toString());
		cvBabyVaccination.put(TBabyVaccinationMst.DAY_VACCIN,"".equals(mEtDayVaccin.getText().toString())?"N":mEtDayVaccin.getText().toString());
		cvBabyVaccination.put(TBabyVaccinationMst.ALARM,"N");

    	try{
	    	dbHelper = new MyTreasureDatabaseHelper(VaccinationReg.this, TBabyVaccinationMst.TABLE_NAME);
	    	dbHelper.open();
	    	
	    	dbHelper.beginTransaction();
	    	
    		String sWhereClause = " BABY_ID = ? AND VACCIN_ID = ? ";
    		String [] saWhereArgs = new String[2];
    		saWhereArgs[0] = sBabyID;
    		saWhereArgs[1] = sVaccinID;
    		
	    	int iResult = dbHelper.updateData(cvBabyVaccination, sWhereClause, saWhereArgs);
	    	
	    	if(iResult > -1){
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
    		Toast.makeText(VaccinationReg.this, getString(R.string.save_success) , Toast.LENGTH_SHORT).show();
    		setResult(RESULT_OK, (new Intent()).setAction(sPosition));
    		finish();
		}else{
			MessageHelper.alert(this, getString(R.string.save_fail));
		}
	}
	
	@SuppressWarnings("static-access")
	private void delete(){
		if (this != null && this.getCurrentFocus() != null) {
			((InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
		}
		MessageHelper.confirm(this, R.string.delete_confirm, mDeleteHandler);
	}
	
	private void deleteConfirm(){
    	boolean isSuccess = false;
		ContentValues cvBabyVaccination = new ContentValues();
		cvBabyVaccination.put(TBabyVaccinationMst.V_FLAG,"N");
		cvBabyVaccination.put(TBabyVaccinationMst.MEMO,"");
		cvBabyVaccination.put(TBabyVaccinationMst.DAY_VACCIN,"N");
		cvBabyVaccination.put(TBabyVaccinationMst.ALARM,"N");

    	try{
	    	dbHelper = new MyTreasureDatabaseHelper(VaccinationReg.this, TBabyVaccinationMst.TABLE_NAME);
	    	dbHelper.open();
	    	
	    	dbHelper.beginTransaction();
	    	
    		String sWhereClause = " BABY_ID = ? AND VACCIN_ID = ? ";
    		String [] saWhereArgs = new String[2];
    		saWhereArgs[0] = sBabyID;
    		saWhereArgs[1] = sVaccinID;
    		
	    	int iResult = dbHelper.updateData(cvBabyVaccination, sWhereClause, saWhereArgs);
	    	
	    	if(iResult > -1){
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
    		Toast.makeText(VaccinationReg.this, getString(R.string.delete_success) , Toast.LENGTH_SHORT).show();
    		setResult(RESULT_OK, (new Intent()).setAction(sPosition));
    		finish();
		}else{
			MessageHelper.alert(this, getString(R.string.save_fail));
		}
	}
    
	/**
	 * 날짜 Dialog 선택시 이벤트
	 */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
    	new DatePickerDialog.OnDateSetListener() {			
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
				mEtDayVaccin.setText(year+"/"+String.format("%02d", (monthOfYear+1))+"/"+String.format("%02d", dayOfMonth));
		}
	};
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}

	
}

