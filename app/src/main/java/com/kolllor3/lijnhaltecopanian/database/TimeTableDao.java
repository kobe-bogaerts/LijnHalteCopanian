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

    @Query("SELECT * FROM timeTable WHERE dayOfWeek=:dayOfWeek")
    LiveData<List<TimeTableItem>> getTimeTableFromDayOfWeek(int dayOfWeek);

    @Query("DELETE FROM timeTable WHERE dayOfWeek=:dayOfWeek")
    void deleteTimeTableFromDayOfWeek(int dayOfWeek);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeTableItem... items);
}
