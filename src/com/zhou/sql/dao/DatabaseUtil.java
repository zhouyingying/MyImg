package com.zhou.sql.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.zhou.bean.label;

public class DatabaseUtil {
	private MyDatabaseHelper helper;

	public DatabaseUtil(Context context) {
		super();
		helper = new MyDatabaseHelper(context);
	}
	
	/**插入数据
	 * @param String
	 * */
	public boolean Insert(label person){
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into "+"ZhouLabel"
					+"(path,label) values (" 
					+ "'"+person.getPath()
					+ "' ," + "'"+ person.getLabel() + "'" + ")";
		try {
			db.execSQL(sql);
			return true;
			} catch (SQLException e){  
				return false;
				}finally{
					db.close();
				}
	}
	
	/**通过id更新数据
	 * @param Person person , int id
	 * */
	public void Update(label person ,int id){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("path", person.getPath());
		values.put("label", person.getLabel());
		int rows = db.update("ZhouLabel", values, "id=?", new String[] { id + "" });
		db.close();
	}
	
	/**通过id删除
	 * @param int id
	 * */
	public void Delete(int id){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		int raw = db.delete("ZhouLabel", "id=?", new String[]{id+""});
		db.close();
	}
	
	/**查询全部
	 * 
	 * */
	public List<label> queryAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<label> list = new ArrayList<label>();
		Cursor cursor = db.query("ZhouLabel", null, null,null, null, null, null);
		while(cursor.moveToNext()){
			label person = new label();
			 person.setId(cursor.getInt(cursor.getColumnIndex("_id")));   
			 person.setPath(cursor.getString(cursor.getColumnIndex("path"))); 
			 person.setLabel(cursor.getString(cursor.getColumnIndex("label")));
			 list.add(person);
		}
		db.close();
		return list;
	}
	
	
	/**
	 * 通过label查询
	 * */
	public List<label> queryByname(String label){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<label> list = new ArrayList<label>();
		Cursor cursor = db.query("ZhouLabel", new String[]{"id","path","label"}, "label like ? " ,new String[]{"%" +label + "%" }, null, null, "id asc");
		while(cursor.moveToNext()){
			label person = new label();
			 person.setId(cursor.getInt(cursor.getColumnIndex("id")));   
			 person.setPath(cursor.getString(cursor.getColumnIndex("path"))); 
			 person.setLabel(cursor.getString(cursor.getColumnIndex("label")));
			 list.add(person);
		}
		db.close();
		return list;
	} 
	
	//通过path查询
	public List<label> queryByPath(String pat){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<label> list = new ArrayList<label>();
		Cursor cursor = db.query("ZhouLabel", new String[]{"id","path","label"}, "path like ?", new String[]{ pat } , null, null, "id asc");
		while(cursor.moveToNext()){
			label person = new label();
			 person.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 person.setPath(cursor.getString(cursor.getColumnIndex("path"))); 
			 person.setLabel(cursor.getString(cursor.getColumnIndex("label")));
			 list.add(person);
		}
		db.close();
		return list;
	}
}
