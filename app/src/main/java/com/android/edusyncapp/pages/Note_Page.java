package com.android.edusyncapp.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.edusyncapp.R;

public class Note_Page extends Fragment {

    public Note_Page() {
    }

    public static Note_Page newInstance(String param1, String param2) {
        Note_Page fragment = new Note_Page();
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

    private EditText searchBox;
    private TextView AddNote;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note__page, container, false);
        items(view);
        actions();
        return view;
    }
    private void items(View view){
        searchBox = view.findViewById(R.id.searchBox);
        AddNote = view.findViewById(R.id.AddNote);

    }
    private void actions(){
        AddNote.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), AddNote_Page.class);
            startActivity(intent);
        });
    }
}