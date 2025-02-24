package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AttendanceRecord;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<AttendanceRecord> attendanceList;

    public AttendanceAdapter(List<AttendanceRecord> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceRecord record = attendanceList.get(position);
        holder.subjectName.setText(record.getSubjectName());
        holder.lectureNumber.setText("Lecture " + record.getLectureNumber());
        holder.attendanceTime.setText(record.getAttendanceTime());
        holder.attendanceStatus.setImageResource(record.isPresent() ? R.drawable.ic_check_circle_24 : R.drawable.ic_cross_circle);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName, lectureNumber, attendanceTime;
        ImageView attendanceStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subjectName);
            lectureNumber = itemView.findViewById(R.id.lectureNumber);
            attendanceTime = itemView.findViewById(R.id.attendanceTime);
            attendanceStatus = itemView.findViewById(R.id.attendanceStatus);
        }
    }
}
