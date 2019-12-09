package com.kolllor3.lijnhaltecopanian.model;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import androidx.core.content.ContextCompat;

import com.kolllor3.lijnhaltecopanian.App;
import com.kolllor3.lijnhaltecopanian.R;

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
    private boolean isGeschrapt;

    public RealTimeItem(int lijnnummer, boolean isRealTime, boolean isGeschrapt, Calendar dienstRegelingTime, Calendar realTime, String besteming) {
        this.lijnnummer = lijnnummer;
        this.isRealTime = isRealTime;
        this.dienstRegelingTime = dienstRegelingTime;
        this.realTime = realTime;
        this.besteming = besteming;
        this.isGeschrapt = isGeschrapt;
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

    public SpannableString getTimeTillString(){
        if (isRealTime() && !isGeschrapt()) {
            long diff = getRealTime().getTime().getTime() - new Date().getTime();
            return new SpannableString(String.format(Locale.getDefault(), "%d'", TimeUnit.MILLISECONDS.toMinutes(diff)));
        }
        SpannableString timeString = new SpannableString(String.format(Locale.getDefault(), "%tH:%tM", getDienstRegelingTime(), getDienstRegelingTime()));
        if(isGeschrapt())
            timeString.setSpan(new StrikethroughSpan(), 0, timeString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return timeString;
    }

    public boolean isGeschrapt() {
        return isGeschrapt;
    }

    public void setGeschrapt(boolean geschrapt) {
        isGeschrapt = geschrapt;
    }

    public String getGescraptString(){
        return App.getInstance().getString(R.string.no_drive_string);
    }

    public int getBasicColor(){
        return isGeschrapt() ? ContextCompat.getColor(App.getInstance(), android.R.color.holo_red_light) : ContextCompat.getColor(App.getInstance(), android.R.color.black);
    }
}
