package com.android.edusyncapp.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.edusyncapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home_Page extends Fragment {


    public Home_Page() {
    }

    public static Home_Page newInstance() {
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

    private LinearLayout btnAssignment, btnNote, btnEvent, btnTimetable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__page, container, false);

        btnAssignment = view.findViewById(R.id.btnAssignment);
        btnNote = view.findViewById(R.id.btnNote);
        btnEvent = view.findViewById(R.id.btnEvent);
        btnTimetable = view.findViewById(R.id.btnTimetable);

        btnAssignment.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Assignment_Page.class);
            startActivity(intent);
        });
        btnTimetable.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Main_Screen.class);
            intent.putExtra("fragment", "Timetable");
            startActivity(intent);
        });
        btnNote.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Main_Screen.class);
            intent.putExtra("fragment", "Note");
            startActivity(intent);
        });
        btnEvent.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), Main_Screen.class);
            intent.putExtra("fragment", "Event");
            startActivity(intent);
        });
        return view;
    }
}