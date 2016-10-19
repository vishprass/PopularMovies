package com.example.vishnuprasadssattigeri.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vishnuprasadssattigeri on 10/9/16.
 */

public class MovieDetail implements Parcelable {

    String movieImagePath;
    String movieTitle;
    double movieRating;
    String movieSynopsis;
    Date releaseDate;
    private String webPath = "https://image.tmdb.org/t/p/w185";

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieImagePath() {
        return movieImagePath;
    }

    public void setMovieImagePath(String movieImagePath) {
        if (!movieImagePath.equals("null")) {
            this.movieImagePath = webPath + movieImagePath;
        } else {
            this.movieImagePath = "null";
        }
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.getMovieTitle());
        out.writeString(this.getMovieImagePath());
        out.writeString(this.getMovieSynopsis());
        out.writeDouble(this.getMovieRating());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        out.writeString(dateFormat.format(this.getReleaseDate()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public MovieDetail() {

    }

    public MovieDetail(Parcel in)
            throws java.text.ParseException {
        this.setMovieTitle(in.readString());
        this.setMovieImagePath(in.readString());
        this.setMovieSynopsis(in.readString());
        this.setMovieRating(in.readDouble());
        String tempDate = in.readString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate = sdf.parse(tempDate);
        this.setReleaseDate(releaseDate);

    }

    public static final Parcelable.Creator<MovieDetail> CREATOR
            = new Parcelable.Creator<MovieDetail>() {
        public MovieDetail createFromParcel(Parcel in) {
            try {
                return new MovieDetail(in);
            } catch (java.text.ParseException ex) {

            }
            return null;
        }

        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

}
