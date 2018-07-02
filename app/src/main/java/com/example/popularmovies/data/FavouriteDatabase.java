package com.example.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavouriteEntity.class},version = 2,exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favouritedata.db";
    private static FavouriteDatabase instance;

    public static FavouriteDatabase getInstance(Context context) {
        if (instance == null) {

            synchronized (LOCK) {

                instance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteDatabase.class,
                        FavouriteDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        //to know which class is instantiating database
        Log.d("database","getting database instance");

        return instance;
    }

    public abstract FavouriteDao favouriteDao();

}
