package com.android.edusyncapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.pages.NoteSingleImage;
import com.bumptech.glide.Glide;

import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.ViewHolder> {
    private List<String> imageUris;
    private Context context;

    public NoteViewAdapter(List<String> imageUris, Context context) {
        this.imageUris = imageUris;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_note_image_view, parent, false);
        return new NoteViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewAdapter.ViewHolder holder, int position) {
        String imageUrl = imageUris.get(position);
        Glide.with(context).load(imageUrl).into(holder.ivImage);
        holder.ivDelete.setVisibility(View.GONE);
        holder.ivImage.setOnClickListener(v->{
            Intent intent = new Intent(context, NoteSingleImage.class);
            intent.putExtra("imageUrl", imageUrl);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
