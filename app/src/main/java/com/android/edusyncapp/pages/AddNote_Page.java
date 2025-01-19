package com.android.edusyncapp.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.adapter.AddNoteAdapter;
import com.android.edusyncapp.database.NoteDB;
import com.android.edusyncapp.models.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNote_Page extends AppCompatActivity {
    private EditText title, Note;
    private TextView btnAddPic, btnAddNote, txtType;
    private RecyclerView picList;
    private NoteDB noteDB;
    private FirebaseAuth auth;
    private String userId;
    private List<Uri> imageUris = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private AddNoteAdapter adapter;
    private String type;
    private Note editNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        type = getIntent().getStringExtra("type");

        items();
        noteDB = new NoteDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("student_notes");

        picList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddNoteAdapter(imageUris, this);
        picList.setAdapter(adapter);

        if (type.equals("Add")) {
            btnAddNote.setText("Add Note");
            btnAddPic.setOnClickListener(v -> selectImages());
            btnAddNote.setOnClickListener(v -> saveNoteImages());
        }else{
            btnAddNote.setText("Edit Note");
            txtType.setText("Edit Note");
            btnAddPic.setVisibility(View.GONE);
            editNote = (Note) getIntent().getSerializableExtra("note");
            setData();
            btnAddPic.setOnClickListener(v -> selectImages());
            btnAddNote.setOnClickListener(v -> saveEditImages());
        }


    }
    private void items(){
        title = findViewById(R.id.title);
        Note = findViewById(R.id.Note);
        btnAddPic = findViewById(R.id.btnAddPic);
        btnAddNote = findViewById(R.id.btnAddNote);
        picList = findViewById(R.id.picList);
        txtType = findViewById(R.id.txtType);
    }
    private void selectImages(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 102);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK && data != null){
            if(type.equals("Add")){
                imageUris.clear();
            }
            if(data.getClipData() != null){
                int count = data.getClipData().getItemCount();
                for(int i=0; i<count; i++){
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imageUris.add(imageUri);
                }
            }else if(data.getData() != null){
                imageUris.add(data.getData());
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void saveNoteImages() {
        imageUrls = new ArrayList<>();

        if (title.getText().toString().isEmpty() || Note.getText().toString().isEmpty()) {
            Toast.makeText(AddNote_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUris.isEmpty()) {
            saveNoteWithoutImages();
            return;
        }

        String id = noteDB.getDatabase().push().getKey();

        for (int i = 0; i < imageUris.size(); i++) {
            Uri imageUri = imageUris.get(i);

            if (imageUri == null) {
                continue;
            }

            String imageId = "note_" + System.currentTimeMillis() + "_" + i;
            StorageReference imageRef = storageRef.child(imageId);

            int finalI = i;
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                imageUrls.add(uri.toString());
                                if (imageUrls.size() == imageUris.size()) {
                                    saveNote(id);
                                }
                            }))
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed to upload image " + (finalI + 1), Toast.LENGTH_SHORT).show()
                    );
        }
    }

    private void saveNoteWithoutImages() {
        String id = noteDB.getDatabase().push().getKey();
        saveNote(id);
    }

    private void saveNote(String noteId){
        long currentTime = System.currentTimeMillis();
        String formattedTime = DateFormat.format("dd/MM/yyyy hh:mm:ss a", new Date(currentTime)).toString();

        Note note = new Note(
                noteId,
                userId,
                title.getText().toString(),
                Note.getText().toString(),
                imageUrls,
                formattedTime
        );
        noteDB.addNote(note, new NoteDB.DBCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(AddNote_Page.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNote_Page.this, Main_Screen.class);
                intent.putExtra("fragment", "Note");
                startActivity(intent);
            }
            @Override
            public void onFailure(String error) {
                Toast.makeText(AddNote_Page.this, error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setData() {
        imageUris.clear();

        title.setText(editNote.getTitle());
        Note.setText(editNote.getDescription());

        imageUrls = editNote.getImageUrls();
        List<Uri> editImages = new ArrayList<>();

        if (imageUrls != null) {
            for (String url : imageUrls) {
                String cleanUrl = url.trim();
                if (!cleanUrl.isEmpty()) {
                    editImages.add(Uri.parse(cleanUrl));
                    Log.d("Image URL", cleanUrl);
                }
            }
        }

        imageUris.addAll(editImages);
        adapter.notifyDataSetChanged();
    }

    private void editNote(){
        if (title.getText().toString().isEmpty() || Note.getText().toString().isEmpty()) {
            Toast.makeText(AddNote_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }else{
            long currentTime = System.currentTimeMillis();
            String formattedTime = DateFormat.format("dd/MM/yyyy hh:mm:ss a", new Date(currentTime)).toString();
            editNote.setTitle(title.getText().toString());
            editNote.setDescription(Note.getText().toString());
            editNote.setEditTime(formattedTime);
            editNote.setImageUrls(imageUrls);
            noteDB.editNote(editNote.getId(), editNote, new NoteDB.DBCallback() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(AddNote_Page.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNote_Page.this, Main_Screen.class);
                    intent.putExtra("fragment", "Note");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(AddNote_Page.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void saveEditImages(){
        if(imageUris.isEmpty()){
            if(imageUrls != null){
                imageUrls.clear();
                editNote.setImageUrls(imageUrls);
            }
            editNote();
        }else if(imageUrls.size() == imageUris.size()){
            editNote();
        }else {
            {
                imageUrls.clear();
                for(Uri u : imageUris){
                    imageUrls.add(u.toString());
                    if(imageUrls.size() == imageUris.size()){
                        editNote();
                    }
                }
            }
        }
    }

}