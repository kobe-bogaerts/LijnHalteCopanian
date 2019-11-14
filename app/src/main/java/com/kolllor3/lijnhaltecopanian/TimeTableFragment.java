package com.kolllor3.lijnhaltecopanian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.lijnhaltecopanian.constants.Constants;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;


public class TimeTableFragment extends Fragment implements Constants {

    private static final String ARG_HALTE_NUMBER = "halte_number";
    private static final String ARG_ENTITEIT_NUMBER = "entiteit_number";

    private TimeTableViewModel timeTableViewModel;
    private int haltenummer;
    private int halteentiteit;


    public static TimeTableFragment newInstance(int haltenummer, int halteentiteit) {
        TimeTableFragment fragment = new TimeTableFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_HALTE_NUMBER, haltenummer);
        bundle.putInt(ARG_ENTITEIT_NUMBER, halteentiteit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel.class);

        if(Utilities.isNull(savedInstanceState))
            timeTableViewModel.init(getActivity());

        if (getArguments() != null) {
            haltenummer = getArguments().getInt(ARG_HALTE_NUMBER);
            halteentiteit = getArguments().getInt(ARG_ENTITEIT_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_halte_time_table, container, false);

        RecyclerView timeLineList = root.findViewById(R.id.timeline);
        timeLineList.setLayoutManager(new LinearLayoutManager(getContext()));
        timeLineList.setAdapter(timeTableViewModel.getAdapter());

        timeTableViewModel.getDienstRegeling(haltenummer, halteentiteit).observe(this, timeTableItems -> timeTableViewModel.getAdapter().setTimeTableItems(timeTableItems));

        return root;
    }
}