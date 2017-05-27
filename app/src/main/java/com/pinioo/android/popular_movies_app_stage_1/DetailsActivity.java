package com.pinioo.android.popular_movies_app_stage_1;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.pinioo.android.popular_movies_app_stage_1.Database.MovieContract;
import com.pinioo.android.popular_movies_app_stage_1.Database.MovieSQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {


    String MovieAPI;

    String MovieAPIwebURL = "https://api.themoviedb.org/3/movie/";
    String MovieAPIwebURL2 = "/videos?api_key=";
    String KEY = "";
    String MovieAPIwebURL3 = "&language=en-US";


    String VideoLinkKey;

    String YOUTUBE = "http://youtube.com/watch?v=";
    String YOUTUBE_TrailerLink = "";

    String ReviewAPI;
    String MovieAPIwebURL2Review = "/reviews?api_key=";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton floatingActionButton;


    int MovieId;
    String MovieIdString;

    String Original_title;
    String Poster;
    String Overview;
    double Vote;
    String Vote_string;
    String Relasing_date;
    Bitmap ImageBitmap;

    String jsonReponceString;

    boolean isfromfavoritedatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.FloatingActionButton);

        MyParcelable object = (MyParcelable) getIntent().getParcelableExtra("myData");

        isfromfavoritedatabase = (boolean)getIntent().getExtras().get("isFromFavorite");

        MovieId = object.Movieid;

        MovieIdString = String.valueOf(MovieId);
        MovieAPI = MovieAPIwebURL+MovieIdString+MovieAPIwebURL2+KEY+MovieAPIwebURL3;
        ReviewAPI = MovieAPIwebURL+MovieIdString+MovieAPIwebURL2Review+KEY+MovieAPIwebURL3;

        MovieInfo movieInfo = new MovieInfo();
        movieInfo.execute();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*****************************************************************/

        floatingbuttongraphics();



        /***************************************************************************************************/

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Overview overviewObj = new Overview();
                overviewObj.saveDataOverview();

                Review review = new Review();
                review.saveDataReview();

                insertdataintodatabse();
//                getdatafromdatabase();

            }

        });

    }

    private void floatingbuttongraphics() {


        if(isfromfavoritedatabase){

            floatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
            floatingActionButton.setEnabled(false);

        }

        else {

            String[] project = { MovieContract.MovieEntry.MOVIE_JSON_ID };

            Cursor cursor = getContentResolver().query(
                    MovieContract.MovieEntry.CONTENT_URL,
                    project,
                    null,
                    null,
                    null
            );

            try{
                int aa = cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_JSON_ID);

                while(cursor.moveToNext()){

                    int movieid = cursor.getInt(aa);

                    if(movieid == MovieId){

                        floatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
                        floatingActionButton.setEnabled(false);
                        break;
                    }


                }

            }finally {
                cursor.close();
            }

        }

    }

//    private void getdatafromdatabase(){
//
//        String[] project = {
//                MovieContract.MovieEntry.MOVIE_JSON_ID,
//                MovieContract.MovieEntry.COLUMN_MOVIE_NAME,
//                MovieContract.MovieEntry.RATING,
//                MovieContract.MovieEntry.DATE,
//                MovieContract.MovieEntry.OVERVIEW,
//                MovieContract.MovieEntry.POSTERIMAGE
//        };
//
//        Cursor cursor = getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URL,
//                project,
//                null,
//                null,
//                null
//        );
//
//        try{
//            int aa = cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_JSON_ID);
//            int a = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_NAME);
//            int b = cursor.getColumnIndex(MovieContract.MovieEntry.RATING);
//            int c = cursor.getColumnIndex(MovieContract.MovieEntry.DATE);
//            int d = cursor.getColumnIndex(MovieContract.MovieEntry.OVERVIEW);
//            int e = cursor.getColumnIndex(MovieContract.MovieEntry.POSTERIMAGE);
//
//            while(cursor.moveToNext()){
//                String movieid = cursor.getString(aa);
//                String name = cursor.getString(a);
//                String rating = cursor.getString(b);
//                String date = cursor.getString(c);
//                String overview = cursor.getString(d);
//                byte[] bytes = cursor.getBlob(e);
//
//                Toast.makeText(DetailsActivity.this,"retriving data",Toast.LENGTH_LONG).show();
//            }
//
//        }finally {
//            cursor.close();
//        }
//    }

    private void insertdataintodatabse(){

        Original_title = FavoriteMovieData.getOriginal_title();
        Vote_string = FavoriteMovieData.getVote_string();
        Relasing_date = FavoriteMovieData.getRelesing_date();
        Overview = FavoriteMovieData.getOverview();
        ImageBitmap = FavoriteMovieData.getimageBitmap();

        jsonReponceString = FavoriteMovieData.getDatabasejsonReponceString();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();

        Log.e("Movie_JSON_ID",Integer.toString(MovieId));
        Log.e("Original_title",Original_title.trim());
        Log.e("Vote_string",Vote_string);
        Log.e("Relesing_date",Relasing_date);
        Log.e("Overview",Overview);
        Log.e("jsonReponceString",jsonReponceString);



        ContentValues values = new ContentValues();

        values.put(MovieContract.MovieEntry.MOVIE_JSON_ID,MovieId);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME,Original_title);
        values.put(MovieContract.MovieEntry.RATING,Vote_string);
        values.put(MovieContract.MovieEntry.DATE,Relasing_date);
        values.put(MovieContract.MovieEntry.OVERVIEW,Overview);
        values.put(MovieContract.MovieEntry.POSTERIMAGE,bArray);
        values.put(MovieContract.MovieEntry.REVIEW_JSON,jsonReponceString);


        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URL,values);

        Toast.makeText(DetailsActivity.this,"saving Done",Toast.LENGTH_LONG).show();

        floatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
        floatingActionButton.setEnabled(false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Overview(), "Overview");
        adapter.addFragment(new Review(), "Review");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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



}