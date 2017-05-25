package com.pinioo.android.popular_movies_app_stage_1.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhatt on 23-05-2017.
 */

public class MovieSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    public MovieSQLiteOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_QUERY = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "("
                + MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieContract.MovieEntry.MOVIE_JSON_ID + " INTEGER, "
                + MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT, "
                + MovieContract.MovieEntry.RATING + " TEXT, "
                + MovieContract.MovieEntry.POSTERIMAGE + " BLOB, "
                + MovieContract.MovieEntry.DATE + " TEXT, "
                + MovieContract.MovieEntry.OVERVIEW + " TEXT, "
                + MovieContract.MovieEntry.REVIEW_JSON + " TEXT);";

        db.execSQL(SQL_QUERY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
