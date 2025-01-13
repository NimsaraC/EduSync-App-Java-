package com.android.edusyncapp.pages;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.R;

public class AddAssignment_Page extends AppCompatActivity {
    private TextView btnAddAssignment, dueDate;
    private EditText subject, title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_assignment_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        selectDate();
    }
    private void items(){
        btnAddAssignment = findViewById(R.id.btnAddAssignment);
        dueDate = findViewById(R.id.dueDate);
        subject = findViewById(R.id.subject);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
    }
    private void selectDate(){
        dueDate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                dueDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
            });
        });

    }
}