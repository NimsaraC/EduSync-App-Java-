package com.android.edusyncapp.pages;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.edusyncapp.R;
import com.android.edusyncapp.database.UserDB;
import com.android.edusyncapp.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Profile_Page extends Fragment {

    public Profile_Page() {
    }

    public static Profile_Page newInstance() {
        Profile_Page fragment = new Profile_Page();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView txtMainSName, txtSName, txtSUsername, txtUId, txtCName, txtEmail, txtPNumber, btnUpdate, btnLogout;
    private FirebaseAuth auth;
    private UserDB userDB;
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile__page, container, false);
        auth = FirebaseAuth.getInstance();
        userDB = new UserDB();
        userId = auth.getCurrentUser().getUid();
        setStudentData();
        items(view);
        btnLogout.setOnClickListener(v-> logout());
        btnUpdate.setOnClickListener(v->{
            startActivity(new Intent(getContext(), EditProfile_Page.class));
        });
        return view;
    }
    private void items(View view){
        txtMainSName = view.findViewById(R.id.txtMainSName);
        txtSName = view.findViewById(R.id.txtSName);
        txtSUsername = view.findViewById(R.id.txtSUsername);
        txtUId = view.findViewById(R.id.txtUId);
        txtCName = view.findViewById(R.id.txtCName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPNumber = view.findViewById(R.id.txtPNumber);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnLogout = view.findViewById(R.id.btnLogout);
    }
    private void setStudentData() {
        userDB.listenForStudentChanges(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Student student = snapshot.child(userId).getValue(Student.class);
                    if (student != null) {
                        txtSName.setText(student.getFullName());
                        txtSUsername.setText(student.getUsername());
                        txtUId.setText(student.getId());
                        txtEmail.setText(student.getEmail());
                        txtPNumber.setText(student.getPhoneNumber());
                        txtMainSName.setText(student.getFullName());
                    }
                } else {
                    Toast.makeText(getContext(), "Student not found in real-time database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to listen for changes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.simple_dialog);
        dialog.show();

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView btnYes = dialog.findViewById(R.id.btnAction);
        TextView btnNo = dialog.findViewById(R.id.btnCancel);

        txtTitle.setText("Logout");
        txtMessage.setText("Are you sure you want to logout?");

        btnNo.setOnClickListener(v->{
            dialog.dismiss();
        });
        btnYes.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getContext(), Login_Page.class));
            getActivity().finish();
        });
    }
}