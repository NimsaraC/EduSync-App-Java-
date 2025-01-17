package com.android.edusyncapp.database;

import androidx.annotation.NonNull;

import com.android.edusyncapp.models.Timetable_Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimetableDB {
    private DatabaseReference database;
    public TimetableDB(){
        database = FirebaseDatabase.getInstance().getReference("Timetable");
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
    public void listenForTimetableChanges(ValueEventListener listener){
        database.addValueEventListener(listener);
    }
    public DatabaseReference getDatabase(){
        return database;
    }
    public void getAllTimetable(DBListCallback<Timetable_Event> callback){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Timetable_Event> timetable = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Timetable_Event event = dataSnapshot.getValue(Timetable_Event.class);
                    timetable.add(event);
                }
                callback.onSuccess(timetable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure("Failed to get timetable: " + error.getMessage());
            }
        });
    }


}
