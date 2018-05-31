package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.utilities.APIStrings;
import com.example.popularmovies.utilities.GetMovie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemListener{
    static public RecyclerView gridRecyclerView;

    static public RecyclerAdapter recyclerAdapter;
    static public ProgressBar mProgressBar;
    static public ArrayList<Movie> moviesList;
    static public ArrayList<String> images;
    public static boolean connectionEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        showProgress();

        moviesList=new ArrayList<>();
        images=new ArrayList<>();

        if (isNetworkAvailable() != false){
            showRecyclerView();
            connectionEnabled = true;
            getJsonData();
            new GetMovie(this);


            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2
                    , LinearLayoutManager.VERTICAL,false);

            gridRecyclerView.setLayoutManager(gridLayoutManager);
            gridRecyclerView.setHasFixedSize(true);

            recyclerAdapter = new RecyclerAdapter(this,moviesList,images);

            gridRecyclerView.setAdapter(recyclerAdapter);

        }else {
            Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();
            connectionEnabled = false;
        }

    }

    private void getJsonData() {
        GetMovie movieDownload = new GetMovie(getApplicationContext());

        try {
                movieDownload.execute(APIStrings.API_URL+APIStrings.API_KEY);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //doing sort menu related stuff

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu item
        getMenuInflater().inflate(R.menu.sort,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //getting id of menu items
        int id = item.getItemId();

        if (id == R.id.action_popular){
            //popular sort coding will done here
            if (isNetworkAvailable()!=false){
                new GetMovie(getApplicationContext()).execute(APIStrings.MOST_POPULAR+APIStrings.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();
            }
        }else if (id== R.id.acrion_highestrated){
            //highest rated coding will be done here
            if (isNetworkAvailable()!=false){
                new GetMovie(getApplicationContext()).execute(APIStrings.HIGH_RATED+ APIStrings.API_KEY);
                //to notify data set changed
                recyclerAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, "Please Connect to a network", Toast.LENGTH_SHORT).show();

            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void showProgress(){
        gridRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
    public void showRecyclerView(){
        gridRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    //to check that network is available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onItemClick(int click) {

        Log.d("Position",String.valueOf(click));

        Intent intent = new Intent(MainActivity.this,MovieActivity.class);

        intent.putExtra("position",click);
        startActivity(intent);
    }

}
