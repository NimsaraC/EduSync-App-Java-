package com.android.edusyncapp.pages;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.adapter.AssignmentAdapter;
import com.android.edusyncapp.database.AssignmentDB;
import com.android.edusyncapp.models.Assignment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Assignment_Page extends AppCompatActivity {

    private TextView btnAll, btnDue, btnEnd, btnAddAssignment, emptyAssignment;
    private RecyclerView assignmentListView;
    private AssignmentDB assignmentDB;
    private FirebaseAuth auth;
    private String userId;
    private AssignmentAdapter adapter;
    private List<Assignment> assignments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assignment_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();

        assignmentDB = new AssignmentDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        btnAll.setTextColor(getResources().getColor(R.color.primary));
        btnAll.setBackgroundColor(getResources().getColor(R.color.secondary_text));
        actions();


        assignmentListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentAdapter(assignments, this);
        assignmentListView.setAdapter(adapter);
        checkStatus();

        getAssignments("All");


    }

    private void items(){
        btnAll = findViewById(R.id.btnAll);
        btnDue = findViewById(R.id.btnDue);
        btnEnd = findViewById(R.id.btnEnd);
        btnAddAssignment = findViewById(R.id.btnAddAssignment);
        assignmentListView = findViewById(R.id.assignmentListView);
        emptyAssignment = findViewById(R.id.emptyAssignment);
    }
    private void actions(){
        btnAll.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.primary));
            btnDue.setTextColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setTextColor(getResources().getColor(R.color.secondary_text));
            btnAll.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            btnDue.setBackgroundColor(getResources().getColor(R.color.primary));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.primary));
            getAssignments("All");
        });
        btnDue.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.secondary_text));
            btnDue.setTextColor(getResources().getColor(R.color.primary));
            btnEnd.setTextColor(getResources().getColor(R.color.secondary_text));
            btnAll.setBackgroundColor(getResources().getColor(R.color.primary));
            btnDue.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.primary));
            getAssignments("Due");
        });
        btnEnd.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.secondary_text));
            btnDue.setTextColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setTextColor(getResources().getColor(R.color.primary));
            btnAll.setBackgroundColor(getResources().getColor(R.color.primary));
            btnDue.setBackgroundColor(getResources().getColor(R.color.primary));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            getAssignments("End");
        });
        btnAddAssignment.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), AddAssignment_Page.class);
            startActivity(intent);
        });
    }
    private void getAssignments(String status){
        assignmentDB.listenForAssignmentChanges(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assignments.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Assignment assignment = dataSnapshot.getValue(Assignment.class);
                    if(assignment.getUserId().equals(userId)){
                        if(assignment.getStatus().equals(status)){
                            assignments.add(assignment);
                        }else if(status.equals("All")){
                            assignments.add(assignment);
                        }
                    }
                }
                if(!assignments.isEmpty()){
                    emptyAssignment.setVisibility(GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Assignment_Page.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void checkStatus() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date today = Calendar.getInstance().getTime();

        if (!assignments.isEmpty()) {
            List<Assignment> assignmentsToUpdate = new ArrayList<>();

            for (Assignment assignment : assignments) {
                String dueDate = assignment.getDueDate();
                try {
                    Date assignmentDueDate = dateFormat.parse(dueDate);

                    if (assignmentDueDate.before(today) && !assignment.getStatus().equals("End")) {
                        assignment.setStatus("End");
                        assignmentsToUpdate.add(assignment);
                    }
                } catch (ParseException e) {
                    Log.e("AssignmentStatus", "Error parsing due date: " + e.getMessage());
                }
            }
            if (!assignmentsToUpdate.isEmpty()) {
                batchUpdateAssignments(assignmentsToUpdate);
            } else {
                Log.d("AssignmentStatus", "No assignments need status updates.");
            }
        } else {
            Log.d("AssignmentStatus", "No assignments available.");
        }
    }

    private void batchUpdateAssignments(List<Assignment> assignmentsToUpdate) {
        for (Assignment assignment : assignmentsToUpdate) {
            assignmentDB.editAssignment(assignment.getId(), assignment, new AssignmentDB.DBCallback() {
                @Override
                public void onSuccess(String message) {
                    Log.d("AssignmentStatus", "Assignment status updated to 'End' for assignment: " + assignment.getTitle());
                }

                @Override
                public void onFailure(String error) {
                    Log.e("AssignmentStatus", "Error updating assignment status: " + error);
                }
            });
        }
    }


}