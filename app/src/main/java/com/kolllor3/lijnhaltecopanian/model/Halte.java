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


}
