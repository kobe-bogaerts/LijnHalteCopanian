package com.kolllor3.lijnhaltecopanian.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kolllor3.lijnhaltecopanian.model.LijnItem;

import java.util.List;

@Dao
public interface LijnKleurenDao {

    @Insert
    void insert(LijnItem... items);

    @Query("SELECT * FROM lijnKleuren WHERE entiteit=:entiteit AND lijn IN (:lijnen)")
    List<LijnItem> getLijnItems(int[] lijnen, int entiteit);

}
