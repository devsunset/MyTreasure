/*
* @(#)MyTreasureDatabase.java
* Date : 2010. 03. 08.
* Copyright: (C) 2010 by Nics Android Developer Team All right reserved.
*/
package com.nics.mytreasure.database;

import android.provider.BaseColumns;

/**
* <PRE>
* My Treasure DataBase Table Master
* </PRE>
* @author 황연준
* @version 1.0
* @since mytreasure1.0
*/
public final class MyTreasureDatabase {
	
	/**
	 * Constructor
	 */
	private MyTreasureDatabase(){}
	
	/**
	 * T_BABY_MST TABLE 상수
	 * @author 황연준
	 *
	 */
	public static final class TBabyMst implements BaseColumns{	
		
		public static final String DEFAULT_SORT_ORDER = " BIRTH ASC ";
		public static final String TABLE_NAME = "T_BABY_MST";
		
		public static final String NAME = "NAME";
		public static final String SEX = "SEX";
		public static final String BIRTH = "BIRTH";
		public static final String BLOOD = "BLOOD";
		public static final String CONSTELLATION = "CONSTELLATION";
		public static final String BIRTHSTONES = "BIRTHSTONES";
		public static final String AGETITLE = "AGETITLE";
		public static final String PICTURE = "PICTURE";
		
		public static final String CREATE_SQL_STATEMENT = "create table if not exists " 
			+ TABLE_NAME + "("+TBabyMst._ID+" integer primary key autoincrement, "
			+ NAME + " text not null, "
			+ SEX + " text not null, "
			+ BIRTH + " text not null, "
			+ BLOOD + " text not null, "
			+ CONSTELLATION + " text not null, "
			+ BIRTHSTONES + " text not null, "
			+ AGETITLE + " text not null, "
			+ PICTURE
			+ ");";
	}

	/**
	 * T_VACCINATION_MST TABLE 상수
	 * @author 황연준
	 *
	 */
	public static final class TVaccinationMst implements BaseColumns{
		
		public static final String DEFAULT_SORT_ORDER = "";
		public static final String TABLE_NAME = "T_VACCINATION_MST";
		public static final String VACCIN_GROUP = "VACCIN_GROUP";		
		public static final String VACCIN_TYPE = "VACCIN_TYPE";		
		public static final String VACCIN_NAME = "VACCIN_NAME";		
		public static final String VACCIN_DESC = "VACCIN_DESC";		
		public static final String VACCIN_PERIOD_S = "VACCIN_PERIOD_S";	
		public static final String VACCIN_PERIOD_E = "VACCIN_PERIOD_E";	
		public static final String VACCIN_DEGREE = "VACCIN_DEGREE";	
		public static final String VACCIN_ETC = "VACCIN_ETC";	
		
		public static final String CREATE_SQL_STATEMENT = "CREATE TABLE IF NOT EXISTS " 
			+ TABLE_NAME + "("+TVaccinationMst._ID+" integer primary key autoincrement, "
			+ VACCIN_GROUP + " text not null, "
			+ VACCIN_TYPE + " text not null, "
			+ VACCIN_NAME + " text not null, "
			+ VACCIN_DESC + " , "
			+ VACCIN_PERIOD_S + " , "
			+ VACCIN_PERIOD_E + " , "
			+ VACCIN_DEGREE + " , "
			+ VACCIN_ETC
			+ ");";			
	}
	
	/**
	 * T_BABY_VACCINATION_MST TABLE 상수
	 * @author 황연준
	 *
	 */
	public static final class TBabyVaccinationMst implements BaseColumns{
		
		public static final String DEFAULT_SORT_ORDER = "";
		public static final String TABLE_NAME = "T_BABY_VACCINATION_MST";
		
		public static final String BABY_ID = "BABY_ID";		
		public static final String VACCIN_ID = "VACCIN_ID";		
		public static final String V_FLAG = "V_FLAG";		
		public static final String MEMO = "MEMO";		
		public static final String VACCIN_DAY = "VACCIN_DAY";		
		public static final String DAY_VACCIN = "DAY_VACCIN";		
		public static final String ALARM = "ALARM";		

		public static final String CREATE_SQL_STATEMENT = "create table if not exists " 
			+ TABLE_NAME + "("+TBabyVaccinationMst._ID+" integer primary key autoincrement, "
			+ BABY_ID + " text not null, "
			+ VACCIN_ID + " text not null, "
			+ V_FLAG + " text not null, "
			+ MEMO+ " , "
			+ VACCIN_DAY+ " , "
			+ DAY_VACCIN+ " , "
			+ ALARM
			+ ");";			
	}

}
