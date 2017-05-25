package com.pinioo.android.popular_movies_app_stage_1.Database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.net.URL;

/**
 * Created by bhatt on 23-05-2017.
 */

public class MovieContract {

    private MovieContract(){}

    public static final String CONTENT_AUTHORITY = "com.pinioo.android.popular_movies_app_stage_1";

    public static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";


    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URL = Uri.withAppendedPath(BASE_CONTENT_URL , PATH_MOVIES);

        public final static String TABLE_NAME = "movies";

        public final static String _ID = BaseColumns._ID;
        public final static String MOVIE_JSON_ID = "movie_jason_id";
        public final static String COLUMN_MOVIE_NAME = "name";
        public final static String RATING = "rating";
        public final static String POSTERIMAGE= "posterimage";
        public final static String DATE = "date";
        public final static String OVERVIEW = "overview";
        public final static String REVIEW_JSON = "reviewinjson";;
    }

}
