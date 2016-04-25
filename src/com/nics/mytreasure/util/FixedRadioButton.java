/*
* @(#)FixedRadioButton.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * <PRE>
 * My Treasure FixedRadioButton
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
public class FixedRadioButton extends RadioButton {
	final static String TAG = "FixedRadioButton";

	public FixedRadioButton(Context context) {
		super(context);
	}

	public FixedRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FixedRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public int getCompoundPaddingLeft() {
		final float scale = this.getResources().getDisplayMetrics().density;

		if (super.getCompoundPaddingLeft() < 50) {
			return (super.getCompoundPaddingLeft() + (int) (10.0f * scale + 15f));
		}

		return super.getCompoundPaddingLeft();

	}

}
