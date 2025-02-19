package com.android.edusyncapp.pages;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.edusyncapp.R;
import com.android.edusyncapp.databinding.ActivityMainBinding;
import com.android.edusyncapp.databinding.ActivityMainScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_Screen extends AppCompatActivity {
    ActivityMainScreenBinding binding;
    private String fragmentName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        replaceFragment(new Home_Page());

        fragmentName = getIntent().getStringExtra("fragment");
        if (fragmentName != null) {
            if (fragmentName.equals("Note")) {
                replaceFragment(new Note_Page());
                binding.bottomNavigationView.setSelectedItemId(R.id.Notes);
            } else if (fragmentName.equals("Event")) {
                replaceFragment(new Event_Page());
                binding.bottomNavigationView.setSelectedItemId(R.id.Events);
            } else if (fragmentName.equals("Timetable")) {
                replaceFragment(new Timetable_Page());
                binding.bottomNavigationView.setSelectedItemId(R.id.Timetable);
            } else{
                replaceFragment(new Profile_Page());
                binding.bottomNavigationView.setSelectedItemId(R.id.Profile);
            }
        } else {
            replaceFragment(new Home_Page());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Home) {
                replaceFragment(new Home_Page());
            } else if (item.getItemId() == R.id.Notes) {
                replaceFragment(new Note_Page());
            } else if (item.getItemId() == R.id.Timetable) {
                replaceFragment(new Timetable_Page());
            } else if (item.getItemId() == R.id.Events) {
                replaceFragment(new Event_Page());
            } else if (item.getItemId() == R.id.Profile) {
                replaceFragment(new Profile_Page());
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        confirmation();
    }

    private void confirmation(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.show();

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAction = dialog.findViewById(R.id.btnAction);

        txtTitle.setText("Confirmation");
        txtMessage.setText("Are you sure you want to exit?");
        btnCancel.setText("Cancel");
        btnAction.setText("Exit");

        btnCancel.setOnClickListener(v-> {
            dialog.dismiss();
        });

        btnAction.setOnClickListener(v-> {
            dialog.dismiss();
            Intent intent = new Intent(this, Login_Page.class);
            finish();
        });

    }
}