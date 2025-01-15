package com.android.edusyncapp.pages;

import android.content.Intent;
import android.os.Bundle;
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

public class Signup_Page extends AppCompatActivity {

    private TextView btnSignUp, btnSignIn, edtFullName, edtUsername, edtNumber, edtEmail, edtPassword;
    private FirebaseAuth auth;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        userDB = new UserDB();

        auth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(V->{
            Intent intent = new Intent(Signup_Page.this, Login_Page.class);
            startActivity(intent);
        });
        btnSignUp.setOnClickListener(v->{
            if(edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                Toast.makeText(Signup_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                userSignUp();
            }
        });

    }
    private void items(){
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        edtEmail = findViewById(R.id.edtEmail);
        edtFullName = findViewById(R.id.edtFullName);
        edtNumber = findViewById(R.id.edtNumber);
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
    }
    private void userSignUp() {
        auth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        addStudent(userId);
                        Toast.makeText(Signup_Page.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup_Page.this, "User creation failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addStudent(String userId) {
        Student student = new Student(
                userId,
                edtUsername.getText().toString(),
                edtFullName.getText().toString(),
                edtEmail.getText().toString(),
                edtNumber.getText().toString()
        );

        userDB.addUser(student, new UserDB.DBCallback() {
            @Override
            public void onSuccess(String message) {
                Intent intent = new Intent(Signup_Page.this, Login_Page.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(Signup_Page.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}