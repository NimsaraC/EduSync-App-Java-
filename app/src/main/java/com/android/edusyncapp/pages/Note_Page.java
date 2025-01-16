package com.android.edusyncapp.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.edusyncapp.R;
import com.android.edusyncapp.adapter.NoteAdapter;
import com.android.edusyncapp.database.NoteDB;
import com.android.edusyncapp.models.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView noteListView;
    private NoteDB noteDB;
    private FirebaseAuth auth;
    private String userId;
    private NoteAdapter adapter;
    private List<Note> notes = new ArrayList<>();
    private List<Note> filteredNotes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note__page, container, false);
        items(view);

        noteDB = new NoteDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        setData();
        actions();
        searchNotes();

        return view;
    }

    private void items(View view) {
        searchBox = view.findViewById(R.id.searchBox);
        AddNote = view.findViewById(R.id.AddNote);
        noteListView = view.findViewById(R.id.noteListView);
    }

    private void actions() {
        AddNote.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNote_Page.class);
            startActivity(intent);
        });
    }

    private void setData() {
        noteListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NoteAdapter(filteredNotes, getActivity());
        noteListView.setAdapter(adapter);
        loadNotes();
    }

    private void loadNotes() {
        noteDB.listenForNoteChanges(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                filteredNotes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                    if (note != null && note.getUserId().equals(userId)) {
                        notes.add(note);
                    }
                }
                filteredNotes.addAll(notes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchNotes() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().trim().toLowerCase();
                filteredNotes.clear();

                if (query.isEmpty()) {
                    filteredNotes.addAll(notes);
                } else {
                    for (Note note : notes) {
                        if (note.getTitle().toLowerCase().contains(query) ||
                                note.getTitle().toLowerCase().contains(query)) {
                            filteredNotes.add(note);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
