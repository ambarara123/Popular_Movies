package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.popularmovies.data.FavouriteDatabase;
import com.example.popularmovies.data.FavouriteEntity;
import com.example.popularmovies.utilities.ReviewModel;
import com.example.popularmovies.utilities.TrailerModel;
import com.example.popularmovies.utilities.Utils;
import com.example.popularmovies.utilities.VolleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView movieDescription;
    TextView movieRating;
    TextView movieReleaseDate;
    ImageView moviePoster;
    ImageView favButton;
    int position;
    int movie_id;
    ProgressBar progressBar, progressBar2;


    public static ArrayList<ReviewModel> reviews;
    public static ArrayList<TrailerModel> trailers;

    RecyclerView trailerRecycler, reviewRecycler;
    ReviewRecyclerAdapter reviewAdapter;
    TrailerRecyclerAdapter trailerAdapter;
    String reviewUrl;
    String trailerUrl;

    public static FavouriteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movieTitle = (TextView) findViewById(R.id.txtTitle);
        movieDescription = (TextView) findViewById(R.id.txtDescription);
        movieRating = (TextView) findViewById(R.id.txtUserRating);
        movieReleaseDate = (TextView) findViewById(R.id.txtReleaseDate);
        moviePoster = (ImageView) findViewById(R.id.detailImageView);
        favButton = (ImageView) findViewById(R.id.favButton);
        trailerRecycler = (RecyclerView) findViewById(R.id.trailerRecycler);
        reviewRecycler = (RecyclerView) findViewById(R.id.reviewRecycler);
        progressBar = (ProgressBar) findViewById(R.id.movieProgress);
        progressBar2 = (ProgressBar) findViewById(R.id.movieProgress2);


        reviews = new ArrayList<>();
        trailers = new ArrayList<>();

        reviewAdapter = new ReviewRecyclerAdapter(this, reviews);
        trailerAdapter = new TrailerRecyclerAdapter(this, trailers);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        reviewRecycler.setLayoutManager(linearLayoutManager1);
        trailerRecycler.setLayoutManager(linearLayoutManager2);
        trailerRecycler.setHasFixedSize(true);

        reviewRecycler.setAdapter(reviewAdapter);
        trailerRecycler.setAdapter(trailerAdapter);

        database = FavouriteDatabase.getInstance(this);


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




        //getting intent and clicked position
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        final String title = MainActivity.moviesList.get(position).originalTitle;
        final String overview = MainActivity.moviesList.get(position).overview;
        final String rating = MainActivity.moviesList.get(position).voteAverage;
        final String releaseDate = MainActivity.moviesList.get(position).releaseDate;
        final String image = MainActivity.images.get(position);
        movieTitle.setText(title);
        movieDescription.setText(overview);
        movieRating.setText(rating + getString(R.string.fullVote));
        movieReleaseDate.setText(releaseDate);

        movie_id = MainActivity.moviesList.get(position).id;

        trailerUrl = Utils.MOVIE_URL + movie_id + "/videos?api_key=";
        reviewUrl = Utils.MOVIE_URL + movie_id + "/reviews?api_key=";
        //loading image from url
        Picasso.get().load(MainActivity.images.get(position)).into(moviePoster);



        //fav button click listener
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FavouriteEntity favouriteEntity = new FavouriteEntity(movie_id, title,image, overview, releaseDate, rating );

/*
                if (checkIfIdExist(favouriteEntity) == 0){
                    database.favouriteDao().insertFav(favouriteEntity);
                }else {
                    database.favouriteDao().deleteFav(favouriteEntity);
                }*/


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
                                    Toast.makeText(MovieActivity.this, "added to favourites", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }else {
                            database.favouriteDao().deleteFavById(movie_id);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    favButton.setImageResource(R.drawable.favorite_off);
                                    Toast.makeText(MovieActivity.this, "Deleted from Favourites", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });




            }
        });



        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Please connect to a network", Toast.LENGTH_SHORT).show();
        } else {
            trailerTask(trailerUrl + Utils.API_KEY);
            reviewTask(reviewUrl + Utils.API_KEY);

        }

    }

    //for setting up trailers
    public void trailerTask(String url) {
        progressBar.setVisibility(View.VISIBLE);
        trailerRecycler.setVisibility(View.INVISIBLE);

        try {

            JsonObjectRequest request = new JsonObjectRequest(
                    url,
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            trailerRecycler.setVisibility(View.VISIBLE);
                            try {
                                JSONArray results = response.getJSONArray("results");

                                /*int length = results.length();
                                ArrayList<TrailerModel> trailers = new ArrayList<>(length);*/
                                Log.d("TrailerResponse", response.toString());

                                for (int i = 0; i < results.length(); i++) {
                                    String key = results.getJSONObject(i).getString("key");
                                    String name = results.getJSONObject(i).getString("name");
                                    TrailerModel trailerModel = new TrailerModel();
                                    trailerModel.setTrailerName(name);
                                    trailerModel.setValue(key);

                                    trailers.add(trailerModel);
                                }
                                //updating adapter with new data
                                trailerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MovieActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
            );

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request, "trailer");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //getting reviews and reviewer name
    public void reviewTask(String url) {
        progressBar2.setVisibility(View.VISIBLE);
        reviewRecycler.setVisibility(View.INVISIBLE);

        try {

            JsonObjectRequest request = new JsonObjectRequest(
                    url,
                    null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar2.setVisibility(View.INVISIBLE);
                            reviewRecycler.setVisibility(View.VISIBLE);
                            try {
                                JSONArray results = response.getJSONArray("results");

                                Log.d("ReviewResponse", response.toString());

                                for (int i = 0; i < results.length(); i++) {
                                    String author = results.getJSONObject(i).getString("author");
                                    String content = results.getJSONObject(i).getString("content");
                                    ReviewModel reviewModel = new ReviewModel();
                                    reviewModel.setReviewerName(author);
                                    reviewModel.setReviewDetail(content);

                                    reviews.add(reviewModel);
                                }


                                reviewAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar2.setVisibility(View.INVISIBLE);
                            Toast.makeText(MovieActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request, "review");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //to check that network is available or not
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}