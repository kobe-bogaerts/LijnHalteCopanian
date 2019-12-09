package com.kolllor3.lijnhaltecopanian.adapter;

import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kolllor3.lijnhaltecopanian.model.LijnItem;
import com.kolllor3.lijnhaltecopanian.model.RealTimeItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RealTimeTableAdapter extends RecyclerView.Adapter<RealTimeTableAdapter.TimeLineViewHolder>{

    private int listItemRecouceId;
    private List<RealTimeItem> items;
    private TimeTableViewModel viewHolder;
    private SparseArray<LijnItem> lijnItemMap = new SparseArray<>();

    public RealTimeTableAdapter(TimeTableViewModel viewModel, int listItemRecouceId) {
        this.viewHolder = viewModel;
        this.listItemRecouceId = listItemRecouceId;
    }

    @NonNull
    @Override
    public RealTimeTableAdapter.TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new TimeLineViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RealTimeTableAdapter.TimeLineViewHolder holder, int position) {
        RealTimeItem item = items.get(position);
        holder.bind(item, viewHolder, lijnItemMap.get(item.getLijnnummer()));
    }

    @Override
    public int getItemViewType(int position) {
        return listItemRecouceId;
    }

    @Override
    public int getItemCount() {
        return Utilities.isNull(items) ? 0 : items.size();
    }

    public void setRealTimeItems(List<RealTimeItem> items){
        this.items = items;
        Handler h = new Handler();
        h.post(this::notifyDataSetChanged);
    }

    public void setLijnItemMap(SparseArray<LijnItem> lijnItemMap) {
        this.lijnItemMap = lijnItemMap;
        Handler h = new Handler();
        h.post(this::notifyDataSetChanged);
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        TimeLineViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RealTimeItem item, TimeTableViewModel viewModel, LijnItem lijnItem){
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.realTimeTableItem, item);
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.realTimeLijnItem, lijnItem);
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.viewModelRealTimeTable, viewModel);
        }
    }
}
