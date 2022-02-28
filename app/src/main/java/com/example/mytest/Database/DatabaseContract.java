package com.example.mytest.Database;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public  static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGE = "subtitle";
        public static final String COLUMN_NAME_STATUS = "status";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                        FeedEntry.COLUMN_NAME_IMAGE + " TEXT, " +
                        FeedEntry.COLUMN_NAME_STATUS + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

        public static final String DATABASE_ALTER_1 = "ALTER TABLE "
                + TABLE_NAME + " ADD COLUMN " + COLUMN_NAME_STATUS + " status";
    }

}
