package com.android.edusyncapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.models.Note;
import com.android.edusyncapp.pages.NoteDetails_Page;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> notes;
    private Context context;
    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.mainTitle.setText(note.getTitle());
        holder.title1.setText(note.getDescription());

        holder.title2.setVisibility(View.GONE);
        holder.title3.setVisibility(View.GONE);

        holder.cardButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteDetails_Page.class);
            intent.putExtra("note", note);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
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
