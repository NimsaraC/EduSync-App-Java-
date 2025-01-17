package com.android.edusyncapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.models.Timetable_Event;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.Viewholder> {
    private List<Timetable_Event> events;
    public TimetableAdapter(List<Timetable_Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public TimetableAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableAdapter.Viewholder holder, int position) {
        Timetable_Event event = events.get(position);

        holder.txtStartTime.setText(String.valueOf(event.getStartTime()));
        holder.txtEndTime.setText(String.valueOf(event.getEndTime()));
        holder.txtEventName.setText(event.getEventName());
        holder.txtEventDescription.setText(event.getEventLocation());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView txtStartTime, txtEndTime, txtEventName, txtEventDescription;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtEventDescription = itemView.findViewById(R.id.txtEventDescription);
        }
    }
}
