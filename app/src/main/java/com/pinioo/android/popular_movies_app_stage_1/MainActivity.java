package com.pinioo.android.popular_movies_app_stage_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.StreamAssetPathFetcher;
import com.pinioo.android.popular_movies_app_stage_1.MovieData;

import static android.support.v7.appcompat.R.styleable.ActionBar;
import static android.support.v7.appcompat.R.styleable.Toolbar_android_gravity;
import static android.support.v7.appcompat.R.styleable.View;


public class MainActivity extends AppCompatActivity {



    private final static String API_KEY = "";
    private final static String DISCOVERY_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1";
    private final static String DISCOVERY_URL_RATING = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&language=en-US&page=1";


    public String DISCOVERY_FUNCATION;

    GridView gridView = null;
    ImageView imageView = null;
    ArrayList<MovieData> MovieDataArray = new ArrayList<>();
    Adpater adpater;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridview);
        imageView = (ImageView)findViewById(R.id.NOConnectionID);

        /*************/
        Drawable drawable  = getResources().getDrawable(R.drawable.no_internet_connection);
        /*************/

        DISCOVERY_FUNCATION = DISCOVERY_URL_POPULAR;

        if(isNetworkStatusAvialable (getApplicationContext())) {

            Toast.makeText(MainActivity.this,getResources().getText(R.string.Most_popular_movies),Toast.LENGTH_LONG).show();
            gridView.setVisibility(android.view.View.VISIBLE);
            MovieAsyncTask task = new MovieAsyncTask();
            task.execute();

        } else {

            gridView.setVisibility(android.view.View.INVISIBLE);
            Toast.makeText(MainActivity.this,getResources().getText(R.string.NO_Internet),Toast.LENGTH_LONG).show();
            imageView.setImageDrawable(drawable);

        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);

                String original_title = MovieDataArray.get(position).getMovieName();
                String poster = MovieDataArray.get(position).getMovieImage();
                String overview = MovieDataArray.get(position).getMovieOverview();
                double vote = MovieDataArray.get(position).getMovieRating();
                String relesing_date = MovieDataArray.get(position).getMovieRelesing_Date();
                int Movieid = MovieDataArray.get(position).getMovieId();

                intent.putExtra("original_title",original_title);
                intent.putExtra("poster",poster);
                intent.putExtra("overview",overview);
                intent.putExtra("vote",vote);
                intent.putExtra("relesing_date",relesing_date);
                intent.putExtra("Movieid",Movieid);
                startActivity(intent);
            }
        });

    }
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort,menu);

        MenuItem item = (MenuItem) menu.findItem(R.id.Popular);
        item.setVisible(false);

        this.menu = menu;

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuItem PopularMENU = (MenuItem) menu.findItem(R.id.Popular);
        MenuItem RatingMENU = (MenuItem) menu.findItem(R.id.Rating);
        MenuItem RefreshMenu = (MenuItem) menu.findItem(R.id.Refresh);
        MenuItem FavoriteMenu = (MenuItem)menu.findItem(R.id.Favorite);

        switch (item.getItemId()){

            case R.id.Rating:

                if(isNetworkStatusAvialable (getApplicationContext())) {

                    if(gridView.getVisibility() == android.view.View.INVISIBLE){

                        gridView.setVisibility(android.view.View.VISIBLE);
                        imageView.setVisibility(android.view.View.INVISIBLE);

                    }

                    PopularMENU.setVisible(true);
                    RatingMENU.setVisible(false);
                    FavoriteMenu.setVisible(true);
                    RefreshMenu.setVisible(true);

                    DISCOVERY_FUNCATION = DISCOVERY_URL_RATING;

                    if(adpater != null){
                        MovieDataArray.clear();
                        adpater.clear();
                        adpater.notifyDataSetChanged();
                        Log.e("Rating","Rating");
                    }

                    MovieAsyncTask task = new MovieAsyncTask();
                    task.execute();

                    Toast.makeText(MainActivity.this,getResources().getText(R.string.Highest_rated_movies),Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(MainActivity.this,getResources().getText(R.string.No_internet_connection),Toast.LENGTH_LONG).show();

                }

                return true;

            case R.id.Popular:

                if(isNetworkStatusAvialable (getApplicationContext())) {

                    if(gridView.getVisibility() == android.view.View.INVISIBLE){

                        gridView.setVisibility(android.view.View.VISIBLE);
                        imageView.setVisibility(android.view.View.INVISIBLE);

                    }

                    PopularMENU.setVisible(false);
                    RatingMENU.setVisible(true);
                    FavoriteMenu.setVisible(true);
                    RefreshMenu.setVisible(true);

                    DISCOVERY_FUNCATION = DISCOVERY_URL_POPULAR;

                    if(adpater != null){
                        MovieDataArray.clear();
                        adpater.clear();
                        adpater.notifyDataSetChanged();
                        Log.e("Popular","Popular");
                    }

                    MovieAsyncTask task1 = new MovieAsyncTask();
                    task1.execute();

                    Toast.makeText(MainActivity.this,getResources().getText(R.string.Most_popular_movies),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.Favorite:

                RefreshMenu.setVisible(false);
                FavoriteMenu.setVisible(false);
                PopularMENU.setVisible(true);
                RatingMENU.setVisible(true);



                return true;


            case R.id.Refresh:

                if(isNetworkStatusAvialable (getApplicationContext())) {

                    if(gridView.getVisibility() == android.view.View.INVISIBLE){

                        gridView.setVisibility(android.view.View.VISIBLE);
                        imageView.setVisibility(android.view.View.INVISIBLE);

                    }
                    if(adpater != null){
                        MovieDataArray.clear();
                        adpater.clear();
                        adpater.notifyDataSetChanged();
                    }

                    MovieAsyncTask task2 = new MovieAsyncTask();
                    task2.execute();


                } else {

                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(ArrayList<MovieData> MovieDataArray) {

        adpater = new Adpater(this,MovieDataArray);

        GridView gridView = (GridView)findViewById(R.id.gridview);

        gridView.setAdapter(adpater);
    }

    private class MovieAsyncTask extends AsyncTask<URL, Void ,ArrayList<MovieData>>{

        @Override
        protected ArrayList<MovieData> doInBackground(URL... params) {

            URL url = null;

            try {
                url = new URL(DISCOVERY_FUNCATION);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String json = null;

            try {
                json = httprequest(url);

                Log.v("PROBLEM",json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<MovieData> MovieDataArray=null;

            try {
                MovieDataArray = getJSONdata(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return MovieDataArray;
        }


        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData) {
            if(movieData == null){
                return;
            }
            updateUI(movieData);
        }

        private ArrayList<MovieData> getJSONdata(String json) throws JSONException {

            if (TextUtils.isEmpty(json)) {
                return null;
            }

            JSONObject FirstOBJ = new JSONObject(json);
            JSONArray FirstARRAY = FirstOBJ.getJSONArray("results");

            if (FirstARRAY.length() > 0) {

                for (int i = 0; FirstARRAY.length() > i; i++) {

                    JSONObject secondOBJ = FirstARRAY.getJSONObject(i);

                    String Moviename = secondOBJ.getString("original_title");
                    String MoviePoster = secondOBJ.getString("poster_path");
                    double MovieRating = secondOBJ.getDouble("vote_average");
                    String MovieOverview = secondOBJ.getString("overview");
                    String MovieRelesingDate = secondOBJ.getString("release_date");
                    int MovieId = secondOBJ.getInt("id");

                    int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

                    MovieDataArray.add(new MovieData(Moviename,MoviePoster,MovieRating,MovieOverview,MovieRelesingDate,MovieId,randomAndroidColor));

                    Log.e("Moviename=",Moviename);
                    Log.e("MoviePoster=",MoviePoster);
                    Log.e("MovieRating=",String.valueOf(MovieRating));
                    Log.e("MovieOverview=",MovieOverview);
                    Log.e("MovieRelesingDate",MovieRelesingDate);
                    Log.e("movieId",String.valueOf(MovieId));
                }
            }

            return MovieDataArray;
        }


        private String httprequest(URL url) throws IOException {

            String jsonResponce = "";

            if(url == null){
                return null;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try{
               urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if(urlConnection.getResponseCode()==200){
                    inputStream = urlConnection.getInputStream();

                    jsonResponce = readFromStream(inputStream);
                }
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if ( urlConnection != null){
                    urlConnection.disconnect();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }
            return jsonResponce;
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