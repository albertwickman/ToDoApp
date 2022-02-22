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

    public static final int VERSION = 1;
    public static final String TABLE_NAME = "FeedReader.db";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.FeedEntry.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DatabaseContract.FeedEntry.SQL_DELETE_ENTRIES);
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
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE, item.getTitle());
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_IMAGE, item.getImageRes());
        db.insert(DatabaseContract.FeedEntry.TABLE_NAME, null, values);
    }

    public void readDatabase() {
        db = this.getReadableDatabase();

        String[] projection = { BaseColumns._ID, DatabaseContract.FeedEntry.COLUMN_NAME_TITLE, DatabaseContract.FeedEntry.COLUMN_NAME_IMAGE};
        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { "My Title" };
        String sortOrder = DatabaseContract.FeedEntry.COLUMN_NAME_IMAGE + " DESC";

        Cursor cursor = db.query(DatabaseContract.FeedEntry.TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);

        List itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();
    }

    public List<ItemModel> getAllItems() {
        openDatabase();
        List<ItemModel> itemsList = new ArrayList<>();
        Cursor cursor = null;

        db.beginTransaction();
        try {
            cursor = db.query(DatabaseContract.FeedEntry.TABLE_NAME, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ItemModel item = new ItemModel();
                        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE)));
                        item.setImageRes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.COLUMN_NAME_IMAGE)));
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
            cursor = db.query(DatabaseContract.FeedEntry.TABLE_NAME, null, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ItemModel item = new ItemModel();
                        item.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE)));
                        item.setImageRes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.COLUMN_NAME_IMAGE)));
                        if (item.isFavorite()) {
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
        db.delete(DatabaseContract.FeedEntry.TABLE_NAME, null, null);
        db.execSQL("delete from "+ DatabaseContract.FeedEntry.TABLE_NAME);
        db.close();
    }

    public void deleteItem(int id) {
        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(DatabaseContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void updateItems(String title, int id) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TITLE, title);

        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.update(DatabaseContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);
    }

}

