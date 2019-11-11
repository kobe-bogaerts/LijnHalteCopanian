package com.kolllor3.lijnhaltecopanian.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.kolllor3.lijnhaltecopanian.model.Halte;

import java.util.List;

@Dao
public interface FavoriteHalteDao {

    @Query("SELECT * FROM Halte WHERE haltenummer in(SELECT haltenummer FROM favoritehalte)")
    LiveData<List<Halte>> getFavoriteHaltes();

    @Query("INSERT INTO favoritehalte(haltenummer) SELECT :id WHERE NOT EXISTS(SELECT 1 FROM favoritehalte WHERE haltenummer = :id)")
    void addFavoriteHalte(int id);

    @Query("DELETE FROM favoritehalte WHERE haltenummer=:id")
    void deleteFavoriteHalte(int id);
}
