package com.example.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favourite")
public class FavouriteEntity {

    @PrimaryKey (autoGenerate = true)
    int id;
    @ColumnInfo(name = "movie_id")
    int movieId;
    String moviename;
    String image;

    @Ignore
    public FavouriteEntity(int movieId, String moviename, String image) {
        this.movieId = movieId;
        this.moviename = moviename;
        this.image = image;
    }

    public FavouriteEntity(int id, int movieId, String moviename, String image) {
        this.id = id;
        this.movieId = movieId;
        this.moviename = moviename;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }
}
