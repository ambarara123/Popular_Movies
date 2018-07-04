package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.data.FavouriteDatabase;
import com.example.popularmovies.data.FavouriteEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteDetail extends AppCompatActivity {

    TextView movieTitle;
    TextView movieDescription;
    TextView movieRating;
    TextView movieReleaseDate;
    ImageView moviePoster;
    ImageView favButton;
    FavouriteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);
        movieTitle = (TextView) findViewById(R.id.txtTitlefav);
        movieDescription = (TextView) findViewById(R.id.txtDescriptionfav);
        movieRating = (TextView) findViewById(R.id.txtUserRatingfav);
        movieReleaseDate = (TextView) findViewById(R.id.txtReleaseDatefav);
        moviePoster = (ImageView) findViewById(R.id.detailImageViewfav);
        favButton = (ImageView) findViewById(R.id.favButtonfav);

        database = FavouriteDatabase.getInstance(this);

        Intent intent = getIntent();
        final int movie_id = intent.getIntExtra("movie_id",0);
        final String title = intent.getStringExtra("title");
        final String image = intent.getStringExtra("image_url");
        final String description = intent.getStringExtra("description");
        final String date = intent.getStringExtra("date");
        final String rating = intent.getStringExtra("rating");


        Picasso.get().load(image).into(moviePoster);
        movieTitle.setText(title);
        movieDescription.setText(description);
        movieReleaseDate.setText(date);
        movieRating.setText(rating + getString(R.string.fullVote));


        //executors for running database queries not in main thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                final int FAV_ID;
                if (database.favouriteDao().getFavByMovieID(movie_id).isEmpty()) {
                    FAV_ID = 0;

                } else {

                    FAV_ID = 1;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (FAV_ID == 0) {
                            favButton.setImageResource(R.drawable.favorite_off);
                        } else if (FAV_ID == 1) {
                            favButton.setImageResource(R.drawable.favorite_on);
                        }
                    }
                });
            }
        });


        //fav button click listener
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FavouriteEntity favouriteEntity = new FavouriteEntity(movie_id, title, image,description
                ,date,rating);

                //executing database operations not in main thered and then update the ui thread
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //if movieId is not in the database insert it
                        if (database.favouriteDao().getFavByMovieID(movie_id).isEmpty()) {
                            database.favouriteDao().insertFav(favouriteEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favButton.setImageResource(R.drawable.favorite_on);
                                    Toast.makeText(FavouriteDetail.this, "added to favourites", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }else {
                            database.favouriteDao().deleteFavById(movie_id);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favButton.setImageResource(R.drawable.favorite_off);
                                    Toast.makeText(FavouriteDetail.this, "Deleted from Favourites", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });




            }
        });





    }
}
