package com.example.mytest.Database;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.example.mytest.Model.ItemModel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String NAME = "FeedReader.db";
    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_IMAGE = "subtitle";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_ID = "status";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_TITLE + " TEXT, "
            + COLUMN_NAME_IMAGE + " INTEGER, " + COLUMN_NAME_STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertItem(ItemModel item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_IMAGE, item.getImageRes());
        values.put(COLUMN_NAME_STATUS, 0);
        values.put(COLUMN_NAME_TITLE, item.getTitle());
        db.insert(TABLE_NAME, null, values);
    }

    public List<ItemModel> getAllItems() {
        openDatabase();
        List<ItemModel> itemsList = new ArrayList<>();
        Cursor cursor = null;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ItemModel item = new ItemModel();
                        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
                        item.setImageRes(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_IMAGE)));
                        item.setFavoriteStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_STATUS)));
                        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID)));
                        itemsList.add(item);
                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return itemsList;
    }

    public List<ItemModel> getFavorites() {
        openDatabase();
        List<ItemModel> favorites = new ArrayList<>();
        Cursor cursor = null;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ItemModel item = new ItemModel();
                        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE)));
                        item.setImageRes(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_IMAGE)));
                        item.setFavoriteStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_STATUS)));
                        if (item.getFavoriteStatus() == 1) {
                            favorites.add(item);
                        }
                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return favorites;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_STATUS, status);
        db.update(TABLE_NAME, cv, COLUMN_NAME_ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateItem(int id, String title) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_TITLE, title);
        db.update(TABLE_NAME, cv, COLUMN_NAME_ID + "= ?", new String[] {String.valueOf(id)});
    }


    public void deleteItem(int id) {
        String selection = COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, selection, selectionArgs);
    }



}


