package com.kolllor3.lijnhaltecopanian.database;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TimeTableDao {

    @Query("SELECT * FROM timeTable WHERE dayOfWeek=:dayOfWeek AND haltenummer = :haltenummer")
    LiveData<List<TimeTableItem>> getTimeTableFromDayOfWeek(int haltenummer, int dayOfWeek);

    @Query("SELECT * FROM timeTable")
    LiveData<List<TimeTableItem>> getAll();

    @Query("DELETE FROM timeTable WHERE dayOfWeek=:dayOfWeek")
    void deleteTimeTableFromDayOfWeek(int dayOfWeek);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeTableItem... items);

    @Query("DELETE FROM timeTable")
    void deleteAll();
}
