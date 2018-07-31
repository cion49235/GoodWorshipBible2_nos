package com.good.worshipbible.nos.ccm.db.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VoicePause_DBOpenHelper extends SQLiteOpenHelper {
	public VoicePause_DBOpenHelper(Context context) {
		super(context, "voice_pause.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
			String createtable = "create table voice_pause ( _id integer primary key autoincrement,"+
			"kwon text, voice_currentPosition integer, jang text);";
			db.execSQL(createtable);

	}		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exits voice_pause"); 
		onCreate(db);
	}
}