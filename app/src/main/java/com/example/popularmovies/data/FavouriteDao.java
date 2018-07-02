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
    @Query("SELECT * FROM favourite")
    LiveData<List<FavouriteEntity>> getFavourite();

    @Insert
    void insertFav(FavouriteEntity favouriteEntity);

    @Delete
    void deleteFav(FavouriteEntity favouriteEntity);

    @Update(onConflict = REPLACE)
    void updateFav(FavouriteEntity favouriteEntity);

    @Query("SELECT * FROM favourite WHERE movie_id = :id")
    List<FavouriteEntity> getFavByID(int id);

    @Query("DELETE FROM favourite WHERE movie_id =:id")
    void deleteFavById(int id);
}
