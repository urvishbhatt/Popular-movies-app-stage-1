package com.pinioo.android.popular_movies_app_stage_1.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.R.attr.id;
import static android.R.attr.logo;

/**
 * Created by bhatt on 23-05-2017.
 */

public class MovieContentProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIES,MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIES + "/#",MOVIES_ID);
    }

    public static final String LOG_TAG = MovieContentProvider.class.getSimpleName();

    private MovieSQLiteOpenHelper movieSQLiteOpenHelper;

    @Override
    public boolean onCreate() {

        movieSQLiteOpenHelper = new MovieSQLiteOpenHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = movieSQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);

        switch (match){

            case MOVIES:
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case MOVIES_ID:
                selection = MovieContract.MovieEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return insertMovie(uri,values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri,ContentValues contentValues){

        SQLiteDatabase database = movieSQLiteOpenHelper.getReadableDatabase();

        long id = database.insert(MovieContract.MovieEntry.TABLE_NAME,null,contentValues);

        if(id == -1){
            Log.e(LOG_TAG,"Faild to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri,id);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
