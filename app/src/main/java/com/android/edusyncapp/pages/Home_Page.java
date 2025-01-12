package com.android.edusyncapp.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.edusyncapp.R;

public class Home_Page extends Fragment {



    public Home_Page() {
    }

    public static Home_Page newInstance(String param1, String param2) {
        Home_Page fragment = new Home_Page();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private LinearLayout btnAssignment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__page, container, false);

        btnAssignment = view.findViewById(R.id.btnAssignment);

        btnAssignment.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Assignment_Page.class);
            startActivity(intent);
        });

        return view;
    }
}