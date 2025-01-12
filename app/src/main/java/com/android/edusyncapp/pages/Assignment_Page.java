package com.android.edusyncapp.pages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.edusyncapp.R;

public class Assignment_Page extends AppCompatActivity {

    private TextView btnAll, btnDue, btnEnd;

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
        btnAll.setTextColor(getResources().getColor(R.color.primary));
        btnAll.setBackgroundColor(getResources().getColor(R.color.secondary_text));
        actions();
    }

    private void items(){
        btnAll = findViewById(R.id.btnAll);
        btnDue = findViewById(R.id.btnDue);
        btnEnd = findViewById(R.id.btnEnd);
    }
    private void actions(){
        btnAll.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.primary));
            btnDue.setTextColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setTextColor(getResources().getColor(R.color.secondary_text));
            btnAll.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            btnDue.setBackgroundColor(getResources().getColor(R.color.primary));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.primary));
        });
        btnDue.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.secondary_text));
            btnDue.setTextColor(getResources().getColor(R.color.primary));
            btnEnd.setTextColor(getResources().getColor(R.color.secondary_text));
            btnAll.setBackgroundColor(getResources().getColor(R.color.primary));
            btnDue.setBackgroundColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.primary));
        });
        btnEnd.setOnClickListener(v->{
            btnAll.setTextColor(getResources().getColor(R.color.secondary_text));
            btnDue.setTextColor(getResources().getColor(R.color.secondary_text));
            btnEnd.setTextColor(getResources().getColor(R.color.primary));
            btnAll.setBackgroundColor(getResources().getColor(R.color.primary));
            btnDue.setBackgroundColor(getResources().getColor(R.color.primary));
            btnEnd.setBackgroundColor(getResources().getColor(R.color.secondary_text));
        });
    }
}