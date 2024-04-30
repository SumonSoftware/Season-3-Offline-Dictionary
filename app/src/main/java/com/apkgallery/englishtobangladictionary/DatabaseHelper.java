package com.apkgallery.englishtobangladictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;



public class DatabaseHelper extends SQLiteAssetHelper {
    public DatabaseHelper(Context context) {
        super(context, "dictionary.db", null, 1);

    }


public Cursor getAllData(){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" select * from Dictionary ",null);
        return cursor;

}


public Cursor searchView(String key, Context context) {
    if (!key.isEmpty()) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Dictionary WHERE ";
        String[] selectionArgs = new String[key.length()];

        // Loop to construct the query dynamically
        for (int i = 0; i < key.length(); i++) {
            query += "substr(word, " + (i + 1) + ", 1) = ? ";
            selectionArgs[i] = key.substring(i, i + 1);
            if (i < key.length() - 1) {
                query += "AND ";
            }
        }

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Check if cursor is empty
        if (cursor.getCount() == 0) {
            // If no data found, show a toast message
            Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show();
        }

        return cursor;
    } else {
        // Handle the case where key is empty
        // For example, you might return null or handle it in some other way
        return null;
    }
}









}
