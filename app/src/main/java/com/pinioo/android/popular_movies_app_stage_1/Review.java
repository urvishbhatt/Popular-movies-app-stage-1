package com.pinioo.android.popular_movies_app_stage_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bhatt on 22-05-2017.
 */

public class Review extends Fragment {


    String MovieAPIwebURL = "https://api.themoviedb.org/3/movie/";
    String KEY = "382a81cb81a8ab80eb5f89325e2095d3";
    String MovieAPIwebURL3 = "&language=en-US";
    String MovieAPIwebURL2Review = "/reviews?api_key=";
    int MovieId;
    String MovieIdString;

    public String ReviewAPI;

    ArrayList<MovieReview> ReviewList = new ArrayList<>();
    ArrayList<String> DatabaseAuthore = new ArrayList<>();
    ArrayList<String> DatabaseReview = new ArrayList<>();



    static String DatabaseAuthoreString;
    static String DatabaseReviewString;


    static String DatabasejsonReponce;


    public Review(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieId = (int)getActivity().getIntent().getExtras().get("Movieid");
        MovieIdString = String.valueOf(MovieId);

        ReviewAPI = MovieAPIwebURL+MovieIdString+MovieAPIwebURL2Review+KEY+MovieAPIwebURL3;

        MovieInfo2 movieInfo2 = new MovieInfo2();
        movieInfo2.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.review, container, false);
    }

    private void UpdateUI(ArrayList<MovieReview> ReviewList){

        ListAdapter adpater = new ListAdpater(getContext(),ReviewList);

        ListView listView = (ListView)getView().findViewById(R.id.reviewList);

        listView.setAdapter(adpater);

    }

    private class MovieInfo2 extends AsyncTask<URL, Void, ArrayList<MovieReview>> {

        @Override
        protected ArrayList<MovieReview> doInBackground(URL... params) {

            URL url = null;

            url = createURL(ReviewAPI);

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
                DatabasejsonReponce = jsonReponce;

                JSONObject jsonObject = new JSONObject(jsonReponce);
                JSONArray jsonresults = jsonObject.getJSONArray("results");

                if(jsonresults.length() > 0){

                    for (int i=0; jsonresults.length()>i;i++){

                        JSONObject NumberOfObject = jsonresults.getJSONObject(i);

                        String ReviewAuthor = NumberOfObject.getString("author");
                        String ReviewText = NumberOfObject.getString("content");

                        DatabaseAuthore.add(ReviewAuthor);
                        DatabaseReview.add(ReviewText);


                        Log.e("ReviewAuthor",ReviewAuthor);
                        Log.e("ReviewText",ReviewText);

                        ReviewList.add(new MovieReview(ReviewAuthor,ReviewText));

                    }
                }

                String[] DatabaseAuthoreStringArray = DatabaseAuthore.toArray(new String[DatabaseAuthore.size()]);
                String[] DatabaseReviewStringArray = DatabaseReview.toArray(new String[DatabaseReview.size()]);

                DatabaseAuthoreString = Arrays.toString(DatabaseAuthoreStringArray);
                DatabaseReviewString = Arrays.toString(DatabaseReviewStringArray);

                Log.e("DatabaseAuthoreString",DatabaseAuthoreString);
                Log.e("DatabaseReviewString",DatabaseReviewString);


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return ReviewList;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieReview> movieReviews) {
            if(movieReviews == null){
                return;
            }
            UpdateUI(movieReviews);

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

    public void saveDataReview(){


        new FavoriteMovieData(DatabasejsonReponce);
    }




}
