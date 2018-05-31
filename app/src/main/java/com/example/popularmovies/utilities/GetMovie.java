package com.example.popularmovies.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.popularmovies.MainActivity;
import com.example.popularmovies.Movie;

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

public class GetMovie extends AsyncTask<String , Void , String> {
    private Context context;

    public GetMovie(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        MainActivity.gridRecyclerView.setVisibility(View.INVISIBLE);
        MainActivity.mProgressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... URLs) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(URLs[0]);
            connection =(HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine() )!= null){
                buffer.append(line +"/n");
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
            try {
                if (reader!= null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        MainActivity.mProgressBar.setVisibility(View.GONE);
        MainActivity.gridRecyclerView.setVisibility(View.VISIBLE);
        Log.d("result",result);


        if (MainActivity.connectionEnabled) {
            MainActivity.moviesList = new ArrayList<>();
            MainActivity.images = new ArrayList<>();

            try {
                JSONObject moviesObject = new JSONObject(result);
                JSONArray moviesArray = moviesObject.getJSONArray("results");
                final int numberOfMovies = moviesArray.length();

                for (int i = 0; i < numberOfMovies; i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    Movie movieItem = new Movie();
                    movieItem.setTitle(movie.getString("title"));
                    movieItem.setId(movie.getInt("id"));
                    movieItem.setBackdropPath(movie.getString("backdrop_path"));
                    movieItem.setOriginalTitle(movie.getString("original_title"));
                    movieItem.setOriginalLanguage(movie.getString("original_language"));

                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setReleaseDate("Unknown Release Date");
                    } else {
                        movieItem.setReleaseDate(movie.getString("release_date"));
                    }
                    movieItem.setPopularity(movie.getString("popularity"));
                    movieItem.setVoteAverage(movie.getString("vote_average"));
                    movieItem.setPosterPath(movie.getString("poster_path"));
                    if (movie.getString("poster_path") == "null") {
                        MainActivity.images.add(APIStrings.IMAGE_NOT_FOUND);
                        movieItem.setPosterPath(APIStrings.IMAGE_NOT_FOUND);
                    } else {
                        MainActivity.images.add(APIStrings.IMAGE_URL + APIStrings.IMAGE_SIZE_185 + movie.getString("poster_path"));
                    }
                    MainActivity.moviesList.add(movieItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MainActivity.recyclerAdapter.notifyDataSetChanged();
        }
        else{

            //network is not working
            Toast.makeText(context, "Please Connect to a Network", Toast.LENGTH_LONG).show();
        }
    }
}
