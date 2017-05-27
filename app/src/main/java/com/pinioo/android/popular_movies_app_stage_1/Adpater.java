package com.pinioo.android.popular_movies_app_stage_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.pinioo.android.popular_movies_app_stage_1.R.id.image;

/**
 * Created by acer-pc on 04-03-2017.
 */

public class Adpater extends ArrayAdapter<MovieData>{

//    private final static String IMAGEURLW300 = "https://image.tmdb.org/t/p/w300";
    private final static String IMAGEURLW300 = "https://image.tmdb.org/t/p/w500";
//    private final static String IMAGEURLW300 = "https://image.tmdb.org/t/p/w250";



    private Context context;
    private int ColorID;
    public boolean isfromdatabse;
    public byte[] bytes;
    public Bitmap bitmap;

    public Adpater(Context context, ArrayList<MovieData> MovieData) {
        super(context, 0, MovieData);
        this.context = context;
    }





    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View gridView  = convertView;

        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.gridadpater,parent,false);

        }

        MovieData currentMovieData = getItem(position);

        ImageView imageView = (ImageView)gridView.findViewById(R.id.MovieImageVIewID);
        TextView textView = (TextView)gridView.findViewById(R.id.MovieNameID);
        TextView ratingview = (TextView)gridView.findViewById(R.id.MovieRatingID);
        View TextLinear = gridView.findViewById(R.id.textcontainer);

        TextLinear.setBackgroundColor(currentMovieData.getColorID());
        isfromdatabse = currentMovieData.getisFromDataBase();

        if(isfromdatabse){
            bytes = currentMovieData.getImageinbyte();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,100, bytes.length);

            Glide.with(context)
                    .load(bytes)
                    .override(300,300)
                    .thumbnail(1f)
                    .crossFade()
                    .into(imageView);
        }
        else {
            Glide.with(context)
                    .load(IMAGEURLW300 + currentMovieData.getMovieImage())
                    .override(300,300)
                    .thumbnail(1f)
                    .crossFade()
                    .into(imageView);
        }

        double ratingFloat = currentMovieData.getMovieRating();
        String ratingText = String.valueOf(ratingFloat);

        textView.setText(currentMovieData.getMovieName());
        ratingview.setText(ratingText);

        return gridView;

    }
}