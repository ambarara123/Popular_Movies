package com.example.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {



    public static String detailUrl = "https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US";

    String example = "https://api.themoviedb.org/3/movie/popular?api_key=7d9d6f64616fc8b37de4e6d7f90d864c&language=en-US&page=1";






    //http connection for the app
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
