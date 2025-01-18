package com.android.edusyncapp.pages;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.edusyncapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Event_Page extends Fragment {


    public Event_Page() {
    }

    public static Event_Page newInstance() {
        Event_Page fragment = new Event_Page();
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

    private TextView noEvents;
    private RecyclerView eventsListView;
    String selectedDate ="";
    private List<String> sampleList = new ArrayList<>();
    private TextView previousSelectedTextView = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event__page, container, false);
        noEvents = view.findViewById(R.id.noEvents);
        eventsListView = view.findViewById(R.id.eventsListView);
        sampleList.add("2025.01.02");
        setCalendar(view);
        return view;
    }

    private void setCalendar(View view) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int todayDay = today.get(Calendar.DAY_OF_MONTH);


        TextView monthYearTextView = view.findViewById(R.id.month_year_textview);
        if (monthYearTextView != null) {
            monthYearTextView.setText(getMonthName(currentMonth) + " " + currentYear);
        }

        int dayOfMonth = 1;

        int startDayPosition = (firstDayOfWeek == Calendar.SUNDAY) ? 7 : firstDayOfWeek - 1;

        for (int week = 1; week <= 6; week++) {
            for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
                String dayTextViewId = "day" + week + "_" + dayOfWeek;
                int resId = getActivity().getResources().getIdentifier(dayTextViewId, "id", getActivity().getPackageName());
                TextView dayTextView = view.findViewById(resId);

                if (dayTextView != null) {
                    if (week == 1 && dayOfWeek < startDayPosition) {
                        dayTextView.setText("");
                    } else if (dayOfMonth <= daysInMonth) {
                        dayTextView.setText(String.valueOf(dayOfMonth));
                        int finalDayOfMonth = dayOfMonth;

                        dayTextView.setOnClickListener(v -> {
                            if (previousSelectedTextView != null) {
                                previousSelectedTextView.setBackgroundColor(Color.parseColor("#ECEFF1"));
                                previousSelectedTextView.setTextColor(Color.parseColor("#283593"));
                            }
                            dayTextView.setBackgroundColor(Color.parseColor("#5C6BC0"));
                            dayTextView.setTextColor(Color.parseColor("#ECEFF1"));

                            selectedDate = String.format("%d.%02d.%02d", currentYear, currentMonth + 1, finalDayOfMonth);
                            checkEvents();

                            previousSelectedTextView = dayTextView;
                        });

                        if (currentYear == todayYear && currentMonth == todayMonth && dayOfMonth == todayDay) {
                            selectedDate = String.format("%d.%02d.%02d", currentYear, currentMonth + 1, finalDayOfMonth);
                            checkEvents();


                            dayTextView.setBackgroundColor(Color.parseColor("#9575CD"));
                            dayTextView.setTextColor(Color.parseColor("#ECEFF1"));
                        }

                        dayOfMonth++;
                    } else {
                        dayTextView.setText("");
                    }
                }
            }
        }
    }
    private void checkEvents(){
        if(sampleList.isEmpty()){
            noEvents.setVisibility(View.VISIBLE);
            eventsListView.setVisibility(View.GONE);
        }else if(sampleList.contains(selectedDate)){
            noEvents.setVisibility(View.GONE);
            eventsListView.setVisibility(View.VISIBLE);
        }else{
            noEvents.setVisibility(View.VISIBLE);
            eventsListView.setVisibility(View.GONE);
        }
    }
    private String getMonthName(int monthIndex) {
        switch (monthIndex) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
            default: return "";
        }
    }

}