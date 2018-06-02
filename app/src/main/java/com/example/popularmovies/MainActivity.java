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

import com.example.popularmovies.utilities.GetMovie;
import com.example.popularmovies.utilities.UTILs;

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
            GetMovie movieDownload = new GetMovie(this);
            movieDownload.execute(UTILs.API_URL + UTILs.API_KEY);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2
                    , LinearLayoutManager.VERTICAL, false);

            gridRecyclerView.setLayoutManager(gridLayoutManager);
            gridRecyclerView.setHasFixedSize(true);

            gridRecyclerView.setAdapter(recyclerAdapter);

        } else {
            //TODO[1]: to cancel previous toast to display new one
            Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();
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
                new GetMovie(getApplicationContext()).execute(UTILs.MOST_POPULAR + UTILs.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.acrion_highestrated) {
            //highest rated coding will be done here
            if (isNetworkAvailable() != false) {
                new GetMovie(getApplicationContext()).execute(UTILs.HIGH_RATED + UTILs.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();

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

}
