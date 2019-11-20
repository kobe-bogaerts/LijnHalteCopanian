package com.kolllor3.lijnhaltecopanian.backgroundTasks;

import com.kolllor3.lijnhaltecopanian.viewModel.TimeTableViewModel;

import java.util.TimerTask;

public class RealTimeTimerTask extends TimerTask {

    private TimeTableViewModel viewModel;
    private int haltenummer,halteentiteit;

    public RealTimeTimerTask(TimeTableViewModel viewModel, int haltenummer, int halteentiteit) {
        this.viewModel = viewModel;
        this.haltenummer = haltenummer;
        this.halteentiteit = halteentiteit;
    }

    @Override
    public void run() {
        viewModel.getRealTimeData(haltenummer, halteentiteit);
    }
}
