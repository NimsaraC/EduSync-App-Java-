package com.android.edusyncapp.database;

import androidx.annotation.NonNull;

import com.android.edusyncapp.models.Assignment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AssignmentDB {
    private DatabaseReference database;
    public AssignmentDB(){
        database = FirebaseDatabase.getInstance().getReference("Assignment");
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
    public void addAssignment(Assignment assignment, DBCallback callback){
        database.child(assignment.getId()).setValue(assignment)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Assignment added successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to add assignment: " + e.getMessage()));
    }
    public void listenForAssignmentChanges(ValueEventListener listener){
        database.addValueEventListener(listener);
    }
    public void getAllAssignments(DBListCallback<Assignment> callback){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Assignment> assignments = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Assignment assignment = dataSnapshot.getValue(Assignment.class);
                    assignments.add(assignment);
                }
                callback.onSuccess(assignments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure("Failed to get assignments: " + error.getMessage());
            }
        });
    }
    public void editAssignment(String id, Assignment updatedAssignment, DBCallback callback){
        database.child(id).setValue(updatedAssignment)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Assignment updated successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to update assignment: " + e.getMessage()));
    }
    public void deleteAssignment(String id, DBCallback callback){
        database.child(id).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess("Assignment deleted successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to delete assignment: " + e.getMessage()));
    }
    public DatabaseReference getDatabase(){
        return database;
    }

}
