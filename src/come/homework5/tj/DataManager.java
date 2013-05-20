/*
 	Assignment #5
 	Author: Josh Leonard & Tyler Shay
 	Filename: DataManager.java
 
 */
package come.homework5.tj;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataManager {
	Context mContext;
	DatabaseHelp dbOpenHelper;
	SQLiteDatabase db;
	DBDAO noteDao;
	
	public DataManager(Context mContext){
		this.mContext = mContext;
		dbOpenHelper = new DatabaseHelp(mContext);
		db = dbOpenHelper.getWritableDatabase();
		noteDao = new DBDAO(db);
	}
	
	public void close(){
		db.close();
	}
	
	
	public long saveNote(Note note){
		return noteDao.save(note);
	}
	
	public boolean updateNote(Note note){
		return noteDao.update(note);
	}
	
	public boolean deleteNote(Note note){
		return noteDao.delete(note);
	}
	
	public Note getNote(String id){
		return noteDao.get(id);
	}
	

	public ArrayList<Note> getAllNotes(){
		return noteDao.getAll();
	}
}
