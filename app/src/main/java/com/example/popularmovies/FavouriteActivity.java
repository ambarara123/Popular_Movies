package com.example.popularmovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.popularmovies.data.FavouriteEntity;
import com.example.popularmovies.data.ViewModel;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView favRecycler;
    private FavouriteAdapter favAdapter;
    GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        int spanCount = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
        gridLayoutManager = new GridLayoutManager(this, spanCount, LinearLayoutManager.VERTICAL, false);


        favRecycler = (RecyclerView) findViewById(R.id.favRecyclerView);

        favRecycler.setLayoutManager(gridLayoutManager);

        favAdapter = new FavouriteAdapter(this);
        favRecycler.setAdapter(favAdapter);

        setViewModel();

    }



    @Override
    protected void onResume() {
        super.onResume();

//        Log.d("favlist",String.valueOf(database.favouriteDao().getFavourite().get(0).getMovieId()));
    }

    //setting view model
    public void setViewModel() {
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getFavourite().observe(this, new Observer<List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> favouriteEntities) {
                Log.d("viewmodel","live data FROM viewmodel");
                favAdapter.setFavList(favouriteEntities);

            }
        });
    }

}
