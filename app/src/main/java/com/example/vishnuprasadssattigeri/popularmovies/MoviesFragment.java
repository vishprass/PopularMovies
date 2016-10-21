package com.example.vishnuprasadssattigeri.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by vishnuprasadssattigeri on 10/9/16.
 */

public class MoviesFragment extends Fragment {
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieDetail>());


        GridView gridview = (GridView) rootView.findViewById(R.id.movieGrid);
        gridview.setAdapter(movieAdapter);

        //Call the detail movie screen
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), DisplayMovie.class);
                intent.putExtra("SELECTED_MOVIE", movieAdapter.getItem(position));
                startActivity(intent);

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        MoviesData moviesData = new MoviesData(movieAdapter,this);
        moviesData.execute();
    }

}
