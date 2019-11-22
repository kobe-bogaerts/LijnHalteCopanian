package com.kolllor3.lijnhaltecopanian.model;

import androidx.room.Entity;
import androidx.room.Ignore;

import com.kolllor3.lijnhaltecopanian.util.ColorFactory;

@Entity(tableName = "lijnKleuren", primaryKeys = {"lijn","entiteit"})
public class LijnItem {

    private int lijn;
    private int entiteit;
    private String kleurCodeVoor;
    private String kleurCodeAchter;

    @Ignore
    public LijnItem(int lijn, int entiteit) {
        this.lijn = lijn;
        this.entiteit = entiteit;
    }

    public LijnItem(int lijn, int entiteit, String kleurCodeVoor, String kleurCodeAchter) {
        this.lijn = lijn;
        this.entiteit = entiteit;
        this.kleurCodeVoor = kleurCodeVoor;
        this.kleurCodeAchter = kleurCodeAchter;
    }

    public int getLijn() {
        return lijn;
    }

    public void setLijn(int lijn) {
        this.lijn = lijn;
    }

    public int getEntiteit() {
        return entiteit;
    }

    public void setEntiteit(int entiteit) {
        this.entiteit = entiteit;
    }

    public String getKleurCodeVoor() {
        return kleurCodeVoor;
    }

    public void setKleurCodeVoor(String kleurCodeVoor) {
        this.kleurCodeVoor = kleurCodeVoor;
    }

    public String getKleurCodeAchter() {
        return kleurCodeAchter;
    }

    public void setKleurCodeAchter(String kleurCodeAchter) {
        this.kleurCodeAchter = kleurCodeAchter;
    }

    public int getKleurAchter(){
        return ColorFactory.getHexColorBackFromColorCode(getKleurCodeAchter());
    }

    public int getKleurVoor(){
        return ColorFactory.getHexColorFrontFromColorCode(getKleurCodeVoor());
    }
}
