package com.example.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.popularmovies.data.FavouriteDatabase;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView favRecycler;
    private FavouriteAdapter favAdapter;
    private FavouriteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favRecycler = (RecyclerView) findViewById(R.id.favRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        favRecycler.setLayoutManager(layoutManager);

        favAdapter = new FavouriteAdapter(this);
        favRecycler.setAdapter(favAdapter);

        database = FavouriteDatabase.getInstance(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        favAdapter.setFavList(database.favouriteDao().getFavourite());
//        Log.d("favlist",String.valueOf(database.favouriteDao().getFavourite().get(0).getMovieId()));
    }
}
