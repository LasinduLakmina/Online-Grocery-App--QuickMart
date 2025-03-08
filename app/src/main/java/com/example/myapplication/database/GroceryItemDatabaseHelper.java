package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroceryItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grocery_db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_GROCERY_ITEMS = "grocery_items";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_AVAILABLE_AMOUNT = "available_amount";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_USER_POINT = "user_point";
    public static final String COLUMN_POPULARITY_POINT = "popularity_point";

    private static final String CREATE_TABLE_GROCERY_ITEMS = "CREATE TABLE " + TABLE_GROCERY_ITEMS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_IMAGE_URL + " TEXT, " +
            COLUMN_CATEGORY + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_AVAILABLE_AMOUNT + " INTEGER, " +
            COLUMN_RATE + " INTEGER, " +
            COLUMN_USER_POINT + " INTEGER, " +
            COLUMN_POPULARITY_POINT + " INTEGER" +
            ");";

    public GroceryItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GROCERY_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY_ITEMS);
        onCreate(db);
    }
}
