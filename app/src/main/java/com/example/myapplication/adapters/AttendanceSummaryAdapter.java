package com.example.myapplication.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AttendanceSummary;

import java.util.List;

public class AttendanceSummaryAdapter extends RecyclerView.Adapter<AttendanceSummaryAdapter.ViewHolder> {

    private List<AttendanceSummary> attendanceSummaries;

    public AttendanceSummaryAdapter(List<AttendanceSummary> attendanceSummaries) {
        this.attendanceSummaries = attendanceSummaries;
    }

    // Update attendance records and notify the adapter
    public void updateAttendanceSummaries(List<AttendanceSummary> attendanceSummaries) {
        this.attendanceSummaries = attendanceSummaries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceSummary summary = attendanceSummaries.get(position);
        holder.textSubjectName.setText(summary.getSubjectName());
        holder.textAttendancePercentage.setText("Attendance: " + summary.getAttendancePercentage() + "%");
    }

    @Override
    public int getItemCount() {
        return attendanceSummaries != null ? attendanceSummaries.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textSubjectName;
        TextView textAttendancePercentage;

        ViewHolder(View itemView) {
            super(itemView);
            textSubjectName = itemView.findViewById(R.id.textSubjectName);
            textAttendancePercentage = itemView.findViewById(R.id.textAttendancePercentage);
        }
    }
}

