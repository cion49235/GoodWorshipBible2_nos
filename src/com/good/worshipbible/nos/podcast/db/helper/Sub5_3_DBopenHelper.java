package com.good.worshipbible.nos.podcast.db.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sub5_3_DBopenHelper extends SQLiteOpenHelper {
	public Sub5_3_DBopenHelper(Context context) {
		super(context, "favorite_podcast.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
			String createtable = "create table favorite_list ( _id integer primary key autoincrement,"+
			"id text, num text, title text, provider text, imageurl text, rssurl text, udate text);";
			db.execSQL(createtable);

	}		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               		
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exits favorite_list"); 
		onCreate(db);
	}
}