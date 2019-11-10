package com.kolllor3.lijnhaltecopanian.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.kolllor3.lijnhaltecopanian.R;
import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.viewModel.BaseHalteViewModel;

import java.util.List;

public class HalteListAdapter extends RecyclerView.Adapter<HalteListAdapter.HalteViewHolder> {

    private List<Halte> halteItems;
    private BaseHalteViewModel modelView;
    private Activity activity;

    public HalteListAdapter(BaseHalteViewModel modelView, Activity activity) {
        this.modelView = modelView;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HalteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);

        return new HalteViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HalteViewHolder holder, int position) {
        Halte item = halteItems.get(position);

        holder.bind(item, modelView);
    }

    @Override
    public int getItemCount() {
        return halteItems == null ? 0 : halteItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.halte_list_item;
    }

    public void setHalteItems(List<Halte> items){
        halteItems = items;
        activity.runOnUiThread(this::notifyDataSetChanged);
    }

    public List<Halte> getHalteItems() {
        return halteItems;
    }

    class HalteViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        //this constructor is tho bind the data and the xml together
        HalteViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Halte item, BaseHalteViewModel model){
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.item, item);
            binding.setVariable(com.kolllor3.lijnhaltecopanian.BR.viewModel, model);
        }
    }
}
