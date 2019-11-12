package com.kolllor3.lijnhaltecopanian.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalTime;
import java.util.Calendar;

@Entity(tableName = "timeTable")
public class TimeTableItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int haltenummer;
    private int lijnnummer;
    private int hour;
    private int minute;
    private int dayOfWeek;
    private String bestemming;
    private boolean isVacation;

    @Ignore
    public TimeTableItem(int haltenummer, int lijnnummer, int hour, int minute, int dayOfWeek, String bestemming, boolean isVacation) {
        this.haltenummer = haltenummer;
        this.lijnnummer = lijnnummer;
        this.hour = hour;
        this.minute = minute;
        this.dayOfWeek = dayOfWeek;
        this.bestemming = bestemming;
        this.isVacation = isVacation;
    }

    public int getHaltenummer() {
        return haltenummer;
    }

    public void setHaltenummer(int haltenummer) {
        this.haltenummer = haltenummer;
    }

    public int getLijnnummer() {
        return lijnnummer;
    }

    public void setLijnnummer(int lijnnummer) {
        this.lijnnummer = lijnnummer;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getBestemming() {
        return bestemming;
    }

    public void setBestemming(String bestemming) {
        this.bestemming = bestemming;
    }

    public boolean isVacation() {
        return isVacation;
    }

    public void setVacation(boolean vacation) {
        isVacation = vacation;
    }

    @Ignore
    @NonNull
    @Override
    public String toString() {
        return String.format("%d, lijn: %d, %d%d, %s || %s", haltenummer, lijnnummer, hour, minute, dayOfWeek, bestemming);
    }
}
