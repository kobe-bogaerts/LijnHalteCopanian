package com.kolllor3.lijnhaltecopanian.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import java.util.List;

public class TimeTableAdapter  extends RecyclerView.Adapter<TimeTableAdapter.TimeLineViewHolder>{

    private List<TimeTableItem> items;
    private TimeLineViewHolder modelView;
    private Activity activity;

    public TimeTableAdapter(TimeLineViewHolder modelView, Activity activity) {
        this.modelView = modelView;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TimeTableAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableAdapter.TimeLineViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Utilities.isNull(items) ? 0 : items.size();
    }

    public void setTimeTableItems(List<TimeTableItem> items){
        this.items = items;
        activity.runOnUiThread(this::notifyDataSetChanged);
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder{
        public TimeLineViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
