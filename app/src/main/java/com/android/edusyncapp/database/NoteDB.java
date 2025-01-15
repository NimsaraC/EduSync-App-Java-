package com.android.edusyncapp.database;

import androidx.annotation.NonNull;

import com.android.edusyncapp.models.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoteDB {
    private DatabaseReference database;
    public NoteDB(){
        database = FirebaseDatabase.getInstance().getReference("Note");
    }
    public interface DBCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }
    public interface DBCallbackWithData<T> {
        void onSuccess(T data);
        void onFailure(String error);
    }
    public interface DBListCallback<T> {
        void onSuccess(List<T> dataList);
        void onFailure(String error);
    }
    public void addNote(Note note, DBCallback callback){
        database.child(note.getId()).setValue(note)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Note added successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to add note: " + e.getMessage()));
    }
    public void listenForNoteChanges(ValueEventListener listener){
        database.addValueEventListener(listener);
    }
    public void getAllNotes(DBListCallback<Note> callback){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Note> notes = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Note note = dataSnapshot.getValue(Note.class);
                    notes.add(note);
                }
                callback.onSuccess(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure("Failed to get notes: " + error.getMessage());
            }
        });
    }
    public void getNoteById(String id, DBCallbackWithData<Note> callback){
        database.child(id).get().addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Note note = snapshot.getValue(Note.class);
                        callback.onSuccess(note);
                    } else {
                        callback.onFailure("Note not found");
                    }
                }
        ).addOnFailureListener(e -> callback.onFailure("Failed to get note: " + e.getMessage()));
    }
    public void deleteNote(String id, DBCallback callback){
        database.child(id).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess("Note deleted successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to delete note: " + e.getMessage()));

    }
    public void editNote(String id, Note updatedNote, DBCallback callback){
        database.child(id).setValue(updatedNote)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Note updated successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to update note: " + e.getMessage()));
    }
    public DatabaseReference getDatabase(){
        return database;
    }


}
