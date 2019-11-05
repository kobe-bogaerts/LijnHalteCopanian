package com.kolllor3.lijnhaltecopanian.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Halte")
public class Halte {

    @PrimaryKey
    private int haltenummer;

    private int entiteitnummer;

    private String omschrijving;

    private double lat;

    private double lng;

    public Halte(int haltenummer, int entiteitnummer, String omschrijving, double lat, double lng) {
        this.haltenummer = haltenummer;
        this.entiteitnummer = entiteitnummer;
        this.omschrijving = omschrijving;
        this.lat = lat;
        this.lng = lng;
    }

    public int getHaltenummer() {
        return haltenummer;
    }

    public int getEntiteitnummer() {
        return entiteitnummer;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
