package com.kolllor3.lijnhaltecopanian.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RealTimeItem {

    private int lijnnummer;
    private boolean isRealTime;
    private Calendar dienstRegelingTime;
    private Calendar realTime;
    private String besteming;

    public RealTimeItem(int lijnnummer, boolean isRealTime, Calendar dienstRegelingTime, Calendar realTime, String besteming) {
        this.lijnnummer = lijnnummer;
        this.isRealTime = isRealTime;
        this.dienstRegelingTime = dienstRegelingTime;
        this.realTime = realTime;
        this.besteming = besteming;
    }

    public int getLijnnummer() {
        return lijnnummer;
    }

    public void setLijnnummer(int lijnnummer) {
        this.lijnnummer = lijnnummer;
    }

    public boolean isRealTime() {
        return isRealTime;
    }

    public void setRealTime(boolean realTime) {
        isRealTime = realTime;
    }

    public Calendar getDienstRegelingTime() {
        return dienstRegelingTime;
    }

    public void setDienstRegelingTime(Calendar dienstRegelingTime) {
        this.dienstRegelingTime = dienstRegelingTime;
    }

    public Calendar getRealTime() {
        return realTime;
    }

    public void setRealTime(Calendar realTime) {
        this.realTime = realTime;
    }

    public String getBesteming() {
        return besteming;
    }

    public void setBesteming(String besteming) {
        this.besteming = besteming;
    }

    public String getTimeTillString(){
        if(isRealTime()){
            long diff = getRealTime().getTime().getTime() - new Date().getTime();
            return String.format(Locale.getDefault(), "%d'", TimeUnit.MILLISECONDS.toMinutes(diff));
        }
        return String.format(Locale.getDefault(), "%d:%d", getDienstRegelingTime().get(Calendar.HOUR_OF_DAY), getDienstRegelingTime().get(Calendar.MINUTE));
    }
}
