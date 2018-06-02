package com.example.popularmovies;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MovieActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieDescription;
    TextView movieRating;
    TextView movieReleaseDate;
    ImageView moviePoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movieTitle = (TextView) findViewById(R.id.txtTitle);
        movieDescription = (TextView) findViewById(R.id.txtDescription);
        movieRating = (TextView) findViewById(R.id.txtUserRating);
        movieReleaseDate = (TextView) findViewById(R.id.txtReleaseDate);
        moviePoster = (ImageView) findViewById(R.id.detailImageView);

        //getting intent and clicked position
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        movieTitle.setText(MainActivity.moviesList.get(position).originalTitle);
        movieDescription.setText(MainActivity.moviesList.get(position).overview);
        movieRating.setText(MainActivity.moviesList.get(position).voteAverage + getString(R.string.fullVote));
        movieReleaseDate.setText(MainActivity.moviesList.get(position).releaseDate);
        //loading image from url
        Picasso.get().load(MainActivity.images.get(position)).into(moviePoster);

    }

}
