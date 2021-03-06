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
    public Boolean isFromDataBase;

    public byte[] bytes;

    public MovieData(String name, String image, double rating, String overview, String relesing_date, int id, int colorID, Boolean isFromDataBase) {

        this.name = name;
        this.image = image;
        this.rating = rating;
        this.overview = overview;
        this.relesing_date = relesing_date;
        this.id = id;
        this.colorID = colorID;
        this.isFromDataBase = isFromDataBase;
    }

    public MovieData(String name, byte[] bytes, String rating, String overview, String relesing_date, String movieid, int colorID, Boolean isFromDataBase) {

        this.name = name;
        this.bytes = bytes;
        this.rating = Double.parseDouble(rating);
        this.overview = overview;
        this.relesing_date = relesing_date;
        this.id = Integer.parseInt(movieid);
        this.colorID = colorID;
        this.isFromDataBase = isFromDataBase;


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
    public boolean getisFromDataBase() { return isFromDataBase; }
    public byte[] getImageinbyte() { return bytes; }

}
