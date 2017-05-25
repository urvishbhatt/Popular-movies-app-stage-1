package com.pinioo.android.popular_movies_app_stage_1;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by bhatt on 23-05-2017.
 */

public class FavoriteMovieData {

    public static String Original_title;
    public static String Vote_string;
    public static String Relesing_date;
    public static String Overview;

    public static Bitmap imagebitmap;


    public static String DatabasejsonReponceString;

    FavoriteMovieData(String Original_title, String Vote_string, String Relesing_date, String Overview, Bitmap bitmap){
        this.Original_title = Original_title;
        this.Vote_string = Vote_string;
        this.Relesing_date = Relesing_date;
        this.Overview = Overview;
        this.imagebitmap = bitmap;
    }


    public FavoriteMovieData(String DatabasejsonReponceString) {
        this.DatabasejsonReponceString = DatabasejsonReponceString;


    }

    public static String getOriginal_title(){ return Original_title; }
    public static String getVote_string() { return Vote_string; }
    public static String getRelesing_date() { return Relesing_date; }
    public static String getOverview() { return Overview; }

    public static Bitmap getimageBitmap() { return imagebitmap; }

    public static String getDatabasejsonReponceString() { return DatabasejsonReponceString; }


}
