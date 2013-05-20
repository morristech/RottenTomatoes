/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: DBDAO.java
 
 */
package come.homework5.tj;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBDAO { 
	private SQLiteDatabase db;


	public DBDAO(SQLiteDatabase db){
		this.db = db;
	}

	public long save(Note note){
		Log.v("save was called", "true");
		ContentValues values = new ContentValues();
		Log.v("save was called", note.getId());
		values.put(DBTable.MOVIE_ID, note.getId());
		return db.insert(DBTable.TABLE_NAME, null, values);
	}

	public boolean update(Note note){
		ContentValues values = new ContentValues();
		values.put(DBTable.MOVIE_ID, note.getId());
		return db.update(DBTable.TABLE_NAME, values, DBTable.NOTE_ID+"="+ note.get_id(), null) > 0;		
	}	


	public boolean delete(Note note){
		return db.delete(DBTable.TABLE_NAME, DBTable.MOVIE_ID+"="+note.getId(), null)>0;
	}

	public Note get(String id){
		Note note = new Note();
		Cursor c = db.query(true, DBTable.TABLE_NAME, 
				new String[]{DBTable.NOTE_ID, DBTable.MOVIE_ID}, 
				DBTable.MOVIE_ID+"="+ id, null, null, null, null, null);
		if(c.getCount() != 0){
			c.moveToFirst();
			note = this.buildNoteFromCursor(c);	
			note.setFavorite(true);
		}	

		if(!c.isClosed()){
			c.close();
		}
		if(note.getId() != null)
			Log.v("(In get() note.getId() = ", note.getId());
		return note;
	}

	public ArrayList<Note> getAll(){
		ArrayList<Note> list = new ArrayList<Note>();
		Cursor c = db.query(DBTable.TABLE_NAME, 
				new String[]{DBTable.NOTE_ID, DBTable.MOVIE_ID}, 
				null, null, null, null, null);
		if(c.getCount() != 0){
			c.moveToFirst();			
			do{
				Note note = this.buildNoteFromCursor(c);
				if(note != null){
					list.add(note);
				}				
			} while(c.moveToNext());

			if(!c.isClosed()){
				c.close();
			}
		}
		return list;
	}

	private Note buildNoteFromCursor(Cursor c){
		Note note = null;		
		if(c != null){
			note = new Note();
			note.set_id(c.getLong(0));
			note.setId(c.getString(1));
		}
		return note;
	}
}