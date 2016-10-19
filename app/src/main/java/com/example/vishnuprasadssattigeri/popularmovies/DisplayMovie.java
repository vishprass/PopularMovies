package com.example.vishnuprasadssattigeri.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class DisplayMovie extends Activity {

    private MovieDetail movieDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        Intent intent = getIntent();
        movieDetail = intent.getParcelableExtra("SELECTED_MOVIE");

        TextView movieName = (TextView) findViewById(R.id.movieName);
        movieName.setText(movieDetail.getMovieTitle());

        ImageView iconView = (ImageView) findViewById(R.id.movie_icon);
        if (!movieDetail.getMovieImagePath().equals("null")) {
            Picasso.with(getApplicationContext()).load(movieDetail.getMovieImagePath()).into(iconView);
        } else {
            iconView.setImageResource(R.drawable.no_poster);
        }
        iconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iconView.setBackgroundColor(Color.WHITE);

        TextView synopsis = (TextView) findViewById(R.id.movieSynopsis);
        synopsis.setText(movieDetail.getMovieSynopsis());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.applyPattern("dd-MMM-yyyy");
        String movieReleaseDate = df.format(movieDetail.getReleaseDate());

        TextView releaseDate = (TextView) findViewById(R.id.movieReleaseDate);
        releaseDate.setText(movieReleaseDate);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.movieRating);
        ratingBar.setRating((float) movieDetail.getMovieRating());

    }
}
