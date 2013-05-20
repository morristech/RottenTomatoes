/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: DatabaseHelp.java
 
 */
package come.homework5.tj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelp extends SQLiteOpenHelper{
	static final String DATABASE_NAME = "Movies.db";
	static final int DATABASE_VERSION = 3;
	static final String TAG = "demo2";

	DatabaseHelp(Context mContext){
		super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		DBTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading db from version " + oldVersion + " to " + newVersion + ", this will destroy all old data");
		DBTable.onUpgrade(db, oldVersion, newVersion);		
	}
}
