package com.example.administrator.one.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.one.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {


//    public MovieFragment() {
//        // Required empty public constructor
//    }

    public static MovieFragment newInstance(){
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_movie, container, false);
    }

}
