/*
* @(#)CommonUtil.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.nics.mytreasure.database.MyTreasureDatabaseHelper;
import com.nics.mytreasure.database.MyTreasureDatabase.TVaccinationMst;

/**
 * <PRE>
 * My Treasure CommonUtil
 * </PRE>
 * 
 * @author ������
 * @version 1.0
 * @since mytreasure1.0
 */
public class CommonUtil {
	
	public static final String TAG = "CommonUtil";
	
	
	public static void createVaccination(Context ctx){
    	boolean isSuccess = false;
    	String KEY_MYTREASURE_VACCINATION_INIT_PREFERENCE = "my_treasure_vaccination_init";
    	MyTreasureDatabaseHelper dbHelper = null;
    	try{
	    	dbHelper = new MyTreasureDatabaseHelper(ctx, TVaccinationMst.TABLE_NAME);
	    	dbHelper.open();
	    	
	    	dbHelper.beginTransaction();
			ArrayList<String> alVaccinData = new ArrayList<String>();
			alVaccinData.add("A|�����ʼ���������|����|BCG(�ǳ���)|0|1|1ȸ|N");
			alVaccinData.add("B|�����ʼ���������|B������|HepB(������ ������)|0||1��|N");
			alVaccinData.add("C|��Ÿ��������|����|BCG(���ǿ�)|0|1|1ȸ|N");
			alVaccinData.add("B|�����ʼ���������|B������|HepB(������ ������)|1||2��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|DTaP|2||1��|N");
			alVaccinData.add("E|�����ʼ���������|������|IPV(����)|2||1��|N");
			alVaccinData.add("F|��Ÿ��������|B�� ����ʷ罺/���÷翣��/��������|Hib|2||1��|N");
			alVaccinData.add("G|��Ÿ��������|�䱸��|PCV|2||1��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|DTaP|4||2��|N");
			alVaccinData.add("E|�����ʼ���������|������|IPV(����)|4||2��|N");
			alVaccinData.add("F|��Ÿ��������|B�� ����ʷ罺/���÷翣��/��������|Hib|4||2��|N");
			alVaccinData.add("G|��Ÿ��������|�䱸��|PCV|4||2��|N");
			alVaccinData.add("B|�����ʼ���������|B������|HepB(������ ������)|6||3��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|DTaP|6||3��|N");
			alVaccinData.add("E|�����ʼ���������|������|IPV(����)|6||3��|N");
			alVaccinData.add("F|��Ÿ��������|B�� ����ʷ罺/���÷翣��/��������|Hib|6||3��|N");
			alVaccinData.add("G|��Ÿ��������|�䱸��|PCV|6||3��|N");
			alVaccinData.add("H|�����ʼ���������|ȫ�� / ���༺ ���ϼ��� / ǳ��|MMR|12|16|1��|N");
			alVaccinData.add("I|�����ʼ���������|����|Var|12|16|1��|N");
			alVaccinData.add("J|�����ʼ���������|�Ϻ�����|JEV(����)|12|23|1~2��|2�� ���� �� 12���� ��");
			alVaccinData.add("K|��Ÿ��������|�Ϻ�����|JEV(�����)|12|24|1��|1�� ���� �� 12���� ��");
			alVaccinData.add("F|��Ÿ��������|B�� ����ʷ罺/���÷翣��/��������|Hib|12|16|��4��|N");
			alVaccinData.add("K|��Ÿ��������|A������|HepA|12|30|1��~2��|N");
			alVaccinData.add("G|��Ÿ��������|�䱸��|PCV|12|16|��4��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|DTaP|15|19|��4��|N");
			alVaccinData.add("J|�����ʼ���������|�Ϻ�����|JEV(����)|36||3��|N");
			alVaccinData.add("K|��Ÿ��������|�Ϻ�����|JEV(�����)|36||2��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|DTaP|48|84|��5��|N");
			alVaccinData.add("E|�����ʼ���������|������|IPV(����)|48|84|��4��|N");
			alVaccinData.add("H|�����ʼ���������|ȫ�� / ���༺ ���ϼ��� / ǳ��|MMR|48|84|2��|N");
			alVaccinData.add("J|�����ʼ���������|�Ϻ�����|JEV(����)|72||��4��|N");
			alVaccinData.add("K|��Ÿ��������|�Ϻ�����|JEV(�����)|72||��3��|N");
			alVaccinData.add("D|�����ʼ���������|�����׸��� �Ļ�ǳ ������|Td(���ο�)|132|156|��6��|N");
			alVaccinData.add("J|�����ʼ���������|�Ϻ�����|JEV(����)|144||��5��|N");
			alVaccinData.add("L|�����ʼ���������|���÷翣��|Flu||||�켱�������� �����");
			alVaccinData.add("M|�����ʼ���������|��ƼǪ��|�汸��||||�����豺�� ���Ͽ� ����");
			alVaccinData.add("M|�����ʼ���������|��ƼǪ��|�ֻ��||||�����豺�� ���Ͽ� ����");
			alVaccinData.add("N|�����ʼ���������|�����ı� ������|�ֻ��||||�����豺�� ���Ͽ� ����");

			ContentValues cvVaccination = null;
			
			for(int i=0;i<alVaccinData.size();i++){
				cvVaccination = new ContentValues();
				String [] asVaccin = alVaccinData.get(i).toString().split("\\|");
				cvVaccination.put(TVaccinationMst.VACCIN_GROUP, asVaccin[0]);
				cvVaccination.put(TVaccinationMst.VACCIN_TYPE, asVaccin[1]);
				cvVaccination.put(TVaccinationMst.VACCIN_NAME, asVaccin[2]);
				cvVaccination.put(TVaccinationMst.VACCIN_DESC, asVaccin[3]);
				cvVaccination.put(TVaccinationMst.VACCIN_PERIOD_S, asVaccin[4]);
				cvVaccination.put(TVaccinationMst.VACCIN_PERIOD_E, asVaccin[5]);
				cvVaccination.put(TVaccinationMst.VACCIN_DEGREE, asVaccin[6]);
				cvVaccination.put(TVaccinationMst.VACCIN_ETC, asVaccin[7]);
				long mlReturn =  dbHelper.createData(cvVaccination);
				
		    	if(mlReturn > 0){
		    		isSuccess = true;
		    	}else{
		    		isSuccess = false;
		    		break;
		    	}
			}
			
	    	if(isSuccess){
	    		@SuppressWarnings("static-access")
				SharedPreferences prefSet = ctx.getSharedPreferences("SETTING_INFO", ctx.MODE_PRIVATE);
	    		SharedPreferences.Editor edit = prefSet.edit();
	    		edit.putString(KEY_MYTREASURE_VACCINATION_INIT_PREFERENCE, "Y");
	    		edit.commit();

	    		dbHelper.setTransactionSuccessful();
	    	}else{
	    	}
    	}catch(Exception e){
    		Log.e(TAG,e.getMessage());
    	}finally{
    		dbHelper.endTransaction();
    		dbHelper.close();
    	}
    }

	
	/**
	 * �¾ ������ ��������� ��ƿ� �ϼ� ���ϱ� 
	 * @param nYear1  	���� ��
	 * @param nMonth1	���� ��
	 * @param nDate1	���� ��
	 * @param nYear2	���� ��
	 * @param nMonth2	���� ��
	 * @param nDate2	���� ��
	 * @return
	 */
	public static int getDateCompare(int nYear1,
			int nMonth1,
			int nDate1,
			int nYear2,
			int nMonth2,
			int nDate2) {
		Calendar cal = Calendar.getInstance();
		int nTotalDate1 = 0, nTotalDate2 = 0, nDiffOfYear = 0, nDiffOfDay = 0;

		if (nYear1 > nYear2) {
			for (int i = nYear2; i < nYear1; i++) {
				cal.set(i, 12, 0);
				nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
			}
			nTotalDate1 += nDiffOfYear;
		} else if (nYear1 < nYear2) {
			for (int i = nYear1; i < nYear2; i++) {
				cal.set(i, 12, 0);
				nDiffOfYear += cal.get(Calendar.DAY_OF_YEAR);
			}
			nTotalDate2 += nDiffOfYear;
		}

		cal.set(nYear1, nMonth1 - 1, nDate1);
		nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
		nTotalDate1 += nDiffOfDay;

		cal.set(nYear2, nMonth2 - 1, nDate2);
		nDiffOfDay = cal.get(Calendar.DAY_OF_YEAR);
		nTotalDate2 += nDiffOfDay;

		return (nTotalDate1 - nTotalDate2)+1;
	}
	
    /**
     * ���� ���� �������� �̷����� ���ϱ�
     * @param iFuntureMonth ���� ��
     * @param sBirthDay		����
     * @return				���Ϸ� ���� ���� ���� ��¥ ��
     */
    @SuppressWarnings({ "static-access" })
	public static String getFutureMonthDay(String iFuntureMonth,String sBirthDay){
    	if(!"".equals(iFuntureMonth) && !"".equals(sBirthDay)){
    		Calendar cal = Calendar.getInstance ( );
    		cal.set ( Integer.parseInt (sBirthDay.substring(0,4) ), Integer.parseInt ( sBirthDay.substring(5,7) ) - 1, Integer.parseInt ( sBirthDay.substring(8,10) ) );
    		cal.add ( cal.MONTH, Integer.parseInt(iFuntureMonth) );
    		return cal.get ( cal.YEAR )+"/"+String.format("%02d", (cal.get ( cal.MONTH ) + 1))+"/"+String.format("%02d", cal.get ( cal.DATE ));
    	}else{
    		return "";
    	}
    }
    
    /**
     * ���� ���� �������� �̷����� ���ϱ�
     * @param daDate ������
     * @param iDays  �̷��� ��
     * @return �̷�����
     */
    public static Date getNextDay(Date daDate, int iDays) {
	    long lTime = daDate.getTime();
	    long lDay = 24*60*60*1000; 
	    
	    for(int i = 0; i < iDays; i++){
	    	lTime += lDay;
	    }
	    return new Date(lTime);
	}
}
