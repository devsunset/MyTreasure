/*
* @(#)Vaccination.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.nics.mytreasure.R;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;

/**
 * <PRE>
 * My Treasure Vaccination Page
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class Vaccination extends Activity {
	public static final String TAG = "Vaccination";
	
	private MyTreasureDatabaseHelper dbHelper = null;
	
	public static final int GET_CODE =0;
	
	private Spinner mSpBabyNames = null;
	private ListView mLvVaccination = null;
	
	private String[] mSaBabyNames = null;
	private String[] mSaBabyBirths = null;
	private String[] mSaBabyID = null;	
	private int mIBabyIndex = 0;
	
	public List<HashMap<String,String>> mlsVacc = null;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination); 
        
        setInit();
    }

	private void setInit() {
		Cursor cursor = null;
        try{ 
            mSpBabyNames = (Spinner) findViewById(R.id.babyname);
            dbHelper = new MyTreasureDatabaseHelper(this);
        	dbHelper.open();
        	
        	StringBuilder sSql = new StringBuilder();
        	String [] sSelectionArgs = null;
        	sSql.append(" SELECT NAME,BIRTH,_ID FROM T_BABY_MST");
        	cursor = dbHelper.getQueryData(sSql.toString(),sSelectionArgs);
        	
        	if(cursor.moveToFirst() && cursor.getCount() > 0){
        		mSaBabyNames = new String[cursor.getCount()];
        		mSaBabyBirths = new String[cursor.getCount()];
        		mSaBabyID =  new String[cursor.getCount()];
        		if(cursor.moveToFirst()){
        			int i=0;
        			do{
        				if(cursor.getCount() > 0){
    						mSaBabyNames[i] = cursor.getString(0);
    						mSaBabyBirths[i] = cursor.getString(1);
    						mSaBabyID[i] = cursor.getString(2);
    						i++;
        				}
        			}while(cursor.moveToNext());
        		}
    		}else{
    			mSaBabyNames = new String[1];
    			mSaBabyBirths = new String[1];
    			mSaBabyID  = new String[1];
    			mSaBabyNames[0] = getString(R.string.babynotfound);
    	        final Calendar c = Calendar.getInstance();        
    			mSaBabyBirths[0] = c.get(Calendar.YEAR)+"/"+String.format("%02d", (c.get(Calendar.MONTH)+1))+"/"+String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
    			mSaBabyID[0] = "1";
    		}
        	
            @SuppressWarnings({ "unchecked", "rawtypes" })
			ArrayAdapter babyNameAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mSaBabyNames);
            babyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpBabyNames.setAdapter(babyNameAdapter);
            mSpBabyNames.setOnItemSelectedListener(spBabyNamesListener);
            
        }catch(Exception e){
        	Log.e(TAG,e.getMessage());
        }finally{
        	if(cursor !=null){
        		cursor.close();
        	}
        	dbHelper.close();
        }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
	
    /**
     * 아기 선택 처리 
     */
    private Spinner.OnItemSelectedListener spBabyNamesListener = 
    	new Spinner.OnItemSelectedListener(){
		@SuppressWarnings("rawtypes")
		public void onItemSelected(AdapterView parent, View view, int pos, long id) {
			int iBabyIndex = parent.getSelectedItemPosition();
			String strBabyBirth = mSaBabyBirths[iBabyIndex];
			String strBabyID = mSaBabyID[iBabyIndex];
			mIBabyIndex = iBabyIndex;
			setVaccination(strBabyBirth,strBabyID);
		}	
		
		@SuppressWarnings("rawtypes")
		public void onNothingSelected(AdapterView parent) {}
    };
    
    /**
     * 아기에 해당하는 예방접종 정보 출력
     */
    private void setVaccination(String strBabyBirth,String strBabyID){
    		Cursor cursor = null; 
			 try{ 
		            mSpBabyNames = (Spinner) findViewById(R.id.babyname);
		            dbHelper = new MyTreasureDatabaseHelper(this);
		        	dbHelper.open();
		        	
		        	StringBuilder sSql = new StringBuilder();
		        	String [] sSelectionArgs = null;
		        	if(getString(R.string.babynotfound).equals(mSpBabyNames.getSelectedItem().toString())){
		        		sSql.append(" SELECT VACCIN_TYPE,VACCIN_NAME,VACCIN_PERIOD_S,VACCIN_PERIOD_E,VACCIN_DEGREE,VACCIN_DESC,VACCIN_ETC,'N' AS V_FLAG,'' AS DAY_VACCIN FROM T_VACCINATION_MST");
		        	}else{
		        		 sSql.append(" SELECT T_VACCINATION_MST.VACCIN_TYPE,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_NAME,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_PERIOD_S,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_PERIOD_E,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_DEGREE,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_DESC,");
			        	 sSql.append(" T_VACCINATION_MST.VACCIN_ETC,");
			        	 sSql.append(" T_BABY_VACCINATION_MST.V_FLAG,");
			        	 sSql.append(" T_BABY_VACCINATION_MST.DAY_VACCIN");			        	 
			        	 sSql.append(" FROM T_VACCINATION_MST INNER JOIN T_BABY_VACCINATION_MST ");
			        	 sSql.append(" ON T_VACCINATION_MST._ID = T_BABY_VACCINATION_MST.VACCIN_ID");
			        	 sSql.append(" WHERE T_BABY_VACCINATION_MST.BABY_ID = ? ");
			        	 sSql.append(" ORDER BY T_VACCINATION_MST._ID ASC");
			        	 
			        	 sSelectionArgs = new String[1];
			        	 sSelectionArgs[0] = strBabyID;
		        	}
		        	cursor = dbHelper.getQueryData(sSql.toString(),sSelectionArgs);
		        	
		        	 final Calendar c = Calendar.getInstance();        
		             int iYear = c.get(Calendar.YEAR);
		             int iMonth = c.get(Calendar.MONTH);
		             int iDay = c.get(Calendar.DAY_OF_MONTH);
		             String sToday = iYear+"/"+String.format("%02d", iMonth+1)+"/"+String.format("%02d", iDay);
		             String babyExist = "Y";
		             
		             if(getString(R.string.babynotfound).equals(mSpBabyNames.getSelectedItem().toString())){
		            	 babyExist = "N";
 					 }
		             
		             mlsVacc = new ArrayList<HashMap<String,String>>();
		             HashMap<String,String> hVacc = null;
		        	
		        	if(cursor !=null && cursor.moveToFirst() && cursor.getCount() > 0){
		        		if(cursor.moveToFirst()){
		        			do{
		        				if(cursor.getCount() > 0){
		        					hVacc = new HashMap<String,String>();
		        					hVacc.put("TODAY", sToday);
		        					hVacc.put("BIRTHDAY", strBabyBirth);
		        					hVacc.put("BABY_EXIST", babyExist);
		        					hVacc.put("VACCIN_TYPE", cursor.getString(cursor.getColumnIndex("VACCIN_TYPE")));
		        					hVacc.put("VACCIN_NAME", cursor.getString(cursor.getColumnIndex("VACCIN_NAME")));
		        					hVacc.put("VACCIN_PERIOD_S", cursor.getString(cursor.getColumnIndex("VACCIN_PERIOD_S")));
		        					hVacc.put("VACCIN_PERIOD_E", cursor.getString(cursor.getColumnIndex("VACCIN_PERIOD_E")));
		        					hVacc.put("VACCIN_DEGREE", cursor.getString(cursor.getColumnIndex("VACCIN_DEGREE")));
		        					hVacc.put("VACCIN_DESC", cursor.getString(cursor.getColumnIndex("VACCIN_DESC")));
		        					hVacc.put("VACCIN_ETC", cursor.getString(cursor.getColumnIndex("VACCIN_ETC")));
		        					hVacc.put("V_FLAG", cursor.getString(cursor.getColumnIndex("V_FLAG")));
		        					hVacc.put("DAY_VACCIN", cursor.getString(cursor.getColumnIndex("DAY_VACCIN")));
		        					mlsVacc.add(hVacc);
		        				}
		        			}while(cursor.moveToNext());
		        		}
		        		
		        		VaccinationListAdapter myaAdapter = new VaccinationListAdapter(this, 0, mlsVacc);
			    		mLvVaccination = (ListView) findViewById(R.id.vaccination);
			    		mLvVaccination.setAdapter(myaAdapter);
			    		mLvVaccination.setOnItemClickListener(listViewVaccinationListner);
			    		
			             ImageView btListTopButton = (ImageView) this.findViewById(R.id.btListTopButton);
				   		 btListTopButton.bringToFront();
				   		 btListTopButton.setOnClickListener(new View.OnClickListener() {
				   				public void onClick(View v) {
				   					mLvVaccination.setSelection(0);
				   				}
				   	      });
		    		}
			 	}catch(Exception e){
			 		Log.e(TAG,e.getMessage());
		        }finally{
		        	if(cursor !=null){
		        		cursor.close();
		        	}
		        	dbHelper.close();
		        }
    }
    
    
    /**
     * Vaccination 항목 선택 처리
     */
    public ListView.OnItemClickListener listViewVaccinationListner = 
    	new ListView.OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if(!getString(R.string.babynotfound).equals(mSpBabyNames.getSelectedItem().toString())){
				 Intent intent = new Intent(Vaccination.this, VaccinationReg.class);
		         intent.putExtra("BABY_ID", mSaBabyID[mIBabyIndex]);
		         intent.putExtra("VACCIN_ID", (arg2+1)+"");
		         intent.putExtra("BABY_BIRTH", mSaBabyBirths[mIBabyIndex]);
		         intent.putExtra("POSITION", mIBabyIndex+"");
		         startActivityForResult(intent, GET_CODE);
			}
		}
    };
    
    /**
     * 백신 정보 호출 후 리턴 받아 처리
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == GET_CODE) {
            if (resultCode == RESULT_OK) {
            	mSpBabyNames.setSelection(Integer.parseInt(data.getAction()));
            	String strBabyBirth = mSaBabyBirths[Integer.parseInt(data.getAction())];
    			String strBabyID = mSaBabyID[Integer.parseInt(data.getAction())];
    			mIBabyIndex = Integer.parseInt(data.getAction());
    			setVaccination(strBabyBirth,strBabyID);
            }
        }
     }
}
