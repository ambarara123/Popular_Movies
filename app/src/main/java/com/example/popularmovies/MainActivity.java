package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.popularmovies.utilities.GetMovie;
import com.example.popularmovies.utilities.UTILs;
import com.example.popularmovies.utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemListener {

    //declaring variables
    public static RecyclerView gridRecyclerView;
    public static RecyclerAdapter recyclerAdapter;
    public static ProgressBar progressBar;
    public static ArrayList<Movie> moviesList;
    public static ArrayList<String> images;
    public static boolean connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        moviesList = new ArrayList<>();
        images = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapter(this, moviesList, images);
        showProgress();




        //if no network problem
        if (isNetworkAvailable() != false) {
            showRecyclerView();
            connection = true;
            //
           // GetMovie movieDownload = new GetMovie(this);
           // movieDownload.execute(UTILs.API_URL + UTILs.API_KEY);
            volleyRequest(getApplicationContext(),UTILs.API_URL+UTILs.API_KEY+"&language=en-US");


            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2
                    , LinearLayoutManager.VERTICAL, false);

            gridRecyclerView.setLayoutManager(gridLayoutManager);
            gridRecyclerView.setHasFixedSize(true);

            gridRecyclerView.setAdapter(recyclerAdapter);

        } else {

            noNetworkToast();
            connection = false;
        }

    }


    //doing sort menu related stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu item
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //getting id of menu items
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            //popular sort coding will done here
            if (isNetworkAvailable() != false) {
                //new GetMovie(getApplicationContext()).execute(UTILs.MOST_POPULAR + UTILs.API_KEY);
                volleyRequest(getApplicationContext(),UTILs.MOST_POPULAR+UTILs.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            } else {
                noNetworkToast();
            }
        } else if (id == R.id.acrion_highestrated) {
            //highest rated coding will be done here
            if (isNetworkAvailable() != false) {
               // new GetMovie(getApplicationContext()).execute(UTILs.HIGH_RATED+UTILs.API_KEY);
                volleyRequest(getApplicationContext(),UTILs.HIGH_RATED+UTILs.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            } else {

               noNetworkToast();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void showProgress() {
        gridRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        gridRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    //to check that network is available or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onItemClick(int click) {

        Log.d("Position", String.valueOf(click));
        //going to movie activity
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);

        intent.putExtra("position", click);

        startActivity(intent);
    }


    public void noNetworkToast(){

        Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();

    }


    public void volleyRequest(final Context context,String url){
        //using volley instead of assync task to avoid small problems
        progressBar.setVisibility(View.VISIBLE);
        gridRecyclerView.setVisibility(View.INVISIBLE);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                MainActivity.progressBar.setVisibility(View.INVISIBLE);
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
                            movie.setId(JSONMovie.getInt("id"));
                            movie.setOriginalTitle(JSONMovie.getString("original_title"));
                            movie.setOverview(JSONMovie.getString("overview"));
                            movie.setReleaseDate(JSONMovie.getString("release_date"));
                            movie.setVoteAverage(JSONMovie.getString("vote_average"));
                            movie.setPosterPath(JSONMovie.getString("poster_path"));


                            MainActivity.images.add(UTILs.IMAGE_URL +
                                    UTILs.IMAGE_SIZE +
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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request,"req");
    }

}
