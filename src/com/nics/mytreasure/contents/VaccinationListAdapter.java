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
			viewHolder.lsVaccination_period.setText("���� �Ⱓ");
			
			// ���� �� ���� ��� ���� ����
			if("Y".equals(mList.get(position).get("V_FLAG"))){
				viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.vacc_color));
				viewHolder.ivVacc.setImageResource(R.drawable.point2);  
			}else{
				//�������� ��� �����ʼ��������� / ��Ÿ���������� ���� ���� ����
				if(mList.get(position).get("VACCIN_TYPE").indexOf("����")>-1) {
					viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.odd_color));
					viewHolder.ivVacc.setImageResource(R.drawable.point3);  
				}else{
					viewHolder.listItemHeader.setBackgroundColor(mContext.getResources().getColor(R.color.even_color));
					viewHolder.ivVacc.setImageResource(R.drawable.point1);  
				}
			}
			
			viewHolder.listItemTextView.setText(mList.get(position).get("VACCIN_TYPE"));
			viewHolder.listVaccinationNameTextView.setText(mList.get(position).get("VACCIN_NAME")+" - "+mList.get(position).get("VACCIN_DESC"));
			
			// ��������- �߰����� ���� ó��
			if(!"".equals(mList.get(position).get("VACCIN_DEGREE"))){
				if(mList.get(position).get("VACCIN_DEGREE").indexOf("��")>-1) {
					viewHolder.listVaccinationGubunTextView.setText("�߰����� - "+mList.get(position).get("VACCIN_DEGREE"));
				}else{
					viewHolder.listVaccinationGubunTextView.setText("�������� - "+mList.get(position).get("VACCIN_DEGREE"));
				}
			}
			
			// �Ʊ� ���� ���� �� ���� ���� ���
			if("N".equals(mList.get(position).get("BABY_EXIST"))){
				viewHolder.listItemDetailButton.setVisibility(View.GONE);
				
				if(!"".equals(mList.get(position).get("VACCIN_PERIOD_S"))){
					String period = mList.get(position).get("VACCIN_PERIOD_S")+"����";
					if(!"".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
						period +=" ~ "+ mList.get(position).get("VACCIN_PERIOD_E")+"����";
					}
					viewHolder.listVaccinationPeriodTextView.setText(period);
				}else{
					viewHolder.listVaccinationPeriodTextView.setText(mList.get(position).get("VACCIN_ETC"));
				}
				
				viewHolder.listVaccinationStatusTextView.setText("������");
				
			// �Ʊ� ���� ���� �� 
			}else{
				viewHolder.listItemDetailButton.setVisibility(View.VISIBLE);
				// ���� �Ⱓ�� ���� ��� 
				if("".equals(mList.get(position).get("VACCIN_PERIOD_S")) && "".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
					if("N".equals(mList.get(position).get("V_FLAG"))){
						viewHolder.listVaccinationStatusTextView.setText("������");
					}else{
						viewHolder.listVaccinationStatusTextView.setText("����");
					}
					
					if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
						String sDay = mList.get(position).get("DAY_VACCIN");
						
						viewHolder.listVaccinationPeriodTextView.setText(sDay);
						
						if("N".equals(mList.get(position).get("V_FLAG"))){
							if(mList.get(position).get("TODAY").equals(sDay)){
								viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n���� ���� �Ⱓ��");
								viewHolder.ivVacc.setImageResource(R.drawable.star);  
								viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
							}else{
								viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
							}
						}
						viewHolder.lsVaccination_period.setText("���� ����");
					}else{
						viewHolder.listVaccinationPeriodTextView.setText(mList.get(position).get("VACCIN_ETC"));
					}
				// ���� �Ⱓ�� ���� �� ��� 
				}else{
					// �������� ���
					if("N".equals(mList.get(position).get("V_FLAG"))){
						viewHolder.listVaccinationStatusTextView.setText("������");
					// ������ ���
					}else{
						viewHolder.listVaccinationStatusTextView.setText("����");
					}
					
					// ���� �Ⱓ�� �ִ� ��� 
					if(!"".equals(mList.get(position).get("VACCIN_PERIOD_E"))){
						String sStartDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_S"),mList.get(position).get("BIRTHDAY"));
						String sEndDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_E"),mList.get(position).get("BIRTHDAY"));
						
						if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
							String sDay = mList.get(position).get("DAY_VACCIN");
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n���� ���� �Ⱓ��");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
							viewHolder.lsVaccination_period.setText("���� ����");
						}else{
							viewHolder.listVaccinationPeriodTextView.setText(sStartDay+" ~ "+sEndDay);
							
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if((Integer.parseInt(sStartDay.replaceAll("/","")) <= Integer.parseInt(mList.get(position).get("TODAY").replaceAll("/","")))  
										&&  (Integer.parseInt(mList.get(position).get("TODAY").replaceAll("/","")) <=Integer.parseInt(sEndDay.replaceAll("/","")))){
									viewHolder.listVaccinationPeriodTextView.setText(sStartDay+" ~ "+sEndDay+"\n���� ���� �Ⱓ��");
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
						
					// ���� �Ⱓ�� �ִ� ��� 	
					}else{
						if(!"N".equals(mList.get(position).get("DAY_VACCIN"))){
							String sDay = mList.get(position).get("DAY_VACCIN");
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n���� ���� �Ⱓ��");
									viewHolder.ivVacc.setImageResource(R.drawable.star);  
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.red));
								}else{
									viewHolder.listVaccinationPeriodTextView.setTextColor(mContext.getResources().getColor(R.color.black));
								}
							}
							viewHolder.lsVaccination_period.setText("���� ����");
						}else{
							String sDay = CommonUtil.getFutureMonthDay(mList.get(position).get("VACCIN_PERIOD_S"),mList.get(position).get("BIRTHDAY"));
							viewHolder.listVaccinationPeriodTextView.setText(sDay);
							if("N".equals(mList.get(position).get("V_FLAG"))){
								if(mList.get(position).get("TODAY").equals(sDay)){
									viewHolder.listVaccinationPeriodTextView.setText(sDay+"\n���� ���� �Ⱓ��");
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
				viewHolder.lsVaccination_period.setText("���� ����");
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

