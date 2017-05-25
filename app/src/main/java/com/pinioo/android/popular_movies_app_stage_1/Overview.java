package com.pinioo.android.popular_movies_app_stage_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import static android.R.attr.bitmap;
import static android.R.attr.resource;

/**
 * Created by bhatt on 22-05-2017.
 */

public class Overview extends Fragment {


    static String Original_title;
    static String Poster;
    static String Overview;
    static double Vote;
    static String Vote_string;
    static String Relesing_date;
    static int MovieId;



    private final static String IMAGEURLW500 = "https://image.tmdb.org/t/p/w500";


    ImageView imageView;
    TextView nameView;
    TextView dateView;
    TextView ratingView;
    TextView OveriewView;

    static Bitmap bitmap;



    public Overview() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Original_title = (String) getActivity().getIntent().getExtras().get("original_title");
        Poster = (String) getActivity().getIntent().getExtras().get("poster");
        Overview = (String) getActivity().getIntent().getExtras().get("overview");
        Vote = (double) getActivity().getIntent().getExtras().get("vote");
        Relesing_date = (String) getActivity().getIntent().getExtras().get("relesing_date");
        MovieId = (int)getActivity().getIntent().getExtras().get("Movieid");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.overview, container, false);

        imageView = (ImageView)view.findViewById(R.id.thumbnailImage);
        nameView = (TextView)view.findViewById(R.id.MovieNAME);
        dateView = (TextView)view.findViewById(R.id.MovieDATE);
        ratingView = (TextView)view.findViewById(R.id.MovieAVGRATING);
        OveriewView = (TextView)view.findViewById(R.id.OverviewText);


//        Glide.with(Overview.this)
//                .load(IMAGEURLW500 +Poster)
//                .override(700,700)
//                .thumbnail(1f)
//                .crossFade()
//                .into(imageView);


        Glide.with(this)
                .load(IMAGEURLW500 +Poster)
                .asBitmap()
                .override(700,700)
                .thumbnail(1f)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                        bitmap = resource;
                    }
                });


//        Glide.with(Overview.this)
//                .load(IMAGEURLW500 +Poster)
//                .override(700,700)
//                .thumbnail(1f)
//                .crossFade()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//
//
//                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//                        bitmap = drawable.getBitmap();
//
//                        if(bitmap == null){
//                            Toast.makeText(getContext(),"fail",Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(getContext(),"done",Toast.LENGTH_LONG).show();
//                        }
//
//                        return false;
//                    }
//                })
//                .into(imageView);

        Vote_string = Double.toString(Vote);

//        Reverse_Relesing_date = new StringBuilder(Relesing_date).reverse().toString();

        nameView.setText(Original_title);
        dateView.setText(Relesing_date);
        ratingView.setText(getResources().getText(R.string.Average_rating) +": "+Vote_string);
        OveriewView.setText(Overview);

        Log.e("saveData",Original_title);

        return view;
    }

    public void saveDataOverview(){

        if(bitmap == null){
            Log.e("saveData","fail");
        }
        else {
            Log.e("saveData","done");
            Log.e("saveData",Original_title);

            new FavoriteMovieData(Original_title,Vote_string,Relesing_date,Overview,bitmap);
        }
    }


}
