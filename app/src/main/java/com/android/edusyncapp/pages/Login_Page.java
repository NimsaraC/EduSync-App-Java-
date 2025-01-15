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
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {

    private TextView btnSignUp, btnSignIn, edtEmail, edtPassword;
    private FirebaseAuth auth;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        items();
        userDB = new UserDB();
        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(V->{
            Intent intent = new Intent(Login_Page.this, Signup_Page.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(V->{
            if(edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()){
                Toast.makeText(Login_Page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                userLogin();
            }
        });

    }
    private void items(){
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnLogin);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }
    private void userLogin(){
        auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Login_Page.this, Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login_Page.this, "User login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}