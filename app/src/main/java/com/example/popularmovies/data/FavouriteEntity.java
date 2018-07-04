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
    String description;
    String releaseDate;
    String voteAverage;

    @Ignore
    public FavouriteEntity(int movieId, String moviename, String image, String description, String releaseDate, String voteAverage) {
        this.movieId = movieId;
        this.moviename = moviename;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public FavouriteEntity(int id, int movieId, String moviename, String image, String description, String releaseDate, String voteAverage) {
        this.id = id;
        this.movieId = movieId;
        this.moviename = moviename;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
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
