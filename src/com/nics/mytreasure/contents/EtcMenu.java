/*
 * @(#)EtcMenu.java
 * Date : 2010. 03. 01.
 * Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
 */
package com.nics.mytreasure.contents;

import java.util.ArrayList;

import android.app.LauncherActivity.ListItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nics.mytreasure.R;
import com.nics.mytreasure.util.CustomViewPager;

/**
 * <PRE>
 * My Treasure 부가 기능 메뉴 선택 페이지
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */

public class EtcMenu extends FragmentActivity {

	private final int TAB_ANNIVERSARY = 0;
	private final int TAB_OBESITY = 1;
	private final int TAB_TALL = 2;
	private final int TAB_PREGNANCYDAY = 3;

	private CustomViewPager vpEtcmenu = null;
	private RelativeLayout llTab1 = null;
	private RelativeLayout llTab2 = null;
	private RelativeLayout llTab3 = null;
	private RelativeLayout llTab4 = null;
	private View vTab1 = null;
	private View vTab2 = null;
	private View vTab3 = null;
	private View vTab4 = null;
	private ImageView ivTab1 = null;
	private ImageView ivTab2 = null;
	private ImageView ivTab3 = null;
	private ImageView ivTab4 = null;
	private TextView tvTab1 = null;
	private TextView tvTab2 = null;
	private TextView tvTab3 = null;
	private TextView tvTab4 = null;

	private ArrayList<Fragment> list = null;
	private ListItem itemInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etcmenu);

		vpEtcmenu = (CustomViewPager) findViewById(R.id.vp_etcmenu);
		llTab1 = (RelativeLayout) findViewById(R.id.ll_tab1);
		llTab2 = (RelativeLayout) findViewById(R.id.ll_tab2);
		llTab3 = (RelativeLayout) findViewById(R.id.ll_tab3);
		llTab4 = (RelativeLayout) findViewById(R.id.ll_tab4);
		vTab1 = findViewById(R.id.v_line_tab1);
		vTab2 = findViewById(R.id.v_line_tab2);
		vTab3 = findViewById(R.id.v_line_tab3);
		vTab4 = findViewById(R.id.v_line_tab4);
		ivTab1 = (ImageView) findViewById(R.id.iv_tab1);
		ivTab2 = (ImageView) findViewById(R.id.iv_tab2);
		ivTab3 = (ImageView) findViewById(R.id.iv_tab3);
		ivTab4 = (ImageView) findViewById(R.id.iv_tab4);
		tvTab1 = (TextView) findViewById(R.id.tv_tab1);
		tvTab2 = (TextView) findViewById(R.id.tv_tab2);
		tvTab3 = (TextView) findViewById(R.id.tv_tab3);
		tvTab4 = (TextView) findViewById(R.id.tv_tab4);

		initFragments();

		llTab1.setOnClickListener(mOnClickListener);
		llTab2.setOnClickListener(mOnClickListener);
		llTab3.setOnClickListener(mOnClickListener);
		llTab4.setOnClickListener(mOnClickListener);

		itemInfo = new ListItem();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// public methods
	// ------------------------------------------------------------------------------------------------------------------------------
	public void setCurrentItem(int position) {
		setTab(position);
		vpEtcmenu.setCurrentItem(position);
	}

	public void setTab(int position) {
		clearEnable();
		enableLine(position);
	}

	public ListItem getItemInfo() {
		return this.itemInfo;
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// private methods
	// ------------------------------------------------------------------------------------------------------------------------------
	private void initFragments() {
		list = new ArrayList<Fragment>();
		list.add(new FragmentAnniversary());
		list.add(new FragmentObesity());
		list.add(new FragmentTall());
		list.add(new FragmentPregnancyDay());

		vpEtcmenu.setAdapter(new PagerAdapter(getSupportFragmentManager()));
		vpEtcmenu.setOnPageChangeListener(mOnPageChangeListener);
		vpEtcmenu.setPagingEnabled(true);
		vpEtcmenu.setOffscreenPageLimit(4);
		vpEtcmenu.requestDisallowInterceptTouchEvent(true);
		
		enableLine(TAB_ANNIVERSARY);
	}

	private void enableLine(int position) {
		int enable = getResources().getColor(R.color.tab_enable);
		switch (position) {
		case TAB_ANNIVERSARY:
			vTab1.setBackgroundColor(enable);
			ivTab1.setImageResource(R.drawable.tab1_on);
			tvTab1.setTextColor(enable);
			break;
		case TAB_OBESITY:
			vTab2.setBackgroundColor(enable);
			ivTab2.setImageResource(R.drawable.tab2_on);
			tvTab2.setTextColor(enable);
			break;
		case TAB_TALL:
			vTab3.setBackgroundColor(enable);
			ivTab3.setImageResource(R.drawable.tab3_on);
			tvTab3.setTextColor(enable);
			break;
		case TAB_PREGNANCYDAY:
			vTab4.setBackgroundColor(enable);
			ivTab4.setImageResource(R.drawable.tab4_on);
			tvTab4.setTextColor(enable);
			break;
		}
	}

	private void clearEnable() {
		int disableLine = getResources().getColor(R.color.tab_disable);
		int disableText = getResources().getColor(R.color.tab_text_disable);
		vTab1.setBackgroundColor(disableLine);
		vTab2.setBackgroundColor(disableLine);
		vTab3.setBackgroundColor(disableLine);
		vTab4.setBackgroundColor(disableLine);
		ivTab1.setImageResource(R.drawable.tab1);
		ivTab2.setImageResource(R.drawable.tab2);
		ivTab3.setImageResource(R.drawable.tab3);
		ivTab4.setImageResource(R.drawable.tab4);
		tvTab1.setTextColor(disableText);
		tvTab2.setTextColor(disableText);
		tvTab3.setTextColor(disableText);
		tvTab4.setTextColor(disableText);
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Listener
	// ------------------------------------------------------------------------------------------------------------------------------
	View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_tab1:
				setCurrentItem(TAB_ANNIVERSARY);
				break;
			case R.id.ll_tab2:
				setCurrentItem(TAB_OBESITY);
				break;
			case R.id.ll_tab3:
				setCurrentItem(TAB_TALL);
				break;
			case R.id.ll_tab4:
				setCurrentItem(TAB_PREGNANCYDAY);
				break;
			}
		}
	};

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
		int beforePageIndex = TAB_ANNIVERSARY;

		@Override
		public void onPageSelected(int index) {
			setTab(index);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (ViewPager.SCROLL_STATE_IDLE == state) {
				hideEditTextKeypad(beforePageIndex);
				beforePageIndex = vpEtcmenu.getCurrentItem();
			}
		}
	};
	
	protected void hideEditTextKeypad(int index) {
		switch (index) {
		case TAB_ANNIVERSARY:
			((FragmentAnniversary) list.get(TAB_ANNIVERSARY)).hideEditTextKeypad();
			break;
		case TAB_OBESITY:
			((FragmentObesity) list.get(TAB_OBESITY)).hideEditTextKeypad();
			break;
		case TAB_TALL:
			((FragmentTall) list.get(TAB_TALL)).hideEditTextKeypad();
			break;
		case TAB_PREGNANCYDAY:
			((FragmentPregnancyDay) list.get(TAB_PREGNANCYDAY)).hideEditTextKeypad();
			break;
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// inner class
	// ------------------------------------------------------------------------------------------------------------------------------
	private class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			return list.size();
		}
	};
}