package com.android.edusyncapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        holder.ivDelete.setOnClickListener(v -> {

            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.simple_dialog);
            dialog.show();

            TextView txtTitle = dialog.findViewById(R.id.txtTitle);
            TextView txtMessage = dialog.findViewById(R.id.txtMessage);
            TextView btnCancel = dialog.findViewById(R.id.btnCancel);
            TextView btnAction = dialog.findViewById(R.id.btnAction);

            txtTitle.setText("Delete Image");
            txtMessage.setText("Are you sure you want to delete this image?");

            btnCancel.setOnClickListener(v2 -> dialog.dismiss());
            btnAction.setOnClickListener(v2 -> {
                imageUris.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, imageUris.size());
            });


        });

        Glide.with(context).load(imageUri).into(holder.ivImage);

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
    private void showDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.simple_dialog);
        dialog.show();

        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
        TextView txtMessage = dialog.findViewById(R.id.txtMessage);
        TextView btnCancel = dialog.findViewById(R.id.btnCancel);
        TextView btnAction = dialog.findViewById(R.id.btnAction);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnAction.setOnClickListener(v -> dialog.dismiss());
    }
}
