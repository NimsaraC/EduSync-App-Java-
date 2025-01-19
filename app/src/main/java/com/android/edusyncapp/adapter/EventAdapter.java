package com.android.edusyncapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.models.Event;
import com.android.edusyncapp.pages.EventDetails_Page;

import java.io.Serializable;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> events;
    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.mainTitle.setText(event.getTitle());
        holder.title1.setText(String.format(event.getDate() + " " + event.getTime()));
        holder.title2.setText(event.getLocation());
        holder.cardButton.setText("View Details");
        holder.title3.setVisibility(View.GONE);
        holder.cardButton.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), EventDetails_Page.class);
            intent.putExtra("event", event);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mainTitle, title1, cardButton, title2, title3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainTitle = itemView.findViewById(R.id.mainTitle);
            title1 = itemView.findViewById(R.id.title1);
            cardButton = itemView.findViewById(R.id.cardButton);
            title2 = itemView.findViewById(R.id.title2);
            title3 = itemView.findViewById(R.id.title3);
        }
    }
}
