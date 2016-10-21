package com.example.vishnuprasadssattigeri.popularmovies;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by vishnuprasadssattigeri on 10/20/16.
 */

public class MoviesData extends AsyncTask<Void, Void, MovieDetail[]> {
    private static final String LOG_TAG = "MyActivity";

    private MovieAdapter movieAdapter;
    private MoviesFragment task = null;

    public MoviesData(MovieAdapter movieAdapter,MoviesFragment activity) {
        this.movieAdapter = movieAdapter;
        task=new MoviesFragment();
        task= activity;
    }

    protected MovieDetail[] doInBackground(Void... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            String moviesJson;

            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(task.getActivity());
            String sortType = sharedPrefs.getString(
                    task.getString(R.string.pref_sort_key),
                    task.getString(R.string.pref_sort_poularity));

            String apiId = null;
            final String FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/"+sortType+"?";
            final String PRIMARY_RELEASE_YEAR = "primary_release_year";
            final String APPID_PARAM = "api_key";

            try {
                ApplicationInfo ai = task.getActivity().getPackageManager().getApplicationInfo(task.getActivity().getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = ai.metaData;
                apiId = bundle.getString("my_movie_api_key");
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
            } catch (NullPointerException e) {
                Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
            }

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, apiId)
                    .build();

            URL url = new URL(builtUri.toString());
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJson = buffer.toString();
            return moviesDataFromJson(moviesJson, 20);

        } catch (JSONException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } catch (java.text.ParseException e) {
            Log.e("PlaceholderFragment", "Error ", e);
        } finally {

        }
        return null;
    }

    public MovieDetail[] moviesDataFromJson(String moviesJson, int noOfMovies)
            throws JSONException, java.text.ParseException {

        MovieDetail[] moviesData = new MovieDetail[noOfMovies];

        final String MOVIES_RESULT = "results";
        JSONObject movieJson = new JSONObject(moviesJson);
        JSONArray moviesArray = movieJson.getJSONArray(MOVIES_RESULT);

        String MOVIE_IMG_PATH = "poster_path";
        String MOVIE_NAME = "original_title";
        String MOVIE_RELEASE_DATE = "release_date";
        String MOVIE_SUMMARY = "overview";
        String MOVIE_RATING = "vote_average";

        for (int i = 0; i < moviesData.length; i++) {

            String movieIMGPath;
            JSONObject movie = moviesArray.getJSONObject(i);
            movieIMGPath = movie.getString(MOVIE_IMG_PATH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = sdf.parse(movie.getString(MOVIE_RELEASE_DATE));

            //Create Movie Object
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setMovieImagePath(movieIMGPath);
            movieDetail.setMovieSynopsis(movie.getString(MOVIE_SUMMARY));
            movieDetail.setMovieTitle(movie.getString(MOVIE_NAME));
            movieDetail.setMovieRating(movie.getDouble(MOVIE_RATING));
            movieDetail.setReleaseDate(releaseDate);

            moviesData[i] = movieDetail;
        }
        return moviesData;
    }

    @Override
    protected void onPostExecute(MovieDetail[] result) {
        //super.onPostExecute(strings);
        if (result != null) {
            movieAdapter.clear();
            for (MovieDetail movie : result) {
                movieAdapter.add(movie);
                movieAdapter.notifyDataSetChanged();
            }
        } else {
            String MESSAGE = "There was error loading movies..Please check your internet connection";
            showToast(MESSAGE);
        }
    }

    //This method is used to display toast
    protected void showToast(String message){

        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(task.getActivity(), text, duration);
        toast.show();
    }

}