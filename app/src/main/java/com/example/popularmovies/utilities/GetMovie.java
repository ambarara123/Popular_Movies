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

public class GetMovie extends AsyncTask<String, Void,String> {
    private Context context;

    public GetMovie(Context context) {
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        MainActivity.gridRecyclerView.setVisibility(View.INVISIBLE);
        MainActivity.progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... URLs) {
        HttpURLConnection connection = null;

        BufferedReader reader = null;

        try {
            URL url = new URL(URLs[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "/n");
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
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
        MainActivity.progressBar.setVisibility(View.GONE);
        MainActivity.gridRecyclerView.setVisibility(View.VISIBLE);
        //to be removed after completing project
        Log.d("result", result);

        if (MainActivity.connection) {
            MainActivity.moviesList = new ArrayList<>();
            MainActivity.images = new ArrayList<>();

            try {
                JSONObject movieObject = new JSONObject(result);
                JSONArray moviesArray = movieObject.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject JSONMovie = moviesArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setOriginalTitle(JSONMovie.getString("original_title"));
                    movie.setOverview(JSONMovie.getString("overview"));
                    movie.setReleaseDate(JSONMovie.getString("release_date"));
                    movie.setVoteAverage(JSONMovie.getString("vote_average"));
                    movie.setPosterPath(JSONMovie.getString("poster_path"));

                    MainActivity.images.add(Utils.IMAGE_URL +
                            Utils.IMAGE_SIZE +
                            JSONMovie.getString("poster_path"));

                    MainActivity.moviesList.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //notify dataset changed
            MainActivity.recyclerAdapter.notifyDataSetChanged();
        }
        //if no network found
        else {

            Toast.makeText(context, "Please Connect to a Network", Toast.LENGTH_LONG).show();
        }
    }
}

   /* //using volley instead of assync task to avoid small problems
    StringRequest request = new StringRequest(url, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            MainActivity.progressBar.setVisibility(View.GONE);
            MainActivity.gridRecyclerView.setVisibility(View.VISIBLE);
            //to be removed after completing project
            Log.d("result",response);

            if (MainActivity.connection) {
                MainActivity.moviesList = new ArrayList<>();
                MainActivity.images = new ArrayList<>();

                try {
                    JSONObject movieObject = new JSONObject(response);
                    JSONArray moviesArray = movieObject.getJSONArray("results");

                    for (int i = 0; i < moviesArray.length(); i++) {
                        JSONObject JSONMovie = moviesArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setOriginalTitle(JSONMovie.getString("original_title"));
                        movie.setOverview(JSONMovie.getString("overview"));
                        movie.setReleaseDate(JSONMovie.getString("release_date"));
                        movie.setVoteAverage(JSONMovie.getString("vote_average"));
                        movie.setPosterPath(JSONMovie.getString("poster_path"));

                        MainActivity.images.add(Utils.IMAGE_URL +
                                Utils.IMAGE_SIZE +
                                JSONMovie.getString("poster_path"));

                        MainActivity.moviesList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //notify dataset changed
                MainActivity.recyclerAdapter.notifyDataSetChanged();
            }
            //if no network found
            else{

                Toast.makeText(context, "Please Connect to a Network", Toast.LENGTH_LONG).show();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
        }
    });
*/


