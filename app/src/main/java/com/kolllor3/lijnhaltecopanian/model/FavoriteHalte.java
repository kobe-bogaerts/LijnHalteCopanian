package com.kolllor3.lijnhaltecopanian.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoriteHalte")
public class FavoriteHalte {

    @PrimaryKey
    private int haltenummer;

    public int getHaltenummer() {
        return haltenummer;
    }

    public void setHaltenummer(int haltenummer) {
        this.haltenummer = haltenummer;
    }
}
