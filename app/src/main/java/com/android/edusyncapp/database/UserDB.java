package com.android.edusyncapp.database;

import androidx.annotation.NonNull;

import com.android.edusyncapp.models.Student;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserDB {
    private DatabaseReference database;
    public UserDB(){
        database = FirebaseDatabase.getInstance().getReference("Student");
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

    public void addUser(Student user, DBCallback callback) {
        database.child(user.getId()).setValue(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess("User added successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to add user: " + e.getMessage()));
    }
    public void listenForStudentChanges(ValueEventListener listener){
        database.addValueEventListener(listener);
    }


    public void updateStudent(String id, Student updatedStudent, DBCallback callback) {
        database.child(id).setValue(updatedStudent)
                .addOnSuccessListener(aVoid -> callback.onSuccess("Student updated successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to update student: " + e.getMessage()));

    }
    public void deleteStudent(String id, DBCallback callback){
        database.child(id).removeValue()
                .addOnSuccessListener(aVoid -> callback.onSuccess("Student deleted successfully"))
                .addOnFailureListener(e -> callback.onFailure("Failed to delete student: " + e.getMessage()));
    }
    public void getUserById(String id, DBCallbackWithData<Student> callback){
        database.child(id).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                Student student = snapshot.getValue(Student.class);
                callback.onSuccess(student);
            } else {
                callback.onFailure("Student not found");
            }
        }
        ).addOnFailureListener(e -> callback.onFailure("Failed to get student: " + e.getMessage()));
    }
}
