/*
* @(#)FragmentAnniversaryListAdapter.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nics.mytreasure.R;

public class FragmentAnniversaryListAdapter extends ArrayAdapter<String> {
		
	private Context mContext;
	private List<String> mList;  
	
	private int mIYear = 0;
	private int mIMonth = 0;
	private int mIDay = 0;

	public FragmentAnniversaryListAdapter(Context context, int resource, List<String> list) {
		super(context, resource, list);
		mList = list;
		mContext = context;
		
		final Calendar c = Calendar.getInstance();        
        mIYear = c.get(Calendar.YEAR);
        mIMonth = c.get(Calendar.MONTH);
        mIDay = c.get(Calendar.DAY_OF_MONTH);
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(mContext, R.layout.anniversary_item_list, null);
			viewHolder.listItemLine	 = (LinearLayout) v.findViewById(R.id.listItemLine);
			viewHolder.ivStar	 = (ImageView) v.findViewById(R.id.ivStar);
			viewHolder.listTextView	 = (TextView) v.findViewById(R.id.listTextView);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		try {
			
			if(position % 2 == 0) {
				viewHolder.listItemLine.setBackgroundColor(mContext.getResources().getColor(R.color.even_color));
			}else{
				viewHolder.listItemLine.setBackgroundColor(mContext.getResources().getColor(R.color.odd_color));
			}
			
			if(mList.get(position).indexOf(getDate()) > -1){
				viewHolder.ivStar.setVisibility(View.VISIBLE);
			}else{
				viewHolder.ivStar.setVisibility(View.GONE);
			}
			
			viewHolder.listTextView.setText(mList.get(position));
			
		}catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}
	
	class ViewHolder {
		LinearLayout listItemLine;
		ImageView ivStar;
		TextView listTextView;	
	}
	
	private String getDate() {
		return (new StringBuffer().
				append(mIYear).append("/").
				append(String.format("%02d", (mIMonth+1))).append("/").
				append(String.format("%02d", mIDay))).toString();
    }

}

