/*
* @(#)FragmentPregnancyDay.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nics.mytreasure.R;

/**
 * <PRE>
 * My Treasure FragmentPregnancyDay Page
 * </PRE>
 * 
 * @author 황연준
 * @version 1.0
 * @since mytreasure1.0 
 */
public class FragmentPregnancyDay extends Fragment {
	
	public static final String TAG = "FragmentPregnancyDay";

	public static final int DATE_DIALOG_ID = 1;
	
	private View view = null;
	
	private EditText mEtDate = null;
	private TextView mTvOvulationDay = null;
	private TextView mTvPregDay = null;
	private Spinner mSpMensPeriod = null;
	
	private int mIYear = 0;
	private int mIMonth = 0;
	private int mIDay = 0;
    
	private int[] mTotalDay = null;
	private String[] mCurMonth = null;

	 /**
     * 임신 가능일 체크 기본 데이타 setting
     */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pregnancyday, container, false);
		view = v;
		setInit();
		return v;
	}

	private void setInit() {

        final Calendar c = Calendar.getInstance();        
        mIYear = c.get(Calendar.YEAR);
        mIMonth = c.get(Calendar.MONTH);
        mIDay = c.get(Calendar.DAY_OF_MONTH);
        
        
		/**
         *  현재 일 셋팅 및 spinner 데이타 설정 확인버튼 event 처리
         */
        mTvOvulationDay = (TextView) view.findViewById(R.id.tvovulation_day);
        mTvPregDay = (TextView) view.findViewById(R.id.tv_preg_day);
        mEtDate = (EditText) view.findViewById(R.id.etcurdate);
        
        Button btCal = (Button) view.findViewById(R.id.btcal);
        btCal.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(getActivity(), mDateSetListener, c.get(Calendar.YEAR),  c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	     		dialog.show();
			}
		});
        
        mSpMensPeriod = (Spinner) view.findViewById(R.id.spmenus_period);
        ArrayAdapter<?> periodAdapter = ArrayAdapter.createFromResource(getActivity(), 
        		R.array.menus_period_array, 
        		android.R.layout.simple_spinner_item);
        
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpMensPeriod.setAdapter(periodAdapter);
        
        Button btInit = (Button) view.findViewById(R.id.btinit);
        btInit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mEtDate.setText("");
				mSpMensPeriod.setSelection(0);
				mTvOvulationDay.setText("");
				mTvPregDay.setText("");
				
		        mIYear = c.get(Calendar.YEAR);
		        mIMonth = c.get(Calendar.MONTH);
		        mIDay = c.get(Calendar.DAY_OF_MONTH);
			}
		});
        
        Button btCalcul = (Button) view.findViewById(R.id.btcalcul);
        btCalcul.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if("".equals(mEtDate.getText().toString())){
					updateDateDisplay();
				}
				String strOvulationDay = getCalCulDay(0,true);
				String strStartPregDay = getCalCulDay(-5,true);
				String strEndPregDay = getCalCulDay(3,true);
				mTvOvulationDay.setText(new StringBuffer().append(strOvulationDay));
				mTvPregDay.setText(new StringBuffer().append(strStartPregDay)+" ~ "+new StringBuffer().append(strEndPregDay));
			}
		});
	}
	
	/**
	 * 날짜 Dialog 선택시 이벤트
	 */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
    	new DatePickerDialog.OnDateSetListener() {			
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mIYear = year;
			mIMonth = monthOfYear;
			mIDay = dayOfMonth;
			updateDateDisplay();
		}
	};
    
	/**
	 * 날짜 업데이트 및 display
	 */
	private void updateDateDisplay() {
		mEtDate.setText(new StringBuffer().
				append(mIYear).append("/").
				append(String.format("%02d", (mIMonth+1))).append("/").
				append(String.format("%02d", mIDay)));
    }

	/**
	 * 배란일, 임신 가능을  계산
	 * @param iDif 선택 일자
	 * @return 임신 가능 시작일
	 */
	public String getCalCulDay(int iDif,boolean bYearFlag){
		String strDate = "";
		mTotalDay = new int[12];
		mTotalDay[0] = 31;
		mTotalDay[1] = 28;
		mTotalDay[2] = 31;
		mTotalDay[3] = 30;
		mTotalDay[4] = 31;
		mTotalDay[5] = 30;
		mTotalDay[6] = 31;
		mTotalDay[7] = 31;
		mTotalDay[8] = 31;
		mTotalDay[9] = 31;
		mTotalDay[10] = 30;
		mTotalDay[11] = 31;
		
		mCurMonth = new String[12];
		for(int i=0; i<mCurMonth.length; i++){
			mCurMonth[i] = i+1+"";
		}

		int iCurYear = mIYear;
		int iCurMonth = mIMonth;
		int iCurDay = (int) (mIDay + mSpMensPeriod.getSelectedItemId() + 25);
		iCurDay = (iCurDay - 14) + iDif;
		
		int max = 0;
		for(int i=0; i<12; i++){
			if(iCurMonth == 2 && (iCurYear % 4) == 0){
				max = 29;
			}else{
				max = mTotalDay[iCurMonth];
				if(iCurDay > max){
					iCurDay = iCurDay - max;
					iCurMonth = iCurMonth + 1;
					if(iCurMonth >= 12){
						iCurYear = iCurYear + 1;
						iCurMonth = 0;
					}
				}else{
					break;
				}
			}
		}
		
		
		if(bYearFlag){
			strDate = iCurYear+"/"+String.format("%02d", Integer.parseInt(mCurMonth[iCurMonth]))+"/"+String.format("%02d", iCurDay);
		}else{
			strDate = String.format("%02d", Integer.parseInt(mCurMonth[iCurMonth]))+"/"+String.format("%02d", iCurDay);
		}
		return strDate;
	}
	
	public void hideEditTextKeypad(){
		if (getActivity() != null && getActivity().getCurrentFocus() != null) {
			((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
		}
	}
	
}
