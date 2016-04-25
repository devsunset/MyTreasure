/*
* @(#)FragmentTall.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nics.mytreasure.R;
import com.nics.mytreasure.util.MessageHelper;

/**
 * <PRE>
 * My Treasure FragmentTall Page
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class FragmentTall extends Fragment {
	
	public static final String TAG = "FragmentTall";
	
	private View view = null;
	
	private EditText mEtFatherTall = null; 
	private EditText mEtMotherTall = null; 
	private TextView mTvPredictTall = null; 
	private RadioButton mRbSexMan = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tall, container, false);
		view = v;
		setInit();
		return v;
	}

	private void setInit() {
		
    	mEtFatherTall = (EditText)view.findViewById(R.id.etfathertall);
    	mEtMotherTall = (EditText)view.findViewById(R.id.etmothertall);
    	mTvPredictTall = (TextView)view.findViewById(R.id.tvpredicttall);
    	mRbSexMan = (RadioButton) view.findViewById(R.id.rbsexman);
    	
		final Button btInit = (Button) view.findViewById(R.id.btinit);
        btInit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mEtFatherTall.setText("");
		    	mEtMotherTall.setText("");
		    	mTvPredictTall.setText("");
		    	mRbSexMan.setChecked(true);
			}
		});
		
		final Button btEnter = (Button) view.findViewById(R.id.btenter);
        btEnter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

            	String sFatherTall = mEtFatherTall.getText().toString();
            	String sMotherTall = mEtMotherTall.getText().toString();
            	
            	if(sFatherTall !=null && !"".equals(sFatherTall) && sMotherTall !=null && !"".equals(sMotherTall)){
            		/*
        		   	장래의 키를 예측하는 대표적인 방법은 부모의 키를 통해 계산하는 방법이다. 
					이 방법은 수치의 정확도는 비교적 높은 편이지만 논란도 많다. 
					단, 부모의 키를 정확히 계측해서 계산해야 한다. 
					
					남아의 예상 신장〓[아버지 신장(cm)＋13＋어머니 신장(cm)]×0.5
					여아의 예상 신장〓[아버지 신장(cm)―13＋어머니 신장(cm)]×0.5
            		*/
            		double dFatherTall = Double.parseDouble(sFatherTall);
            		double dMotherTall = Double.parseDouble(sMotherTall);
            		
            		if(mRbSexMan.isChecked()){
            			mTvPredictTall.setText(((dFatherTall+13+dMotherTall)*0.5)+" cm");
    				}else{
    					mTvPredictTall.setText(((dFatherTall-13+dMotherTall)*0.5)+" cm");
    				}
            		
            	}else{
            		if(sFatherTall == null || "".equals(sFatherTall)){
            			MessageHelper.alert(getActivity(), getString(R.string.inputfathertall_text));
            		}else{
            			MessageHelper.alert(getActivity(), getString(R.string.inputmothertall_text));
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
