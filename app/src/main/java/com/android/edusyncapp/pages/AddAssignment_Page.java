package com.android.edusyncapp.pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.R;
import com.android.edusyncapp.database.AssignmentDB;
import com.android.edusyncapp.database.NoteDB;
import com.android.edusyncapp.models.Assignment;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssignment_Page extends AppCompatActivity {
    private TextView btnAddAssignment;
    private TextView dueDate;
    private EditText subject, title, description;
    private AssignmentDB assignmentDB;
    private FirebaseAuth auth;
    private String userId;

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

        assignmentDB = new AssignmentDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        selectDate();
        btnAddAssignment.setOnClickListener(v->{
            if(dueDate.getText().toString().isEmpty() || subject.getText().toString().isEmpty() || title.getText().toString().isEmpty() || description.getText().toString().isEmpty()){
                Toast.makeText(AddAssignment_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                saveData();
            }
        });
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

    private void saveData() {
        String dueDateString = dueDate.getText().toString();
        String status;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date dueDate = dateFormat.parse(dueDateString);
            Date currentDate = Calendar.getInstance().getTime();

            if (dueDate.after(currentDate) || dueDate.equals(currentDate)) {
                status = "Due";
            } else {
                status = "End";
            }
            String id = assignmentDB.getDatabase().push().getKey();
            Assignment assignment = new Assignment(
                    id,
                    userId,
                    subject.getText().toString(),
                    title.getText().toString(),
                    description.getText().toString(),
                    dueDateString,
                    status,
                    currentDate.toString()
            );

            assignmentDB.addAssignment(assignment, new AssignmentDB.DBCallback() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(AddAssignment_Page.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }
                @Override
                public void onFailure(String error) {
                    Toast.makeText(AddAssignment_Page.this, error, Toast.LENGTH_SHORT).show();
                }
            });



        } catch (ParseException e) {
            Log.e("AddAssignment_Page", "Error parsing date: " + e.getMessage());
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
        }
    }

}