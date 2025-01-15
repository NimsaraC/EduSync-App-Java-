package com.android.edusyncapp.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNote_Page extends AppCompatActivity {
    private EditText title, Note;
    private TextView btnAddPic, btnAddNote;
    private RecyclerView picList;
    private NoteDB noteDB;
    private FirebaseAuth auth;
    private String userId;
    private List<Uri> imageUris = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private AddNoteAdapter adapter;

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

        items();
        noteDB = new NoteDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("student_notes");

        picList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new AddNoteAdapter(imageUris);
        picList.setAdapter(adapter);

        btnAddPic.setOnClickListener(v -> selectImages());
        btnAddNote.setOnClickListener(v -> saveNoteImages());

    }
    private void items(){
        title = findViewById(R.id.title);
        Note = findViewById(R.id.Note);
        btnAddPic = findViewById(R.id.btnAddPic);
        btnAddNote = findViewById(R.id.btnAddNote);
        picList = findViewById(R.id.picList);
    }
    private void addNote(){

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
            imageUris.clear();
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
}