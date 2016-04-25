/*
* @(#)FragmentObesity.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.math.BigDecimal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nics.mytreasure.R;
import com.nics.mytreasure.util.MessageHelper;

/**
 * <PRE>
 * My Treasure FragmentObesity Page
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class FragmentObesity extends Fragment {

	public static final String TAG = "FragmentObesity";
	
	private View view = null;
	
	private EditText mEtBabyTall = null; 
	private EditText mEtBabyWeight = null; 
	private TextView mTvObesityRate = null; 
	private Spinner mSpMenusObesityResult = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.obesity, container, false);
		view = v;
		setInit();
		return v;
	}

	private void setInit() {
    	mEtBabyTall = (EditText)view.findViewById(R.id.etbabytall);
    	mEtBabyWeight = (EditText)view.findViewById(R.id.etbabyweight);
    	mTvObesityRate = (TextView)view.findViewById(R.id.tvobesityrate);
		
		/**
         * 아기 비만도 결과 view spinner setting
         */
        mSpMenusObesityResult = (Spinner) view.findViewById(R.id.spmenus_obesity_result);
        ArrayAdapter<?> periodAdapter = ArrayAdapter.createFromResource(getActivity(), 
        		R.array.menus_obesity_result_array, 
        		android.R.layout.simple_spinner_item);
        
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpMenusObesityResult.setAdapter(periodAdapter);
        
        final Button btInit = (Button) view.findViewById(R.id.btinit);
        btInit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mEtBabyTall.setText("");
				mEtBabyWeight.setText("");
				mTvObesityRate.setText("");
				mSpMenusObesityResult.setSelection(0);
			}
		});
        
        /**
         * 아기 비만도 체크 로직 처리
         */
        final Button btEnter = (Button) view.findViewById(R.id.btenter);
        btEnter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

            	String sBabyTall = mEtBabyTall.getText().toString();
            	String sBabyWeight = mEtBabyWeight.getText().toString();
            	
            	if(sBabyTall !=null && !"".equals(sBabyTall) && sBabyWeight !=null && !"".equals(sBabyWeight)){
            		//카우프지수 = {체중(g) / 신장(cm) X 신장(cm)} X 10 
            		double dBabyTall = Double.parseDouble(sBabyTall);
            		double dBabyWeight = Double.parseDouble(sBabyWeight);
            		double dObesity = (((dBabyWeight*1000)  / (dBabyTall * dBabyTall)) * 10);
            		/*
            		   	카우프지수 결과
            		  	아기 비만도 결과
						13 미만 영양실조
						13~15 말랐어요
						15~18 정상
						18~20 우량
						20~22 비만
						22 이상 명확한 비만
            		 */
            		if(dObesity < 13){
            			mSpMenusObesityResult.setSelection(1);
            		}else if(dObesity > 13 && dObesity <= 15){
            			mSpMenusObesityResult.setSelection(2);
            		}else if(dObesity > 15 && dObesity <= 18){
            			mSpMenusObesityResult.setSelection(3);
            		}else if(dObesity > 18 && dObesity <= 20){
            			mSpMenusObesityResult.setSelection(4);
            		}else if(dObesity > 20 && dObesity <= 22){
            			mSpMenusObesityResult.setSelection(5);
            		}else if(dObesity > 22){
            			mSpMenusObesityResult.setSelection(6);
            		}
            		mTvObesityRate.setText(new BigDecimal(dObesity).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
            	}else{
            		if(sBabyTall == null || "".equals(sBabyTall)){
            			MessageHelper.alert(getActivity(), getString(R.string.inputtall_text));
            		}else{
            			MessageHelper.alert(getActivity(), getString(R.string.inputweight_text));
            		}
            		
            	}
            }
        });
	}
	
	public void hideEditTextKeypad(){
		if (getActivity() != null && getActivity().getCurrentFocus() != null) {
			((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
		}
	}
}
