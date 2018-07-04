package com.example.popularmovies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavouriteDao {
    //getting live data list from this
    @Query("SELECT * FROM favourite")
    LiveData<List<FavouriteEntity>> getFavourite();

    @Insert
    void insertFav(FavouriteEntity favouriteEntity);

    @Delete
    void deleteFav(FavouriteEntity favouriteEntity);

    @Update(onConflict = REPLACE)
    void updateFav(FavouriteEntity favouriteEntity);
    //get row of specific movie id
    @Query("SELECT * FROM favourite WHERE movie_id = :id")
    List<FavouriteEntity> getFavByMovieID(int id);
    //to delete row of specific movie_id

    @Query("DELETE FROM favourite WHERE movie_id =:id")
    void deleteFavById(int id);
    //get row of id
    @Query("SELECT * FROM favourite WHERE id = :id")
    List<FavouriteEntity> getFavouriteById(int id);
}
