/*
* @(#)VaccinationListAdapter.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nics.mytreasure.R;
import com.nics.mytreasure.util.CommonUtil;

public class VaccinationListAdapter extends ArrayAdapter<HashMap<String,String>> {
		
	private Context mContext;
	private List<HashMap<String,String>> mList;  

	public VaccinationListAdapter(Context context, int resource, List<HashMap<String,String>> list) {
		super(context, resource, list);
		mList = list;
		mContext = context;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = View.inflate(mContext, R.layout.vaccination_item_list, null);
			viewHolder.listItemHeader	 = (LinearLayout) v.findViewById(R.id.listItemHeader);
			viewHolder.ivVacc	 = (ImageView) v.findViewById(R.id.ivVacc);
			viewHolder.listItemTextView	 = (TextView) v.findViewById(R.id.listItemTextView);
			viewHolder.listItemDetailButton	 = (ImageView) v.findViewById(R.id.listItemDetailButton);
			viewHolder.listVaccinationNameTextView	 = (TextView) v.findViewById(R.id.listVaccinationNameTextView);
			viewHolder.listVaccinationGubunTextView	 = (TextView) v.findViewById(R.id.listVaccinationGubunTextView);
			viewHolder.lsVaccination_period	 = (TextView) v.findViewById(R.id.lsVaccination_period);
			viewHolder.listVaccinationPeriodTextView	 = (TextView) v.findViewById(R.id.listVaccinationPeriodTextView);
			viewHolder.listVaccinationStatusTextView	 = (TextView) v.findViewById(R.id.listVaccinationStatusTextView);
			v.setTag(viewHolder);
			
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		try {
			viewHolder.lsVaccination_period.setText("접종 기간");
			
			// 접종 한 경우는 헤더 색상 변경
			if("Y".equals(mList.get(position).get("V_FLAG"))){
				viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.vacc_color));
				viewHolder.ivVacc.setImageResource(R.drawable.point2);  
			}else{
				//미접종인 경우 국가필수예방접종 / 기타예방접종에 따라 색상 변경
				if(mList.get(position).get("VACCIN_TYPE").indexOf("국가")>-1) {
					viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.odd_color));
					viewHolder.ivVacc.setImageResource(R.drawable.point3);  
				}else{
					viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.even_color));
					viewHolder.ivVacc.setImageResource(R.drawable.point1);  
				}
			}
			
			viewHolder.listItemTextView.setText(mList.get(position).get("VACCIN_TYPE"));
			viewHolder.listVaccinationNameTextView.setText(mList.get(position).get("VACCIN_NAME")+" - "+mList.get(position).get("VACCIN_DESC"));
			
			// 기초접종- 추가접종 구분 처리
			if(!"".equals(mList.get(position).get("VACCIN_DEGREE"))){
				if(mList.get(position).get("VACCIN_DEGREE").indexOf("추")>-1) {
					viewHolder.listVaccinationGubunTextView.setText("추가접종 - "+mList.get(position).get("VACCIN_DEGREE"));
				}else{
					viewHolder.listVaccinationGubunTextView.setText("기초접종 - "+mList.get(position).get("VACCIN_DEGREE"));
				}
			}
			
			// 아기 정보 없을 시 기준 정보 출력
			if("N".equals(mList.get(position).get("BABY_EXIST"))){
				viewHolder.listItemDetailButton.setVisibility(View.GONE);
				
				if(!"".equals(mList.get(position).get("VACCIN_PERIOD_S"))){
					String period = mList.get(position).get("VACCIN_PERIOD_S")+"개월";
					if(!"".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
						period +=" ~ "+ mList.get(position).get("VACCIN_PERIOD_E")+"개월";
					}
					viewHolder.listVaccinationPeriodTextView.setText(period);
				}else{
					viewHolder.listVaccinationPeriodTextView.setText(mList.get(position).get("VACCIN_ETC"));
				}
				
				viewHolder.listVaccinationStatusTextView.setText("미접종");
				
			// 아기 정보 존재 시 
			}else{
				viewHolder.listItemDetailButton.setVisibility(View.VISIBLE);
				// 접종 기간이 없는 경우 
				if("".equals(mList.get(position).get("VACCIN_PERIOD_S")) && "".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
					if("N".equals(mList.get(position).get("V_FLAG"))){
						viewHolder.listVaccinationStatusTextView.setText("미접종");
					}else{
						viewHolder.listVaccinationStatusTextView.setText("접종");
					}
					
					if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
						String sDay = mList.get(position).get("DAY_VACCIN");
						
						viewHolder.listVaccinationPeriodTextView.setText(sDay);
						
						if("N".equals(mList.get(position).get("V_FLAG"))){
							if(mList.get(position).get("TODAY").equals(sDay)){
								viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n현재 접종 기간임");
								viewHolder.ivVacc.setImageResource(R.drawable.star);  
								viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
							}else{
								viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
							}
						}
						viewHolder.lsVaccination_period.setText("예약 일자");
					}else{
						viewHolder.listVaccinationPeriodTextView.setText(mList.get(position).get("VACCIN_ETC"));
					}
				// 접종 기간이 존재 할 경우 
				}else{
					// 미접종인 경우
					if("N".equals(mList.get(position).get("V_FLAG"))){
						viewHolder.listVaccinationStatusTextView.setText("미접종");
					// 접종인 경우
					}else{
						viewHolder.listVaccinationStatusTextView.setText("접종");
					}
					
					// 종료 기간이 있는 경우 
					if(!"".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
						String sStartDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_S"),mList.get(position).get("BIRTHDAY"));
						String sEndDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_E"),mList.get(position).get("BIRTHDAY"));
						
						if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
							String sDay = mList.get(position).get("DAY_VACCIN");
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n현재 접종 기간임");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
							viewHolder.lsVaccination_period.setText("예약 일자");
						}else{
							viewHolder.listVaccinationPeriodTextView.setText(sStartDay+" ~ "+sEndDay);
							
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if((Integer.parseInt(sStartDay.replaceAll("/","")) <= Integer.parseInt(mList.get(position).get("TODAY").replaceAll("/","")))  
										&&  (Integer.parseInt(mList.get(position).get("TODAY").replaceAll("/","")) <=Integer.parseInt(sEndDay.replaceAll("/","")))){
									viewHolder.listVaccinationPeriodTextView.setText(sStartDay+" ~ "+sEndDay+"\n현재 접종 기간임");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
						}
						
						if(!"N".equals(mList.get(position).get("VACCIN_ETC"))){
							viewHolder.listVaccinationPeriodTextView.setText(viewHolder.listVaccinationPeriodTextView.getText()+"\n"+mList.get(position).get("VACCIN_ETC"));
						}
						
					// 시작 기간만 있는 경우 	
					}else{
						if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
							String sDay = mList.get(position).get("DAY_VACCIN");
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n현재 접종 기간임");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
							viewHolder.lsVaccination_period.setText("예약 일자");
						}else{
							String sDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_S"),mList.get(position).get("BIRTHDAY"));
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n현재 접종 기간임");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
						}
						
						if(!"N".equals(mList.get(position).get("VACCIN_ETC"))){
							viewHolder.listVaccinationPeriodTextView.setText(viewHolder.listVaccinationPeriodTextView.getText()+"\n"+mList.get(position).get("VACCIN_ETC"));
						}
					}
				}
			}
			
			if("Y".equals(mList.get(position).get("V_FLAG"))){
				viewHolder.lsVaccination_period.setText("접종 일자");
			}
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
		LinearLayout listItemHeader;	
		ImageView ivVacc;
		TextView listItemTextView;	
		ImageView listItemDetailButton;
		TextView listVaccinationNameTextView;
		TextView listVaccinationGubunTextView;
		TextView lsVaccination_period;
		TextView listVaccinationPeriodTextView;
		TextView listVaccinationStatusTextView;
	}
}

