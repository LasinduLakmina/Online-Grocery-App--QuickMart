package com.example.myapplication.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.GroceryItem;
import com.example.myapplication.database.GroceryItemDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class GroceryItemService {
    private GroceryItemDatabaseHelper dbHelper;

    public GroceryItemService(Context context) {
        dbHelper = new GroceryItemDatabaseHelper(context);
    }

    public long insertGroceryItem(GroceryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroceryItemDatabaseHelper.COLUMN_NAME, item.getName());
        values.put(GroceryItemDatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(GroceryItemDatabaseHelper.COLUMN_IMAGE_URL, item.getImageUrl());
        values.put(GroceryItemDatabaseHelper.COLUMN_CATEGORY, item.getCategory());
        values.put(GroceryItemDatabaseHelper.COLUMN_PRICE, item.getPrice());
        values.put(GroceryItemDatabaseHelper.COLUMN_AVAILABLE_AMOUNT, item.getAvailableAmount());
        values.put(GroceryItemDatabaseHelper.COLUMN_RATE, item.getRate());
        values.put(GroceryItemDatabaseHelper.COLUMN_USER_POINT, item.getUserPoint());
        values.put(GroceryItemDatabaseHelper.COLUMN_POPULARITY_POINT, item.getPopularityPoint());

        long id = db.insert(GroceryItemDatabaseHelper.TABLE_GROCERY_ITEMS, null, values);
        db.close();
        return id;
    }

    public List<GroceryItem> getAllGroceryItems() {
        List<GroceryItem> groceryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(GroceryItemDatabaseHelper.TABLE_GROCERY_ITEMS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                GroceryItem item = new GroceryItem(
                        cursor.getString(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_CATEGORY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_AVAILABLE_AMOUNT))
                );
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_ID)));
                item.setRate(cursor.getInt(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_RATE)));
                item.setUserPoint(cursor.getInt(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_USER_POINT)));
                item.setPopularityPoint(cursor.getInt(cursor.getColumnIndexOrThrow(GroceryItemDatabaseHelper.COLUMN_POPULARITY_POINT)));

                groceryList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return groceryList;
    }

    public int updateGroceryItem(GroceryItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroceryItemDatabaseHelper.COLUMN_NAME, item.getName());
        values.put(GroceryItemDatabaseHelper.COLUMN_DESCRIPTION, item.getDescription());
        values.put(GroceryItemDatabaseHelper.COLUMN_IMAGE_URL, item.getImageUrl());
        values.put(GroceryItemDatabaseHelper.COLUMN_CATEGORY, item.getCategory());
        values.put(GroceryItemDatabaseHelper.COLUMN_PRICE, item.getPrice());
        values.put(GroceryItemDatabaseHelper.COLUMN_AVAILABLE_AMOUNT, item.getAvailableAmount());
        values.put(GroceryItemDatabaseHelper.COLUMN_RATE, item.getRate());
        values.put(GroceryItemDatabaseHelper.COLUMN_USER_POINT, item.getUserPoint());
        values.put(GroceryItemDatabaseHelper.COLUMN_POPULARITY_POINT, item.getPopularityPoint());

        int rowsUpdated = db.update(GroceryItemDatabaseHelper.TABLE_GROCERY_ITEMS, values,
                GroceryItemDatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
        return rowsUpdated;
    }

    public int deleteGroceryItem(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete(GroceryItemDatabaseHelper.TABLE_GROCERY_ITEMS, GroceryItemDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    public int updateAvailableAmount(int id, int newAmount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroceryItemDatabaseHelper.COLUMN_AVAILABLE_AMOUNT, newAmount);

        int rowsUpdated = db.update(GroceryItemDatabaseHelper.TABLE_GROCERY_ITEMS, values,
                GroceryItemDatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated;
    }
}
