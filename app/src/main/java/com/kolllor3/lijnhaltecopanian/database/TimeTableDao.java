package com.kolllor3.lijnhaltecopanian.database;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimeTableDao {

    @Query("SELECT * FROM timeTable WHERE dayOfWeek=:dayOfWeek AND haltenummer = :haltenummer")
    LiveData<List<TimeTableItem>> getTimeTableFromDayOfWeek(int haltenummer, int dayOfWeek);

    @Query("SELECT * FROM timeTable WHERE dayOfWeek=:dayOfWeek AND haltenummer = :haltenummer")
    List<TimeTableItem> getTimeTableFromDayOfWeekToList(int haltenummer, int dayOfWeek);

    @Query("SELECT * FROM timeTable")
    LiveData<List<TimeTableItem>> getAll();

    @Query("DELETE FROM timeTable WHERE dayOfWeek=:dayOfWeek AND haltenummer=:haltenummer")
    void deleteTimeTableFromDayOfWeek(int dayOfWeek, int haltenummer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeTableItem... items);

    @Query("DELETE FROM timeTable")
    void deleteAll();

    @Update
    void update(TimeTableItem... item);
}
