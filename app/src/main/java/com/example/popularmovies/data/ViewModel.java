package com.example.popularmovies.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private LiveData<List<FavouriteEntity>> tasks;
    FavouriteDatabase database;

    public ViewModel(Application application) {
        super(application);
        database = FavouriteDatabase.getInstance(this.getApplication());

        //tasks = database.favouriteDao().getFavourite();
    }


    public LiveData<List<FavouriteEntity>> getFavourite() {
        return tasks;
    }

    public void deleteItem(FavouriteEntity favouriteEntity) {
        new deleteFavourite(database).execute(favouriteEntity);
    }

    private static class deleteFavourite extends AsyncTask<FavouriteEntity, Void, Void> {

        private FavouriteDatabase mDatabase;

        deleteFavourite(FavouriteDatabase database) {
            this.mDatabase = database;
        }

        @Override
        protected Void doInBackground(final FavouriteEntity... params) {

            mDatabase.favouriteDao().deleteFav(params[0]);

            return null;
        }

    }
}
