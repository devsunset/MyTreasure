/*
* @(#)BabyReg.java
* Date : 2010. 03. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.contents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.nics.mytreasure.R;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyMst;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyVaccinationMst;
import com.nics.mytreasure.database.MyTreasureDatabaseHelper;
import com.nics.mytreasure.util.CommonUtil;
import com.nics.mytreasure.util.MessageHelper;

/**
 * <PRE>
 * My Treasure BabyReg Page
 * </PRE>
 * 
 * @author ������
 * @version 1.0
 * @since mytreasure1.0
 */
public class BabyReg extends Activity implements OnClickListener {
	public static final String TAG = "BabyReg";
	
	public static final int DATE_DIALOG_ID = 1;
	
	private EditText mEtBabyName = null;
	private RadioButton rbSexMan = null;
	private EditText mEtBirthDay = null;
	private Spinner mSpMenusBlood = null;
	private Button btSave = null;
	private Button btCancel = null;
	private Button btCal = null;
	private long mlReturn = 0;
	
	private MyTreasureDatabaseHelper dbHelper = null;
	
	public static final HashMap<String, String> MAGETITLEMAP = new HashMap<String, String>();
	public static final HashMap<String, String> MBIRTHSTONESHMAP = new HashMap<String, String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babyreg);
        
        setInit();
    }

	private void setInit() {
		/*
		 ��
		 	  birthyear = �¾ �⵵;
			  x = birthyear % 12;
			  byc[x];
		 */
        MAGETITLEMAP.put("1","��");
        MAGETITLEMAP.put("2","��");
        MAGETITLEMAP.put("3","��");
        MAGETITLEMAP.put("4","�䳢");
        MAGETITLEMAP.put("5","��");
        MAGETITLEMAP.put("6","��");
        MAGETITLEMAP.put("7","��");
        MAGETITLEMAP.put("8","��");
        MAGETITLEMAP.put("9","������");
        MAGETITLEMAP.put("10","��");
        MAGETITLEMAP.put("11","��");
        MAGETITLEMAP.put("0","����");

        /*
			ź����
    		01�� ����
    		02�� �ڼ���
    		03�� ���⸶��
    		04�� ���̾Ƹ��
    		05�� ���޶���
    		06�� ����
    		07�� ���
    		08�� �丮��Ʈ
    		09�� �����̾�
    		10�� ����
    		11�� ������
    		12�� ��Ű��
		*/
        MBIRTHSTONESHMAP.put("01", "����");
        MBIRTHSTONESHMAP.put("02", "�ڼ���");
        MBIRTHSTONESHMAP.put("03", "���⸶��");
        MBIRTHSTONESHMAP.put("04", "���̾Ƹ��");
        MBIRTHSTONESHMAP.put("05", "���޶���");
        MBIRTHSTONESHMAP.put("06", "����");
        MBIRTHSTONESHMAP.put("07", "���");
        MBIRTHSTONESHMAP.put("08", "�丮��Ʈ");
        MBIRTHSTONESHMAP.put("09", "�����̾�");
    	MBIRTHSTONESHMAP.put("10", "����");
    	MBIRTHSTONESHMAP.put("11", "������");
    	MBIRTHSTONESHMAP.put("12","��Ű��");
    	
        /**
         * ���� - �Ʊ� ���� ���� ó��
         */
        btSave = (Button)findViewById(R.id.btsave);
        btSave.setOnClickListener(this);
        
        btCancel = (Button)findViewById(R.id.btcancel);
        btCancel.setOnClickListener(this);
        
    	mEtBabyName = (EditText)findViewById(R.id.etbabyname);
    	rbSexMan = (RadioButton)findViewById(R.id.rbsexman);
    	mEtBirthDay = (EditText)findViewById(R.id.etbirthday);
    	mSpMenusBlood = (Spinner)findViewById(R.id.spmenus_blood_name);
    	btCal = (Button)findViewById(R.id.btcal);
		btCal.setOnClickListener(this);
		
        /**
         * ������  ����
         */
        mSpMenusBlood = (Spinner) findViewById(R.id.spmenus_blood_name);
        ArrayAdapter<?> alarmAdapter = ArrayAdapter.createFromResource(this,R.array.menus_baby_array,android.R.layout.simple_spinner_item);
        alarmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpMenusBlood.setAdapter(alarmAdapter);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btcal:
			Calendar c = Calendar.getInstance();    
			 DatePickerDialog dialog = new DatePickerDialog(this, mDateSetListener, c.get(Calendar.YEAR),  c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
     		 dialog.show();
			break;
		case R.id.btsave:
			save();
			break;
		case R.id.btcancel:
			setResult(RESULT_OK, (new Intent()).setAction(mlReturn+""));
    		finish();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ��¥ Dialog ���ý� �̺�Ʈ
	 */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
    	new DatePickerDialog.OnDateSetListener() {			
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
				mEtBirthDay.setText(year+"/"+String.format("%02d", (monthOfYear+1))+"/"+String.format("%02d", dayOfMonth));
		}
	};
	
	private void save(){
    	String sBabyName = mEtBabyName.getText().toString();
    	String sBirthDay = mEtBirthDay.getText().toString();
    	
    	if(sBabyName !=null && !"".equals(sBabyName) && sBirthDay !=null && !"".equals(sBirthDay)){
    		MessageHelper.confirm(this, R.string.save_confirm, mHandler);
    	}else{
    		if(sBabyName == null || "".equals(sBabyName)){
    			MessageHelper.alert(this, getString(R.string.inputbabyname_text));
    		}else{
    			MessageHelper.alert(this, getString(R.string.inputbirthday_text));
    		}
    	}
	}
	
	private void saveConfirm(){
    	String sBabyName = mEtBabyName.getText().toString();
    	String sSex = rbSexMan.isChecked() ? "����":"����";
    	String sBirthDay = mEtBirthDay.getText().toString();
    	String sBlood = mSpMenusBlood.getSelectedItem().toString();
    	
		/*
			���ڸ�
			�����ڸ� 		01/20~02/18
			������ڸ� 		02/19~03/20
			���ڸ� 		03/21~04/19
			Ȳ���ڸ� 		04/20~05/20
			�ֵ����ڸ� 		05/21~06/21
			���ڸ� 		06/22~07/22
			�����ڸ� 		07/23~08/22
			ó���ڸ� 		08/23~09/23
			õĪ�ڸ� 		09/24~10/22
			�����ڸ� 		10/23~11/22
			����ڸ� 		11/23~12/24
			�����ڸ� 		12/25~01/19
		 */
		String sConstellation ="";
		int iPeriod =Integer.parseInt(sBirthDay.substring(5, sBirthDay.length()).replaceAll("/", ""));
		
		if((120 <= iPeriod) && (iPeriod <= 218)){
			sConstellation = "����";
		}else if((219 <= iPeriod) && (iPeriod <= 320)){
			sConstellation = "�����";
		}else if((321 <= iPeriod) && (iPeriod <= 419)){
			sConstellation = "���ڸ�"; 
		}else if((420 <= iPeriod) && (iPeriod <= 520)){
			sConstellation = "Ȳ��";
		}else if((521 <= iPeriod) && (iPeriod <= 621)){
			sConstellation = "�ֵ���";
		}else if((622 <= iPeriod) && (iPeriod <= 722)){
			sConstellation = "��";
		}else if((723 <= iPeriod) && (iPeriod <= 822)){
			sConstellation = "����";
		}else if((823 <= iPeriod) && (iPeriod <= 923)){
			sConstellation = "ó��";
		}else if((924 <= iPeriod) && (iPeriod <= 1022)){
			sConstellation = "õĪ";
		}else if((1023 <= iPeriod) && (iPeriod <= 1122)){
			sConstellation = "����";
		}else if((1123 <= iPeriod) && (iPeriod <= 1224)){
			sConstellation = "���";
		}else if((1225 <= iPeriod) || (iPeriod <= 119)){
			sConstellation = "����";
		}
		
		String sBirthMonth = sBirthDay.substring(5, 7);
		
    	ContentValues cvBaby = new ContentValues();
    	cvBaby.put(TBabyMst.NAME, sBabyName);
    	cvBaby.put(TBabyMst.SEX, sSex);
    	cvBaby.put(TBabyMst.BIRTH, sBirthDay);
    	cvBaby.put(TBabyMst.BLOOD, sBlood);
    	cvBaby.put(TBabyMst.CONSTELLATION, sConstellation);
    	cvBaby.put(TBabyMst.BIRTHSTONES, MBIRTHSTONESHMAP.get(sBirthMonth));
    	cvBaby.put(TBabyMst.AGETITLE, MAGETITLEMAP.get(Integer.toString(((Integer.parseInt(sBirthDay.substring(0, 4))+9)) % 12)));
    	cvBaby.put(TBabyMst.PICTURE, "");
    	
		boolean isSuccess = createBaby(cvBaby);
		if(isSuccess){
			Toast.makeText(BabyReg.this, getString(R.string.save_success) , Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, (new Intent()).setAction(mlReturn+""));
    		finish();
		}else{
			MessageHelper.alert(this, getString(R.string.save_fail));
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MessageHelper.YES:
				saveConfirm();
				break;
			case MessageHelper.NO:
				break;
			default:
				break;
			}
		};
	};
    
	private boolean createBaby(ContentValues cvBaby){
    	boolean isSuccess = false;
    	boolean isBabySuccess = false;
    	boolean isBabyVaccinSuccess = false;
    	try{
	    	dbHelper = new MyTreasureDatabaseHelper(this, TBabyMst.TABLE_NAME);
	    	dbHelper.open();
	    	
	    	dbHelper.beginTransaction();
	    	mlReturn = dbHelper.createData(cvBaby);
	    	if(mlReturn > 0){
	    		isBabySuccess = true;
	    	}

	    	if(isBabySuccess){
	    		ArrayList<String> alVaccinData = new ArrayList<String>();
				alVaccinData.add(mlReturn+"|1|N||N|"+CommonUtil.getFutureMonthDay("0", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|2|N||N|"+CommonUtil.getFutureMonthDay("0", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|3|N||N|"+CommonUtil.getFutureMonthDay("0", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|4|N||N|"+CommonUtil.getFutureMonthDay("1", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|5|N||N|"+CommonUtil.getFutureMonthDay("2", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|6|N||N|"+CommonUtil.getFutureMonthDay("2", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|7|N||N|"+CommonUtil.getFutureMonthDay("2", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|8|N||N|"+CommonUtil.getFutureMonthDay("2", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|9|N||N|"+CommonUtil.getFutureMonthDay("4", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|10|N||N|"+CommonUtil.getFutureMonthDay("4", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|11|N||N|"+CommonUtil.getFutureMonthDay("4", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|12|N||N|"+CommonUtil.getFutureMonthDay("4", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|13|N||N|"+CommonUtil.getFutureMonthDay("6", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|14|N||N|"+CommonUtil.getFutureMonthDay("6", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|15|N||N|"+CommonUtil.getFutureMonthDay("6", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|16|N||N|"+CommonUtil.getFutureMonthDay("6", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|17|N||N|"+CommonUtil.getFutureMonthDay("6", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|18|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|19|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|20|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|21|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|22|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|23|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|24|N||N|"+CommonUtil.getFutureMonthDay("12", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|25|N||N|"+CommonUtil.getFutureMonthDay("15", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|26|N||N|"+CommonUtil.getFutureMonthDay("36", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|27|N||N|"+CommonUtil.getFutureMonthDay("36", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|28|N||N|"+CommonUtil.getFutureMonthDay("48", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|29|N||N|"+CommonUtil.getFutureMonthDay("48", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|30|N||N|"+CommonUtil.getFutureMonthDay("48", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|31|N||N|"+CommonUtil.getFutureMonthDay("72", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|32|N||N|"+CommonUtil.getFutureMonthDay("72", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|33|N||N|"+CommonUtil.getFutureMonthDay("132", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|34|N||N|"+CommonUtil.getFutureMonthDay("134", mEtBirthDay.getText().toString())+"|N");
				alVaccinData.add(mlReturn+"|35|N||N|N|N");
				alVaccinData.add(mlReturn+"|36|N||N|N|N");
				alVaccinData.add(mlReturn+"|37|N||N|N|N");
				alVaccinData.add(mlReturn+"|38|N||N|N|N");
				ContentValues cvVaccin = null;
				for(int i=0; i<alVaccinData.size(); i++){
					cvVaccin = new ContentValues();
					String[] asVaccin = alVaccinData.get(i).toString().split("\\|");
					cvVaccin.put(TBabyVaccinationMst.BABY_ID, asVaccin[0]);
					cvVaccin.put(TBabyVaccinationMst.VACCIN_ID, asVaccin[1]);
					cvVaccin.put(TBabyVaccinationMst.V_FLAG, asVaccin[2]);
					cvVaccin.put(TBabyVaccinationMst.MEMO, asVaccin[3]);
					cvVaccin.put(TBabyVaccinationMst.ALARM, asVaccin[4]);
					cvVaccin.put(TBabyVaccinationMst.VACCIN_DAY, asVaccin[5]);
					cvVaccin.put(TBabyVaccinationMst.DAY_VACCIN, asVaccin[6]);
					long lReturn = dbHelper.createData(cvVaccin, TBabyVaccinationMst.TABLE_NAME);
					if(lReturn > 0){
						isBabyVaccinSuccess = true;
					}else{
						isBabyVaccinSuccess = false;
						break;
					}
				}
	    	}
	    	if(isBabySuccess && isBabyVaccinSuccess){
	    		dbHelper.setTransactionSuccessful();
	    		isSuccess = true;
	    	}
    	}catch(Exception e){
    		isSuccess = false;
    		Log.e(TAG,e.getMessage());
    	}finally{
    		dbHelper.endTransaction();
    		dbHelper.close();
    	}
    	return isSuccess;
    }
}
