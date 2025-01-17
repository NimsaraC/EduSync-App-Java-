package com.android.edusyncapp.pages;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.android.edusyncapp.database.NoteDB;
import com.android.edusyncapp.models.Note;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class NoteDetails_Page extends AppCompatActivity {
    private TextView btnManageNote, noteTitle, updatedDate, noteDescription, noImages, btnDelete;
    private RecyclerView imagesView;
    private List<String> imageUris = new ArrayList<>();
    private Note note = null;
    private NoteViewAdapter adapter;
    private NoteDB noteDB;

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
        noteDB = new NoteDB();
        note = (Note) getIntent().getSerializableExtra("note");
        imagesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        adapter = new NoteViewAdapter(imageUris, this);
        imagesView.setAdapter(adapter);
        setData();

        btnManageNote.setOnClickListener(v -> manageNote());
        btnDelete.setOnClickListener(v -> noteDelete());

    }
    private void items(){
        btnManageNote = findViewById(R.id.btnManageNote);
        noteTitle = findViewById(R.id.noteTitle);
        updatedDate = findViewById(R.id.updatedDate);
        noteDescription = findViewById(R.id.noteDescription);
        noImages = findViewById(R.id.noImages);
        imagesView = findViewById(R.id.imagesView);
        btnDelete = findViewById(R.id.btnDelete);
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
    private void manageNote(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAction = dialog.findViewById(R.id.btnAction);

        txtTitle.setText("Edit Note");
        txtMessage.setText("Are you sure you want to edit this note?");
        btnAction.setText("Edit");
        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        btnAction.setOnClickListener(v12 -> {
            Intent intent = new Intent(NoteDetails_Page.this, AddNote_Page.class);
            intent.putExtra("type", "Edit");
            intent.putExtra("note", note);
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.show();
    }
    private void noteDelete() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAction = dialog.findViewById(R.id.btnAction);

        txtTitle.setText("Delete Note");
        txtMessage.setText("Are you sure you want to delete this note?");
        btnAction.setText("Delete");

        btnCancel.setOnClickListener(v1 -> dialog.dismiss());

        btnAction.setOnClickListener(v -> {
            noteDB.deleteNote(note.getId(), new NoteDB.DBCallback() {
                @Override
                public void onSuccess(String message) {
                    if (note.getImageUrls() != null && !note.getImageUrls().isEmpty()) {
                        for (String imageUrl : note.getImageUrls()) {
                            StorageReference imageRef = FirebaseStorage.getInstance()
                                    .getReferenceFromUrl(imageUrl);

                            imageRef.delete()
                                    .addOnSuccessListener(aVoid -> Log.d("ImageDelete", "Image deleted successfully"))
                                    .addOnFailureListener(e -> Log.e("ImageDelete", "Failed to delete image", e));
                        }
                    }

                    Toast.makeText(NoteDetails_Page.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(NoteDetails_Page.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }


}