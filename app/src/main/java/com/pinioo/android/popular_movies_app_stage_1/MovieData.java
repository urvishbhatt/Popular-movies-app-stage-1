package com.pinioo.android.popular_movies_app_stage_1;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by acer-pc on 28-02-2017.
 */

public class MovieData {

    public String name;
    public String image;
    public double rating;
    public String overview;
    public String relesing_date;
    public int id;
    public int colorID;


    public MovieData(String name, String image, double rating,String overview,String relesing_date,int id,int colorID) {

        this.name = name;
        this.image = image;
        this.rating = rating;
        this.overview = overview;
        this.relesing_date = relesing_date;
        this.id = id;
        this.colorID = colorID;
    }

    public String getMovieName(){
        return name;
    }
    public String getMovieImage(){ return image; }
    public double getMovieRating() { return rating; }
    public String getMovieOverview() { return overview; }
    public String getMovieRelesing_Date() { return relesing_date; }
    public int getMovieId() { return id; }
    public int getColorID(){ return colorID; }

}
