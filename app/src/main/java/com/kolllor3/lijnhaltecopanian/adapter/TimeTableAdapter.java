package com.kolllor3.lijnhaltecopanian.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeLineViewHolder>{

    private int listItemRecouceId;
    private List<TimeTableItem> items;
    private TimeTableViewModel viewHolder;

    public TimeTableAdapter(TimeTableViewModel viewModel, int listItemRecouceId) {
        this.viewHolder = viewModel;
        this.listItemRecouceId = listItemRecouceId;
    }

    @NonNull
    @Override
    public TimeTableAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new TimeLineViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableAdapter.TimeLineViewHolder holder, int position) {
        TimeTableItem item = items.get(position);
        holder.bind(item, viewHolder);
    }

    @Override
    public int getItemViewType(int position) {
        return listItemRecouceId;
    }

    @Override
    public int getItemCount() {
        return Utilities.isNull(items) ? 0 : items.size();
    }

    public void setTimeTableItems(List<TimeTableItem> items){
        this.items = items;
        Handler h = new Handler();
        h.post(this::notifyDataSetChanged);
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        TimeLineViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TimeTableItem item, TimeTableViewModel viewModel){
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.timeTableItem, item);
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.viewModelTimeTable, viewModel);
        }
    }
}
