package com.kolllor3.lijnhaltecopanian.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

@Dao
public interface HalteDao {

    @Query("SELECT * FROM Halte WHERE haltenummer=:id")
    Halte getHalte(int id);


    @Query("Select * from Halte order by (abs(lng -(:lng)) +( abs(lat-:lat)*2.1)) Limit 8;")
    LiveData<List<Halte>> getClosestHaltes(double lat, double lng);

    @Query("SELECT * FROM Halte WHERE entiteitnummer=:entiteitId")
    List<Halte> getAllHaltesByEntiteit(int entiteitId);

    @Query("SELECT * FROM Halte WHERE haltenummer in(:ids)")
    List<Halte> getHaltesById(int[] ids);

    @Query("SELECT * FROM Halte WHERE omschrijving LIKE :likeString")
    LiveData<List<Halte>> getHalteByName(String likeString);

    @Insert
    void insertAll(Halte... haltes);


}
