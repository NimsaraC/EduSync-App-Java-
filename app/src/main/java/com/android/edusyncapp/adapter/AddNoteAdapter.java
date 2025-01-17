package com.android.edusyncapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AddNoteAdapter extends RecyclerView.Adapter<AddNoteAdapter.ViewHolder> {
    private List<Uri> imageUris;
    private Context context;
    public AddNoteAdapter(List<Uri> imageUris, Context context) {
        this.imageUris = imageUris;
        this.context = context;
    }

    @NonNull
    @Override
    public AddNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_note_image_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddNoteAdapter.ViewHolder holder, int position) {
        Uri imageUri = imageUris.get(position);
//        holder.ivImage.setImageURI(imageUri);
        Glide.with(context).load(imageUri).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
