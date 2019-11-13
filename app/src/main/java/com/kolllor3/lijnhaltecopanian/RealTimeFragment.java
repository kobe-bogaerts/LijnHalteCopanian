package com.kolllor3.lijnhaltecopanian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;


public class RealTimeFragment extends Fragment {

    private static final String ARG_HALTE_NUMBER = "halte_number";
    private static final String ARG_ENTITEIT_NUMBER = "entiteit_number";

    private TimeTableViewModel timeTableViewModel;
    private int haltenummer;
    private int halteentiteit;

    public RealTimeFragment() {
        // Required empty public constructor
    }

    public static RealTimeFragment newInstance(int haltenummer, int halteentiteit) {
        RealTimeFragment fragment = new RealTimeFragment();
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
        if (getArguments() != null) {
            haltenummer = getArguments().getInt(ARG_HALTE_NUMBER);
            halteentiteit = getArguments().getInt(ARG_ENTITEIT_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_real_time, container, false);

        return root;
    }
}
