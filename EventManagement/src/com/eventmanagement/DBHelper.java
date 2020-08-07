package com.eventmanagement;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.eventmanagement.entity.DataEntity;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+"/EventManagement/db_event.db";
	public static final String TABLE_ADMIN = "admin";
	public static final String TABLE_CATEGORY = "category";
	public static final String TABLE_DATA = "data";

	public DBHelper(Context context){
		super(context, DATABASE_NAME, null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+TABLE_ADMIN+" (id integer primary key AUTOINCREMENT NOT NULL, name text, password text)");
		db.execSQL("create table "+TABLE_CATEGORY+" (id integer primary key AUTOINCREMENT NOT NULL, categoryname text)");
		db.execSQL("create table "+TABLE_DATA+" (id integer primary key AUTOINCREMENT NOT NULL, categoryname text,name text, address text, contact text, rates text,  path text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS post");
		onCreate(db);
	}

	public boolean insertAdmin(){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("name","admin");
		values.put("password","123456");

		Cursor cursor = db.rawQuery("select * from "+TABLE_ADMIN,null);
		if (cursor != null)
			cursor.moveToFirst();
		if (!cursor.moveToFirst()) {
			db.insert(TABLE_ADMIN, null, values);
		}
		return true;
	}

	public boolean insertCategory(String category){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("categoryname",category);

			db.insert(TABLE_CATEGORY, null, values);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean insertData(DataEntity de){
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("categoryname",de.getCategoryname());
			values.put("name",de.getName());
			values.put("address",de.getAddress());
			values.put("contact",de.getContact());
			values.put("rates",de.getRates());
			values.put("path",de.getImagePath());
			db.insert(TABLE_DATA, null, values);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePswd(String pswd){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("password", pswd);

		db.update(TABLE_ADMIN, values,  null, null);
		return true;
	}

	public String getOldPassword(){
		String password="";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select password from "+TABLE_ADMIN,null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.moveToFirst()) {
			do {

				password=cursor.getString(0);
			} while (cursor.moveToNext());
		}
		return password;
	}

	public ArrayList<String> getUserPassword(){
		ArrayList<String> detials= new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select name,password from "+TABLE_ADMIN,null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.moveToFirst()) {
			do {

				detials.add(cursor.getString(0));
				detials.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		return detials;
	}


	public List<String> getAllCategory(){
		List<String> category_list = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from "+TABLE_CATEGORY,null);
		if (cursor != null)
			cursor.moveToFirst();
		if (cursor.moveToFirst()) {
			do {
				category_list.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		return category_list;
	}

	public boolean deleteCategory(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			db.execSQL("delete from "+ TABLE_CATEGORY+" where categoryname='"+name+"'" );
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteData(DataEntity de){
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			db.execSQL("delete from "+ TABLE_DATA+" where categoryname='"+de.getCategoryname()+"' and name='"+de.getName()+"' and contact='"+de.getContact()+"'");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateCategory(String name){
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("update "+ TABLE_CATEGORY+" set categoryname='"+name+"', password='");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//address text, contact text, rates text,  path
	public boolean updateData(DataEntity de){
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL("update "+ TABLE_DATA+" set categoryname='"+de.getCategoryname()+"', name='"+de.getName()
					+"', address='"+de.getAddress()+"', contact ='"+de.getContact()+"', rates='"+de.getRates()
					+"', path='"+de.getImagePath()+"'"+" where id="+de.getId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<DataEntity> getAllData(String category){
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<DataEntity> dataList = new ArrayList<DataEntity>();
		Cursor cursor = db.rawQuery("select * from "+TABLE_DATA+" where categoryname='"+category+"'",null);
		if (cursor != null)
		{
			if (cursor.moveToFirst()) {
				do {
					DataEntity de = new DataEntity(cursor.getInt(0), category,cursor.getString(2),cursor.getString(3),
							cursor.getString(4), cursor.getString(5), cursor.getString(6) );
					dataList.add(de);
				} while (cursor.moveToNext());
			}
		}
		return dataList;
	}
}
