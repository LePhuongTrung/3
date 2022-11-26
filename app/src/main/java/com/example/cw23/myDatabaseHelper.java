package com.example.cw23;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class myDatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "CW23.db";

    static final String TABLE_NAME = "Link";
    static final String ID_COLUMN = "id";
    static final String Link_COLUMN = "Link";
    SQLiteDatabase sqLiteDatabase;

    Context context;
    public myDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Link_COLUMN + " TEXT) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addLink(String link){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Link_COLUMN, link);

        sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Oke", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getLink(){
        String query = "SELECT * FROM " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }


}
