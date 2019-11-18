package com.kolllor3.lijnhaltecopanian.model;

import java.util.Calendar;

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
}
