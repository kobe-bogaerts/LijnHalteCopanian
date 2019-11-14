package com.kolllor3.lijnhaltecopanian.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.kolllor3.lijnhaltecopanian.R;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeLineViewHolder>{

    private List<TimeTableItem> items;
    private TimeTableViewModel viewHolder;
    private Activity activity;

    public TimeTableAdapter(TimeTableViewModel viewModel, Activity activity) {
        this.viewHolder = viewModel;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TimeTableAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new TimeLineViewHolder(viewDataBinding, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableAdapter.TimeLineViewHolder holder, int position) {
        TimeTableItem item = items.get(position);
        holder.bind(item, viewHolder);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.timeline_list_item;
    }

    @Override
    public int getItemCount() {
        return Utilities.isNull(items) ? 0 : items.size();
    }

    public void setTimeTableItems(List<TimeTableItem> items){
        if(Utilities.isNotNull(activity)) {
            this.items = items;
            activity.runOnUiThread(this::notifyDataSetChanged);
        }
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        TimeLineViewHolder(ViewDataBinding binding, int viewType) {
            super(binding.getRoot());
            TimelineView timelineView = binding.getRoot().findViewById(R.id.timeline);
            timelineView.initLine(viewType);
            this.binding = binding;
        }

        void bind(TimeTableItem item, TimeTableViewModel viewModel){
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.timeTableItem, item);
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.viewModelTimeTable, viewModel);
        }
    }
}
