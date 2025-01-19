package com.android.edusyncapp.database;

import androidx.annotation.NonNull;

import com.android.edusyncapp.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventDB {
    private DatabaseReference database;
    public EventDB(){
        database = FirebaseDatabase.getInstance().getReference("Event");
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
    public void listenForEventChanges(ValueEventListener listener){
        database.addValueEventListener(listener);
    }
    public DatabaseReference getDatabase(){
        return database;
    }
    public void getEventById(String id, DBCallbackWithData<Event> callback){
        database.child(id).get().addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Event event = snapshot.getValue(Event.class);
                        callback.onSuccess(event);
                    } else {
                        callback.onFailure("Event not found");
                    }
                }
        ).addOnFailureListener(e -> callback.onFailure("Failed to get event: " + e.getMessage()));
    }
    public void getAllEvents(DBListCallback<Event> callback){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Event> events = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    events.add(event);
                }
                callback.onSuccess(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure("Failed to get events: " + error.getMessage());
            }
        });
    }


}
