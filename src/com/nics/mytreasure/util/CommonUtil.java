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
 * @author 강경희
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
			alVaccinData.add("A|국가필수예방접종|결핵|BCG(피내용)|0|1|1회|N");
			alVaccinData.add("B|국가필수예방접종|B형간염|HepB(유전자 재조합)|0||1차|N");
			alVaccinData.add("C|기타예방접종|결핵|BCG(경피용)|0|1|1회|N");
			alVaccinData.add("B|국가필수예방접종|B형간염|HepB(유전자 재조합)|1||2차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|DTaP|2||1차|N");
			alVaccinData.add("E|국가필수예방접종|폴리오|IPV(사백신)|2||1차|N");
			alVaccinData.add("F|기타예방접종|B형 헤모필루스/인플루엔자/뇌수막염|Hib|2||1차|N");
			alVaccinData.add("G|기타예방접종|페구균|PCV|2||1차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|DTaP|4||2차|N");
			alVaccinData.add("E|국가필수예방접종|폴리오|IPV(사백신)|4||2차|N");
			alVaccinData.add("F|기타예방접종|B형 헤모필루스/인플루엔자/뇌수막염|Hib|4||2차|N");
			alVaccinData.add("G|기타예방접종|페구균|PCV|4||2차|N");
			alVaccinData.add("B|국가필수예방접종|B형간염|HepB(유전자 재조합)|6||3차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|DTaP|6||3차|N");
			alVaccinData.add("E|국가필수예방접종|폴리오|IPV(사백신)|6||3차|N");
			alVaccinData.add("F|기타예방접종|B형 헤모필루스/인플루엔자/뇌수막염|Hib|6||3차|N");
			alVaccinData.add("G|기타예방접종|페구균|PCV|6||3차|N");
			alVaccinData.add("H|국가필수예방접종|홍역 / 유행성 이하선염 / 풍진|MMR|12|16|1차|N");
			alVaccinData.add("I|국가필수예방접종|수두|Var|12|16|1차|N");
			alVaccinData.add("J|국가필수예방접종|일본뇌염|JEV(사백신)|12|23|1~2차|2차 접종 후 12개월 후");
			alVaccinData.add("K|기타예방접종|일본뇌염|JEV(생백신)|12|24|1차|1차 접종 후 12개월 후");
			alVaccinData.add("F|기타예방접종|B형 헤모필루스/인플루엔자/뇌수막염|Hib|12|16|추4차|N");
			alVaccinData.add("K|기타예방접종|A형간염|HepA|12|30|1차~2차|N");
			alVaccinData.add("G|기타예방접종|페구균|PCV|12|16|추4차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|DTaP|15|19|추4차|N");
			alVaccinData.add("J|국가필수예방접종|일본뇌염|JEV(사백신)|36||3차|N");
			alVaccinData.add("K|기타예방접종|일본뇌염|JEV(생백신)|36||2차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|DTaP|48|84|추5차|N");
			alVaccinData.add("E|국가필수예방접종|폴리오|IPV(사백신)|48|84|추4차|N");
			alVaccinData.add("H|국가필수예방접종|홍역 / 유행성 이하선염 / 풍진|MMR|48|84|2차|N");
			alVaccinData.add("J|국가필수예방접종|일본뇌염|JEV(사백신)|72||추4차|N");
			alVaccinData.add("K|기타예방접종|일본뇌염|JEV(생백신)|72||추3차|N");
			alVaccinData.add("D|국가필수예방접종|디프테리아 파상풍 백일해|Td(성인용)|132|156|추6차|N");
			alVaccinData.add("J|국가필수예방접종|일본뇌염|JEV(사백신)|144||추5차|N");
			alVaccinData.add("L|국가필수예방접종|인플루엔자|Flu||||우선접종권장 대상자");
			alVaccinData.add("M|국가필수예방접종|장티푸스|경구용||||고위험군에 한하여 접종");
			alVaccinData.add("M|국가필수예방접종|장티푸스|주사용||||고위험군에 한하여 접종");
			alVaccinData.add("N|국가필수예방접종|신증후군 출혈열|주사용||||고위험군에 한하여 접종");

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
	 * 태어난 날부터 현재까지의 살아온 일수 구하기 
	 * @param nYear1  	현재 년
	 * @param nMonth1	현재 월
	 * @param nDate1	현재 일
	 * @param nYear2	생일 년
	 * @param nMonth2	생일 월
	 * @param nDate2	생일 일
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
     * 인자 값을 기준으로 미래일자 구하기
     * @param iFuntureMonth 개월 수
     * @param sBirthDay		생일
     * @return				생일로 부터 개월 수의 날짜 값
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
     * 인자 값을 기준으로 미래일자 구하기
     * @param daDate 현재일
     * @param iDays  미래일 수
     * @return 미래일자
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
