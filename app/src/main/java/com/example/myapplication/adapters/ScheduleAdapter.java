package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.ClassSchedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ClassSchedule> classSchedules;

    public ScheduleAdapter(List<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassSchedule schedule = classSchedules.get(position);
        holder.className.setText(schedule.getClassName());
        holder.classTime.setText(schedule.getStartTime() + " - " + schedule.getEndTime());
        holder.classRoom.setText("Room: " + schedule.getRoom());
        holder.classInstructor.setText("Instructor: " + schedule.getInstructor());
    }

    @Override
    public int getItemCount() {
        return classSchedules.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        TextView classTime;
        TextView classRoom;
        TextView classInstructor;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            classTime = itemView.findViewById(R.id.classTime);
            classRoom = itemView.findViewById(R.id.classRoom);
            classInstructor = itemView.findViewById(R.id.classInstructor);
        }
    }
}