package com.pinioo.android.popular_movies_app_stage_1;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class DetailsActivity extends AppCompatActivity {


    String MovieAPI;

    String MovieAPIwebURL = "https://api.themoviedb.org/3/movie/";
    String MovieAPIwebURL2 = "/videos?api_key=";
    String KEY = "382a81cb81a8ab80eb5f89325e2095d3";
    String MovieAPIwebURL3 = "&language=en-US";


    String VideoLinkKey;

    String YOUTUBE = "http://youtube.com/watch?v=";
    String YOUTUBE_TrailerLink = "";

    String Original_title;
    String Poster;
    String Overview;
    double Vote;
    String Vote_string;
    String Relesing_date;
    String Reverse_Relesing_date;
    int MovieId;
    String MovieIdString;

    private final static String IMAGEURLW500 = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Original_title = (String) getIntent().getExtras().get("original_title");
        Poster = (String) getIntent().getExtras().get("poster");
        Overview = (String) getIntent().getExtras().get("overview");
        Vote = (double) getIntent().getExtras().get("vote");
        Relesing_date = (String) getIntent().getExtras().get("relesing_date");
        MovieId = (int)getIntent().getExtras().get("Movieid");

        MovieIdString = String.valueOf(MovieId);


        MovieAPI = MovieAPIwebURL+MovieIdString+MovieAPIwebURL2+KEY+MovieAPIwebURL3;


        ImageView imageView = (ImageView)findViewById(R.id.thumbnailImage);
        TextView nameView = (TextView)findViewById(R.id.MovieNAME);
        TextView dateView = (TextView)findViewById(R.id.MovieDATE);
        TextView ratingView = (TextView)findViewById(R.id.MovieAVGRATING);
        TextView OveriewView = (TextView)findViewById(R.id.OverviewText);

        Glide.with(DetailsActivity.this)
                .load(IMAGEURLW500 +Poster)
                .override(700,700)
                .thumbnail(1f)
                .crossFade()
                .into(imageView);

        Vote_string = Double.toString(Vote);

//        Reverse_Relesing_date = new StringBuilder(Relesing_date).reverse().toString();

        nameView.setText(Original_title);
        dateView.setText(Relesing_date);
        ratingView.setText(getResources().getText(R.string.Average_rating) +": "+Vote_string);
        OveriewView.setText(Overview);



            MovieInfo movieInfo = new MovieInfo();
            movieInfo.execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detailsactivity,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.playtraile:


                YOUTUBE_TrailerLink = YOUTUBE+VideoLinkKey;

                Toast.makeText(DetailsActivity.this,YOUTUBE_TrailerLink,Toast.LENGTH_LONG).show();

                Uri webpage = Uri.parse(YOUTUBE_TrailerLink);

                Intent intent = new Intent(Intent.ACTION_VIEW , webpage);

                startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    private class MovieInfo extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... params) {

            URL url = null;

            url = createURL(MovieAPI);

            String jsonReponce = "";

            try{
                jsonReponce = makeHttpRequest(url);
            } catch (IOException e){
                e.printStackTrace();
            }


            if(jsonReponce.isEmpty()){
                return null;
            }



            try {
                    JSONObject jsonObject = new JSONObject(jsonReponce);
                    JSONArray  jsonresults = jsonObject.getJSONArray("results");

                    if(jsonresults.length() > 0){

                        for (int i=0; jsonresults.length()>i;i++){

                            JSONObject NumberOfObject = jsonresults.getJSONObject(i);


                            String VideoLinktype = NumberOfObject.getString("type");


                            Log.e("VideoLinktype",VideoLinktype);

                            if(VideoLinktype.equals("Trailer")){

                                VideoLinkKey = NumberOfObject.getString("key");
                                Log.e("VideoLinkKey",VideoLinkKey);

                                break;


                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            return null;
        }




        private URL createURL(String stringurl){

            URL url = null;

            try {
                url = new URL(stringurl);
            } catch (MalformedURLException e) {
                Log.e("createURL","Error with create URL");
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonReponse = "";

            if(url == null){
                return jsonReponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try{

                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if ((urlConnection.getResponseCode() == 200)){
                    inputStream = urlConnection.getInputStream();
                    jsonReponse = readFromStream(inputStream);
                }else {
                    Log.e("makeHttpRequest","error in makeHttpRequest");
                }
            }
            catch (IOException e){

            }
            finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }

            return jsonReponse;
        }

        private String readFromStream(InputStream inputStream) throws IOException {

            StringBuilder builder = new StringBuilder();

            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader readre = new BufferedReader(inputStreamReader);
                String line = readre.readLine();
                while (line != null){
                    builder.append(line);
                    line = readre.readLine();
                }
            }


            return builder.toString();
        }
    }



}