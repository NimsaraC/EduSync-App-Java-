package com.android.edusyncapp.pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.R;
import com.android.edusyncapp.models.Event;

public class EventDetails_Page extends AppCompatActivity {
    private TextView eventTitle, eventDate, eventLocation, eventDescription, btnBack;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        event = (Event) getIntent().getSerializableExtra("event");
        setData();
        btnBack.setOnClickListener(v->{
            finish();
        });
    }
    private void items(){
        eventTitle = findViewById(R.id.eventTitle);
        eventDate = findViewById(R.id.eventDate);
        eventLocation = findViewById(R.id.eventLocation);
        eventDescription = findViewById(R.id.eventDescription);
        btnBack = findViewById(R.id.btnBack);
    }
    private void setData(){
        eventTitle.setText(event.getTitle());
        eventDate.setText("Date & time: " + event.getDate() + " " + event.getTime());
        eventLocation.setText("Location: " + event.getLocation());
        eventDescription.setText(event.getDescription());

    }
}