package com.android.edusyncapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.pages.Login_Page;
import com.android.edusyncapp.pages.Main_Screen;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView btnEnter;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();

        btnEnter = findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(v->{
            if(auth.getCurrentUser() != null){
                Intent intent = new Intent(MainActivity.this, Main_Screen.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, Login_Page.class);
                startActivity(intent);
            }
        });

        //todo: Edit & Delete Notes
        //todo: Logout
        //todo: Note details single Image view
        //todo: Note Search
        //todo: Assignment ProgressBar
    }
}