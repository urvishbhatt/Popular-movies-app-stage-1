package com.pinioo.android.popular_movies_app_stage_1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bhatt on 26-05-2017.
 */

public class MyParcelable implements Parcelable {

    public String original_title;
    public String poster;
    public String overview;
    public double vote;
    public String relesing_date;
    public int Movieid;



    public MyParcelable(String original_title, String poster, String overview, double vote, String relesing_date, int movieid) {
        this.original_title = original_title;
        this.poster = poster;
        this.overview = overview;
        this.vote = vote;
        this.relesing_date = relesing_date;
        this.relesing_date = relesing_date;
        this.Movieid = movieid;

    }

    protected MyParcelable(Parcel in) {
        original_title = in.readString();
        poster = in.readString();
        overview = in.readString();
        vote = in.readDouble();
        relesing_date = in.readString();
        Movieid = in.readInt();

    }

//    public MyParcelable(String original_title, String poster, String overview, double vote, String relesing_date, int movieid) {
//        this.original_title = original_title;
//        this.poster = poster;
//        this.overview = overview;
//        this.vote = vote;
//        this.relesing_date = relesing_date;
//        this.relesing_date = relesing_date;
//        this.Movieid = movieid;
//    }




    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_title);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeDouble(vote);
        dest.writeString(relesing_date);
        dest.writeInt(Movieid);

    }


    public static final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
        @Override
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        @Override
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
