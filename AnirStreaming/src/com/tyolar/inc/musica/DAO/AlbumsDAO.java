package com.tyolar.inc.musica.DAO;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tyolar.inc.musica.model.PlayList;
import com.tyolar.inc.musica.model.album;
import com.tyolar.inc.musica.utils.tools;

public class AlbumsDAO {

	private static final String TAG = "DBAdapter";

	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	public static final String KEY_json = "json_data";

	public static final int COL_NAME = 1;
	public static final String[] ALL_KEYS = new String[] { KEY_ROWID, KEY_json };

	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "musica_album";	
	public static final String DATABASE_TABLE = "almbums";


	private static final String DATABASE_CREATE_SQL = "create table "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " integer primary key autoincrement, "

			+ KEY_json + " text not null " + ");";

	private static DatabaseHelper myDBHelper;
	private static SQLiteDatabase db;



	public static void save(Context c, album v) throws Exception {
		myDBHelper = new DatabaseHelper(c);
		db = myDBHelper.getWritableDatabase();
		Gson gson = new Gson();
		String json = gson.toJson(v);
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_json, json);
		db.close();
		myDBHelper = new DatabaseHelper(c);
		db = myDBHelper.getWritableDatabase();
		String where = KEY_ROWID + "=" + v.getId();
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_ROWID, v.getId());
		newValues.put(KEY_json, gson.toJson(v));
	     db.insert(DATABASE_TABLE, null, newValues);
	}
	
	
	
	public static void remove(Context c, album v) throws Exception {
		myDBHelper = new DatabaseHelper(c);
		db = myDBHelper.getWritableDatabase();
		String where = KEY_ROWID + "=" + v.getId();
		db.delete(DATABASE_TABLE, where, null);
		db.close();
	}

	public static boolean Exists(Context c,album v) {
		myDBHelper = new DatabaseHelper(c);
		db = myDBHelper.getWritableDatabase();
		String where = "_id="+v.getId();
		Cursor cursor =db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
    	   boolean exists = (cursor.getCount() > 0);
		   cursor.close();
		   return exists;
		}
	
	private static Cursor getAllRows(Context ca) {
		myDBHelper = new DatabaseHelper(ca);
		db = myDBHelper.getWritableDatabase();
		String where = null;
		Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		db.close();
		return c;
	}

	public static List<album> get(Context ca) throws JSONException {
		Cursor cursor = getAllRows(ca);
		List<album> ss = new ArrayList<album>();
		if (cursor.moveToFirst()) {
			do {
				Gson gson = new Gson();
				Type listType = new TypeToken<album>() {
				}.getType();
				String json = cursor.getString(1);
				album t = gson.fromJson(json, listType);
				ss.add(t);
			} while (cursor.moveToNext());
		}
		return ss;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(_db);
		}
	}
}
