/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: DBTable.java
 
 */
package come.homework5.tj;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBTable {
	static final String TABLE_NAME = "FavoriteMovies";
	static final String NOTE_ID = "_id";
	static final String MOVIE_ID = "Movie_id";

	static public void onCreate(SQLiteDatabase db){	
		StringBuilder sb = new StringBuilder();		
		sb.append("CREATE TABLE " + DBTable.TABLE_NAME + " (");
		sb.append(DBTable.NOTE_ID + " integer primary key autoincrement, ");
		sb.append(DBTable.MOVIE_ID + " text not null");
		sb.append(");");	
		Log.v("sb = ", sb.toString());

		try{
			db.execSQL(sb.toString());
			Log.v("table has been created", "true");
		} catch (SQLException e){				
			e.printStackTrace();
		}


	}

	static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + DBTable.TABLE_NAME);
		DBTable.onCreate(db);
	}	
}
