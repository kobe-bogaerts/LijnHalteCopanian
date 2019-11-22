package com.kolllor3.lijnhaltecopanian;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kolllor3.lijnhaltecopanian.interfaces.Constants;
import com.kolllor3.lijnhaltecopanian.model.LijnItem;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;
import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;

import java.util.HashMap;
import java.util.Map;


public class TimeTableFragment extends Fragment implements Constants {

    private static final String ARG_HALTE_NUMBER = "halte_number";
    private static final String ARG_ENTITEIT_NUMBER = "entiteit_number";

    private TimeTableViewModel timeTableViewModel;
    private int haltenummer;
    private int halteentiteit;
    private Fragment fragment;


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

        setHasOptionsMenu(true);

        fragment = this;

        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel.class);
        timeTableViewModel.cancelGetDienstregelingRequest();
        if(Utilities.isNull(savedInstanceState))
            timeTableViewModel.init();

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

        LinearLayout loadingView = root.findViewById(R.id.loading_icon);

        timeTableViewModel.getDienstRegeling(haltenummer, halteentiteit).observe(fragment, timeTableItems -> {
            if(timeTableItems.size() > 0) {
                timeTableViewModel.cancelGetDienstregelingRequest();
                loadingView.setVisibility(View.GONE);
                Map<String, Integer> lijnen = new HashMap<>();
                for (TimeTableItem item: timeTableItems) {
                    String lijnNummer = String.valueOf(item.getLijnnummer());
                    if (!lijnen.containsKey(lijnNummer)){
                        lijnen.put(lijnNummer, item.getLijnnummer());
                    }
                }

                timeTableViewModel.getLijnItems(lijnen.values().toArray(new Integer[0]), halteentiteit).observe(fragment, lijnItems -> {
                    SparseArray<LijnItem> lijnItemMap = new SparseArray<>();
                    for (LijnItem i:lijnItems) {
                        lijnItemMap.put(i.getLijn(), i);
                    }
                    timeTableViewModel.getAdapter().setLijnItemMap(lijnItemMap);
                });
            }else{
                loadingView.setVisibility(View.VISIBLE);
            }
            timeTableViewModel.getAdapter().setTimeTableItems(timeTableItems);
        });

//        //Todo: remove deze debug
//        timeTableViewModel.getAll().observe(this, timeTableItems -> {
//            for (TimeTableItem item: timeTableItems) {
//                LogUtils.logI("db data", item.toString());
//            }
//        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.halte_timetable_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                if(Utilities.isNotNull(getActivity()))
                    getActivity().finish();
                return true;
            case R.id.refresh_manual:
                timeTableViewModel.updateDienstRegelingManul(haltenummer, halteentiteit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}