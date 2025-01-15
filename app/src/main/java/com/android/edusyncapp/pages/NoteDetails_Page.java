package com.android.edusyncapp.pages;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.adapter.AddNoteAdapter;
import com.android.edusyncapp.adapter.NoteViewAdapter;
import com.android.edusyncapp.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteDetails_Page extends AppCompatActivity {
    private TextView btnManageNote, noteTitle, updatedDate, noteDescription, noImages;
    private RecyclerView imagesView;
    private List<String> imageUris = new ArrayList<>();
    private Note note = null;
    private NoteViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        note = (Note) getIntent().getSerializableExtra("note");
        imagesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new NoteViewAdapter(imageUris, this);
        imagesView.setAdapter(adapter);
        setData();

    }
    private void items(){
        btnManageNote = findViewById(R.id.btnManageNote);
        noteTitle = findViewById(R.id.noteTitle);
        updatedDate = findViewById(R.id.updatedDate);
        noteDescription = findViewById(R.id.noteDescription);
        noImages = findViewById(R.id.noImages);
        imagesView = findViewById(R.id.imagesView);
    }
    private void setData() {
        if (note == null) {
            Toast.makeText(this, "Note data is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        noteTitle.setText(note.getTitle());
        updatedDate.setText("Last update: " + note.getEditTime());
        noteDescription.setText(note.getDescription());

        if (note.getImageUrls() == null || note.getImageUrls().isEmpty()) {
            noImages.setVisibility(View.VISIBLE);
        } else {
            noImages.setVisibility(View.GONE);
            for (String url : note.getImageUrls()) {
                imageUris.add(url);
            }
            adapter.notifyDataSetChanged();
        }
    }

}