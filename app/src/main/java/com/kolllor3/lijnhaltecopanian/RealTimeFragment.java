package com.kolllor3.lijnhaltecopanian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.lijnhaltecopanian.backgroundTasks.RealTimeTimerTask;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;

import java.util.Timer;


public class RealTimeFragment extends Fragment {

    private static final String ARG_HALTE_NUMBER = "halte_number";
    private static final String ARG_ENTITEIT_NUMBER = "entiteit_number";

    private TimeTableViewModel timeTableViewModel;
    private int haltenummer;
    private int halteentiteit;
    private Timer timer;

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

        if(Utilities.isNull(savedInstanceState))
            timeTableViewModel.init();

        if (getArguments() != null) {
            haltenummer = getArguments().getInt(ARG_HALTE_NUMBER);
            halteentiteit = getArguments().getInt(ARG_ENTITEIT_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_real_time, container, false);

        RecyclerView timeLineList = root.findViewById(R.id.timeline);
        timeLineList.setLayoutManager(new LinearLayoutManager(getContext()));
        timeLineList.setAdapter(timeTableViewModel.getRealTimeAdapter());

        LinearLayout loadingView = root.findViewById(R.id.loading_icon);

        timer = new Timer();

        timeTableViewModel.getRealTimeData(haltenummer, halteentiteit).observe(this, realTimeItems -> {
            if(realTimeItems.size() > 0) {
                timeTableViewModel.cancelGetDienstregelingRequest();
                loadingView.setVisibility(View.GONE);
            }else{
                loadingView.setVisibility(View.VISIBLE);
            }
            timeTableViewModel.getRealTimeAdapter().setRealTimeItems(realTimeItems);
        });

        RealTimeTimerTask realTimeTimerTask = new RealTimeTimerTask(timeTableViewModel, haltenummer, halteentiteit);
        timer.schedule(realTimeTimerTask, 50000, 60000);

        return root;
    }

    @Override
    public void onDestroy() {
        timer.purge();
        super.onDestroy();
    }
}
