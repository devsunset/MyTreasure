/*
* @(#)FragmentAnniversary.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.nics.mytreasure.R;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyMst;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;
import com.nics.mytreasure.util.CommonUtil;

/**
 * <PRE>
 * My Treasure FragmentAnniversary Page
 * </PRE>
 * 
 * @author  강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class FragmentAnniversary extends Fragment {
	
	public static final String TAG = "FragmentAnniversary";
	
	private View view = null;
	MyTreasureDatabaseHelper dbHelper = null;
	
	private Spinner mSpBabyNames = null;
	private ListView mLvAnniversary = null;
	
	private String[] mSaBabyNames = null;
	private String[] mSaBabyBirths = null;
	private final int[] ANNIVERSARY = {10,20,30,40,50,60,70,80,90,100,
			110,120,130,140,150,160,170,180,190,200,
			210,220,230,240,250,260,270,280,290,300,
			310,320,330,340,350,360,365,370,380,390,400,
			410,420,430,440,450,460,470,480,490,500,
			510,520,530,540,550,560,570,580,590,600,
			610,620,630,640,650,660,670,680,690,700,
			710,720,730,740,750,760,770,780,790,800,
			810,820,830,840,850,860,870,880,890,900,
			910,920,930,940,950,960,970,980,990,1000,
			1095,1100,1200,1300,1400,1500,1600,1700,1800,1900,
			2000,2500,3000,3500,4000,4500,5000};
	private List<String> mSaAnniversary= null;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.anniversary, container, false);
		view = v;
		setInit();
		return v;
	}

	private void setInit() {
		Cursor cursor = null;
        try{
        	mSpBabyNames = (Spinner) view.findViewById(R.id.babyname);
            dbHelper = new MyTreasureDatabaseHelper(getActivity(), TBabyMst.TABLE_NAME);
         	dbHelper.open();
         	String[] saColumns = new String[]{TBabyMst.NAME, TBabyMst.BIRTH};
         	cursor = dbHelper.getAllData(saColumns, TBabyMst.DEFAULT_SORT_ORDER);
         	
         	//startManagingCursor(cursor);
         	
         	if(cursor.moveToFirst() && cursor.getCount() > 0){
         		mSaBabyNames = new String[cursor.getCount()];
         		mSaBabyBirths = new String[cursor.getCount()];
         		if(cursor.moveToFirst()){
         			int i=0;
         			do{
         				if(cursor.getCount() > 0){
     						mSaBabyNames[i] = cursor.getString(0);
     						mSaBabyBirths[i] = cursor.getString(1);
     						i++;
         				}
         			}while(cursor.moveToNext());
         		}
     		}else{
     			mSaBabyNames = new String[1];
     			mSaBabyBirths = new String[1];
     			mSaBabyNames[0] = getString(R.string.babynotfound);
     	        final Calendar c = Calendar.getInstance();        
     			mSaBabyBirths[0] = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
     		}
             ArrayAdapter<Object> babyNameAdapter = new ArrayAdapter<Object>(getActivity(), android.R.layout.simple_spinner_item, mSaBabyNames);
             babyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             mSpBabyNames.setAdapter(babyNameAdapter);
             mSpBabyNames.setOnItemSelectedListener(spBabyNamesListener);
             
    		 Button btListTopButton = (Button) view.findViewById(R.id.btListTopButton);
    		 btListTopButton.bringToFront();
    		 btListTopButton.setOnClickListener(new View.OnClickListener() {
    				public void onClick(View v) {
    					mLvAnniversary = (ListView) view.findViewById(R.id.anniversary_day);
    					mLvAnniversary.setSelection(0);
    				}
    	      });
    		 
        }catch(Exception e){
			Log.e(TAG, e.getMessage());
        }finally{
        	if(cursor !=null){
        		cursor.close();
        	}
        	dbHelper.close();
        }
	}
    
    private Spinner.OnItemSelectedListener spBabyNamesListener = 
    	new Spinner.OnItemSelectedListener(){
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			int iBabyIndex = parent.getSelectedItemPosition();
			String strBabyBirth = mSaBabyBirths[iBabyIndex];
			setAnniversary(iBabyIndex, strBabyBirth);
		}		
		public void onNothingSelected(AdapterView<?> parent) {}
    };
    
	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	public void setAnniversary(int iBabyIndex, String sBabyBirth){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    	
		mSaAnniversary = new ArrayList<String>();
    	for(int i=0; i<ANNIVERSARY.length; i++){
    		mSaAnniversary.add(df.format(CommonUtil.getNextDay(new Date(sBabyBirth), ANNIVERSARY[i]-1)) + " : "+getString(R.string.anniversarydesc1)+" " +  ANNIVERSARY[i] + " "+getString(R.string.anniversarydesc2));
    	}
    	
    	FragmentAnniversaryListAdapter anniversaryAdapter = new FragmentAnniversaryListAdapter(getActivity(), 0, mSaAnniversary);
        mLvAnniversary = (ListView) view.findViewById(R.id.anniversary_day);
        mLvAnniversary.setAdapter(anniversaryAdapter);
    }
	
	public void hideEditTextKeypad(){
		if (getActivity() != null && getActivity().getCurrentFocus() != null) {
			((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
		}
	}
    
}
