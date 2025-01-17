package com.android.edusyncapp.pages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.edusyncapp.R;
import com.android.edusyncapp.adapter.TimetableAdapter;
import com.android.edusyncapp.database.TimetableDB;
import com.android.edusyncapp.models.Timetable_Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Timetable_Page extends Fragment {

    public Timetable_Page() {
    }

    public static Timetable_Page newInstance() {
        Timetable_Page fragment = new Timetable_Page();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    private TextView txtToday, day1, day2, day3, day4, day5, day6, day7, noEvents, day, selectedDay;
    private RecyclerView timeTableRecycle;
    private TimetableDB timetableDB;
    private FirebaseAuth auth;
    private String userId;
    private List<Timetable_Event> timetableEvents = new ArrayList<>();
    private List<Timetable_Event> selectedDayEvents = new ArrayList<>();
    private TimetableAdapter adapter;
    private String selectedDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable__page, container, false);

        items(view);
        setWeekDays();

        timetableDB = new TimetableDB();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        adapter = new TimetableAdapter(selectedDayEvents);
        timeTableRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeTableRecycle.setAdapter(adapter);

        loadTimetable();

        return view;
    }

    private void items(View view){

        txtToday = view.findViewById(R.id.txtToday);
        day1 = view.findViewById(R.id.day1);
        day2 = view.findViewById(R.id.day2);
        day3 = view.findViewById(R.id.day3);
        day4 = view.findViewById(R.id.day4);
        day5 = view.findViewById(R.id.day5);
        day6 = view.findViewById(R.id.day6);
        day7 = view.findViewById(R.id.day7);
        noEvents = view.findViewById(R.id.noEvents);
        day = view.findViewById(R.id.day);
        timeTableRecycle = view.findViewById(R.id.timeTableRecycle);
    }

    private void setWeekDays() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault());
        SimpleDateFormat fullDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        selectedDate = fullDate.format(calendar.getTime());
        txtToday.setText(dateFormat.format(calendar.getTime()));

        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        TextView[] days = {day1, day2, day3, day4, day5, day6, day7};
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        String todayDate = dayFormat.format(Calendar.getInstance().getTime());

        for (int i = 0; i < 7; i++) {
            String currentDay = dayFormat.format(calendar.getTime());
            days[i].setText(currentDay);

            if (currentDay.equals(todayDate)) {
                days[i].setBackgroundResource(R.color.btn_l);
                days[i].setTextColor(getResources().getColor(R.color.primary));
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.add(Calendar.DAY_OF_MONTH, -7);

        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (int i = 0; i < 7; i++) {
            String currentDate = fullDate.format(calendar.getTime());
            setDateName(dayNames[i], days[i], txtToday, days, currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void setDateName(String day, TextView selectedDay,TextView today, TextView[] allDays, String currentDate) {
        selectedDay.setOnClickListener(v -> {
            today.setText(day);

            for (TextView dayTextView : allDays) {
                dayTextView.setBackgroundResource(R.color.primary);
                dayTextView.setTextColor(getResources().getColor(R.color.btn_l));
            }

            selectedDay.setBackgroundResource(R.color.btn_l);
            selectedDay.setTextColor(getResources().getColor(R.color.primary));
            selectedDate = currentDate;
            loadTimetable();
        });
    }
    private void loadTimetable() {
        timetableDB.listenForTimetableChanges(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                timetableEvents.clear();
                selectedDayEvents.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Timetable_Event event = dataSnapshot.getValue(Timetable_Event.class);
                    if (event != null && event.getStudentId().equals(userId)) {
                        timetableEvents.add(event);
                    }
                }
                for (Timetable_Event t: timetableEvents){
                    if(t.getDate().equals(selectedDate)){
                        selectedDayEvents.add(t);
                    }
                }

                if (selectedDayEvents.isEmpty()) {
                    noEvents.setVisibility(View.VISIBLE);
                } else {
                    noEvents.setVisibility(View.GONE);
                }

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}