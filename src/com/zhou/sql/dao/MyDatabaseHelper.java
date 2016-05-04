package com.zhou.sql.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper{
	private static String DB_NAME = "mydata.db";  //数据库名字
	public static String TABLE_NAME = "ZhouLabel"; //表名
	static final String id = "_id";
	static final String path = "path_data";  
    static final String label = "name";  
    
    public static final String CREATE_Label = "create table ZhouLabel ("
			+ "id  integer primary key autoincrement, " 
    		+ "path text, "
			+ "label text)";  //创建表的sql语句
    
    public MyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, 2);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e("table oncreate", "create table");
		db.execSQL(CREATE_Label);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE "+"ZhouLabel"+" ADD label TEXT");
	}


}
