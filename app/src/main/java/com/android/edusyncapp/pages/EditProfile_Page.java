package com.android.edusyncapp.pages;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.R;
import com.android.edusyncapp.database.UserDB;
import com.android.edusyncapp.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;

public class EditProfile_Page extends AppCompatActivity {

    private TextView btnSave;
    private EditText txtUsername, txtFullName, txtPhoneNumber;
    private String userId;
    private UserDB userDB;
    private FirebaseAuth auth;
    private Student student = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        userDB = new UserDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        userDB.getUserById(userId, new UserDB.DBCallbackWithData<Student>() {
            @Override
            public void onSuccess(Student data) {
                student = data;
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(EditProfile_Page.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        setDetails();
        btnSave.setOnClickListener(v->saveDetails());

    }
    private void items(){
        btnSave = findViewById(R.id.btnSave);
        txtUsername = findViewById(R.id.txtUsername);
        txtFullName = findViewById(R.id.txtFullName);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
    }
    private void setDetails(){
        userDB.getUserById(userId, new UserDB.DBCallbackWithData<Student>() {
            @Override
            public void onSuccess(Student data) {
                txtUsername.setText(data.getUsername());
                txtFullName.setText(data.getFullName());
                txtPhoneNumber.setText(data.getPhoneNumber());
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(EditProfile_Page.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveDetails(){
        student.setUsername(txtUsername.getText().toString());
        student.setFullName(txtFullName.getText().toString());
        student.setPhoneNumber(txtPhoneNumber.getText().toString());

        if(student.getUsername().isEmpty() || student.getFullName().isEmpty() || student.getPhoneNumber().isEmpty()){
            Toast.makeText(EditProfile_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            userDB.updateStudent(userId, student, new UserDB.DBCallback() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(EditProfile_Page.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(EditProfile_Page.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}