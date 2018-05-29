package com.example.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView gridRecyclerView;

    private RecyclerAdapter recyclerAdapter;
    ProgressBar mProgressBar;
    //test case
    int num = 20;

    String testUrl = "https://api.themoviedb.org/3/movie/popular?api_key=7d9d6f64616fc8b37de4e6d7f90d864c&language=en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2
        , LinearLayoutManager.VERTICAL,false);

        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(this,num);

        gridRecyclerView.setAdapter(recyclerAdapter);


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
            return true;
        }else if (id== R.id.acrion_highestrated){
            //highest rated coding will be done here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class Networking extends AsyncTask<String[],Void,String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected String[] doInBackground(String[]... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            showRecyclerView();
        }
    }

    public void showProgress(){
        gridRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

    }
    public void showRecyclerView(){
        gridRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

}
