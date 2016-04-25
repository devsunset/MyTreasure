/*
* @(#)MyTreasureDatabaseHelper.java
* Date : 2010. 03. 08.
* Copyright: (C) 2010 by NicsTech Android Developer Team All right reserved.
*/
package com.nics.mytreasure.database;

import com.nics.mytreasure.database.MyTreasureDatabase.TBabyMst;
import com.nics.mytreasure.database.MyTreasureDatabase.TBabyVaccinationMst;
import com.nics.mytreasure.database.MyTreasureDatabase.TVaccinationMst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
* <PRE>
* MyTreasureDatabaseHelper
* </PRE>
* @author 황연준
* @version 1.0
* @since mytreasure1.0
*/
public class MyTreasureDatabaseHelper {
	private static final String TAG = "MyTreasureDatabaseHelper";
	private static final boolean LOGFLAG = false;
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "mytreasurenics.db";
	private static final String KEY_ROWID = "_id";
	
	private String strTableName = null;
	
	private final Context mCtx;
	private DatabaseHelper mDatabaseHelper = null;
	private SQLiteDatabase mSQLDb = null;
	
	private class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public DatabaseHelper(Context context, 
				String name,
				CursorFactory factory, 
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TBabyMst.CREATE_SQL_STATEMENT);
			db.execSQL(TVaccinationMst.CREATE_SQL_STATEMENT);
			db.execSQL(TBabyVaccinationMst.CREATE_SQL_STATEMENT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, 
				int oldVersion, 
				int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TBabyMst.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + TVaccinationMst.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + TBabyVaccinationMst.TABLE_NAME);
		}
	}
	
	/**
	 * Constructor
	 * @param ctx
	 */
	public MyTreasureDatabaseHelper(Context ctx){
		this.mCtx = ctx;
	}
	
	/**
	 * Constructor
	 * @param ctx
	 * @param table
	 * @param dbCreate
	 */
	public MyTreasureDatabaseHelper(Context ctx, 
			String table){
		this.mCtx = ctx;
		this.strTableName = table;
	}
	
	/**
	 * Constructor
	 * @param ctx
	 * @param table
	 * @param columns
	 */
	public MyTreasureDatabaseHelper(Context ctx, 
			String table, 
			String[] columns){
		this.mCtx = ctx;
		this.strTableName = table;
	}
	
	/**
	 * DATABASE OPEN
	 * @return
	 * @throws SQLException
	 */
	public MyTreasureDatabaseHelper open() throws SQLException {
		mDatabaseHelper = new DatabaseHelper(mCtx);
		try{
			mSQLDb = mDatabaseHelper.getWritableDatabase();
		}catch(Exception e){
			mSQLDb = mDatabaseHelper.getReadableDatabase();
		}
		return this;		
	}
	
	/**
	 * DATABASE CLOSE
	 * @return
	 */
	public void close(){
		if(mSQLDb !=null){
			mSQLDb.close();
		}
		
		if(mDatabaseHelper !=null){
			mDatabaseHelper.close();
		}
	}
	
	/**
	 * Start Transaction
	 */
	public void beginTransaction(){
		if(mSQLDb !=null){
			mSQLDb.beginTransaction();
		}
	}
	
	/**
	 * Success Transaction
	 */
	public void setTransactionSuccessful(){
		if(mSQLDb !=null){
			mSQLDb.setTransactionSuccessful();
		}
	}
	
	/**
	 * End Transaction
	 */
	public void endTransaction(){
		if(mSQLDb !=null){
			mSQLDb.endTransaction();
		}
	}
	/**
	 * Insert Data
	 * @param contentValues
	 * @return
	 */
	public long createData(ContentValues contentValues){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Insert Data");
		}
		return mSQLDb.insert(strTableName, null, contentValues);
	}
	
	/**
	 * 
	 * @param contentValues
	 * @param sTalbeName
	 * @return
	 */
	public long createData(ContentValues contentValues, String sTalbeName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTalbeName+"] Insert Data");
		}
		return mSQLDb.insert(sTalbeName, null, contentValues);
	}
	
	/**
	 * Delete Data
	 * @param rowID
	 * @return
	 */
	public boolean deleteData(long rowID){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Delete Data");
		}
		return mSQLDb.delete(strTableName, KEY_ROWID + "=" + rowID, null) > 0;
	}
	
	/**
	 * Delete Data
	 * @param rowID
	 * @param sTalbeName
	 * @return
	 */
	public boolean deleteData(long rowID, String sTalbeName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTalbeName+"] Delete Data");
		}
		return mSQLDb.delete(sTalbeName, KEY_ROWID + "=" + rowID, null) > 0;
	}
	
	/**
	 * Delete Data
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int deleteData(String whereClause, String[] whereArgs){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Delete Data");
		}
		return mSQLDb.delete(strTableName, whereClause, whereArgs);
	}
	
	/**
	 * Delete Data
	 * @param whereClause
	 * @param whereArgs
	 * @param sTalbeName
	 * @return
	 */
	public int deleteData(String whereClause, String[] whereArgs, String sTalbeName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTalbeName+"] Delete Data");
		}
		return mSQLDb.delete(sTalbeName, whereClause, whereArgs);
	}
	
	/**
	 * Update Data
	 * @param rowID
	 * @param contentValues
	 * @return
	 */
	public boolean updateData(long rowID, ContentValues contentValues){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Update Data");
		}
		return mSQLDb.update(strTableName, contentValues, KEY_ROWID + "=" + rowID, null) > 0;
	}
	
	/**
	 * Update Data
	 * @param rowID
	 * @param contentValues
	 * @param sTalbeName
	 * @return
	 */
	public boolean updateData(long rowID, ContentValues contentValues, String sTalbeName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTalbeName+"] Update Data");
		}
		return mSQLDb.update(sTalbeName, contentValues, KEY_ROWID + "=" + rowID, null) > 0;
	}

	/**
	 * Update Data
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int updateData(ContentValues values, 
			String whereClause, 
			String[] whereArgs){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Update Data");
		}
		return mSQLDb.update(strTableName, values, whereClause, whereArgs);
	}
	
	/**
	 * Update Data
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @param sTalbeName
	 * @return
	 */
	public int updateData(ContentValues values, 
			String whereClause, 
			String[] whereArgs
			, String sTalbeName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTalbeName+"] Update Data");
		}
		return mSQLDb.update(sTalbeName, values, whereClause, whereArgs);
	}
	
	/**
	 * Select All Data
	 * @param saColumns
	 * @return
	 */
	public Cursor getAllData(String[] saColumns){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Select All Data");
		}
		return mSQLDb.query(strTableName, saColumns, null, null, null, null, null);
	}
	
	/**
	 * Select All Data
	 * @param saColumns
	 * @param sTableName
	 * @return
	 */
	public Cursor getTableAllData(String[] saColumns, String sTableName){
		if(LOGFLAG){
			Log.i(TAG, "["+sTableName+"] Select All Data");
		}
		return mSQLDb.query(sTableName, saColumns, null, null, null, null, null);
	}
	
	/**
	 * 
	 * @param saColumns
	 * @param sTableName
	 * @param sWhere
	 * @return
	 */
	public Cursor getTableWhereData(String[] saColumns, String sTableName, String sWhere){
		if(LOGFLAG){
			Log.i(TAG, "["+sTableName+"] Select All Data");
		}
		return mSQLDb.query(sTableName, saColumns, sWhere, null, null, null, null);
	}

	/**
	 * Select All Data
	 * @param saColumns
	 * @return
	 */
	public Cursor getAllData(String[] saColumns, String orderBy){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Select All Data");
		}
		return mSQLDb.query(strTableName, saColumns, null, null, null, null, orderBy);
	}
	
	/**
	 * Select All Data
	 * @param saColumns
	 * @param sTableName
	 * @param orderBy
	 * @return
	 */
	public Cursor getTableAllData(String[] saColumns, String sTableName, String orderBy){
		if(LOGFLAG){
			Log.i(TAG, "["+sTableName+"] Select All Data");
		}
		return mSQLDb.query(sTableName, saColumns, null, null, null, null, orderBy);
	}
	
	/**
	 * Select All Data
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public Cursor getAllData(String[] columns, 
			String selection, 
			String[] selectionArgs, 
			String groupBy, 
			String having, 
			String orderBy){
		if(LOGFLAG){
			Log.i(TAG, "["+strTableName+"] Select All Data");
		}
		return mSQLDb.query(strTableName, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	public Cursor getTableAllData(String[] columns, String sTableName, 
			String selection, 
			String[] selectionArgs, 
			String groupBy, 
			String having, 
			String orderBy){
		if(LOGFLAG){
			Log.i(TAG, "["+sTableName+"] Select All Data");
		}
		return mSQLDb.query(sTableName, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	/**
	 * Select Specified Data
	 * @param rowID
	 * @param saColumns
	 * @return
	 * @throws SQLException
	 */
	public Cursor getData(long rowID, String[] saColumns) throws SQLException{
		Cursor mCursor = mSQLDb.query(true, strTableName, saColumns, KEY_ROWID + "=" + rowID, null, null, null, null, null);
		if(mCursor != null){
			if(LOGFLAG){
				Log.i(TAG, "["+strTableName+"] Select Data");
			}
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/**
	 * Select Specified Data
	 * @param rowID
	 * @param saColumns
	 * @param saColumns
	 * @return
	 * @throws SQLException
	 */
	public Cursor getData(long rowID, String[] saColumns,String orderBy) throws SQLException{
		Cursor mCursor = mSQLDb.query(true, strTableName, saColumns, KEY_ROWID + "=" + rowID, null, null, null, null, orderBy);
		if(mCursor != null){
			if(LOGFLAG){
				Log.i(TAG, "["+strTableName+"] Select Data");
			}
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	/**
	 * Select Specified Data
	 * @param distinct
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	public Cursor getData(boolean distinct, 
			String[] columns, 
			String selection, 
			String[] selectionArgs, 
			String groupBy, 
			String having, 
			String orderBy, 
			String limit){
		Cursor mCursor = mSQLDb.query(distinct, strTableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		if(mCursor != null){
			if(LOGFLAG){
				Log.i(TAG, "["+strTableName+"] Select Data");
			}
			mCursor.moveToFirst();
		}
		return mCursor;
	}	
	
	/**
	 * SQL QUERY  실행
	  * @param sQuery 
	  * @param sSelectionArgs
	  * @return
	  */
	 public Cursor getQueryData(String sQuery, String[] sSelectionArgs){
	  return mSQLDb.rawQuery(sQuery, sSelectionArgs);
	 }
}
