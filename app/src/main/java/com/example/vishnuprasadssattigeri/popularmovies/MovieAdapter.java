package com.example.vishnuprasadssattigeri.popularmovies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vishnuprasadssattigeri on 10/9/16.
 */

public class MovieAdapter extends ArrayAdapter<MovieDetail> {
    Bitmap bitmap;

    public MovieAdapter(Activity context, List<MovieDetail> moviesDetails) {
        super(context, 0, moviesDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieDetail movieDetail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_movie, parent, false);
        }
        ImageView iconView = (ImageView) convertView.findViewById(R.id.list_movie_icon);
        if (!movieDetail.getMovieImagePath().equals("null")) {
            Picasso.with(getContext()).load(movieDetail.getMovieImagePath()).into(iconView);
        } else {
            iconView.setImageResource(R.drawable.no_poster);
        }
        iconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iconView.setBackgroundColor(Color.WHITE);

        return convertView;
    }
}

