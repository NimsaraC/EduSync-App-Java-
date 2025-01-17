package com.android.edusyncapp.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.edusyncapp.R;
import com.android.edusyncapp.database.AssignmentDB;
import com.android.edusyncapp.models.Assignment;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private List<Assignment> assignments;
    private Context context;
    public AssignmentAdapter(List<Assignment> assignments, Context context) {
        this.assignments = assignments;
        this.context = context;
    }
    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_card, parent, false);
        return new AssignmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.mainTitle.setText(assignment.getTitle());
        holder.title1.setText(assignment.getTitle());
        holder.txtDueDate.setText("Due Date: " + assignment.getDueDate());

        AssignmentDB assignmentDB = new AssignmentDB();

        if (assignment.getStatus().equals("End")) {
            holder.isComplete.setVisibility(View.VISIBLE);
            holder.isProgress.setVisibility(View.GONE);
            holder.txtDueDateComplete.setText("Due Date: " + assignment.getDueDate());
        } else {
            holder.isComplete.setVisibility(View.GONE);
            holder.isProgress.setVisibility(View.VISIBLE);

            if (assignment.getDueDate() == null || assignment.getAddDate() == null) {
                holder.progressBar.setProgress(0);
                holder.txtProgress.setText("N/A");
                return;
            }

            SimpleDateFormat addDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat dueDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH);

            try {
                Date addDate = addDateFormat.parse(assignment.getAddDate());

                String dueDateStringWithTime = assignment.getDueDate() + " 23:59";
                Date dueDate = dueDateFormat.parse(dueDateStringWithTime);

                Date currentDate = new Date();

                long totalMinutes = TimeUnit.MINUTES.convert(dueDate.getTime() - addDate.getTime(), TimeUnit.MILLISECONDS);
                long minutesPassed = TimeUnit.MINUTES.convert(currentDate.getTime() - addDate.getTime(), TimeUnit.MILLISECONDS);

                if (totalMinutes > 0) {
                    int progress = (int) ((minutesPassed * 100) / totalMinutes);
                    holder.progressBar.setProgress(progress);
                    holder.progressBar.setMax(100);
                    holder.progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.btn_r)));

                    holder.txtProgress.setText(progress + "%");

                } else {
                    holder.progressBar.setProgress(0);
                    holder.txtProgress.setText("0%");
                }

            } catch (ParseException e) {
                e.printStackTrace();
                holder.progressBar.setProgress(0);
                holder.txtProgress.setText("Error");
            }
        }
    }



    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mainTitle, title1, txtDueDate, txtProgress, txtDueDateComplete;
        private LinearLayout isProgress, isComplete;
        private LinearProgressIndicator progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainTitle = itemView.findViewById(R.id.mainTitle);
            title1 = itemView.findViewById(R.id.title1);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            txtProgress = itemView.findViewById(R.id.txtProgress);
            txtDueDateComplete = itemView.findViewById(R.id.txtDueDateComplete);
            isProgress = itemView.findViewById(R.id.isProgress);
            isComplete = itemView.findViewById(R.id.isComplete);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
