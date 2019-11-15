package com.kolllor3.lijnhaltecopanian.database;

import android.content.Context;

import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TimeTableItem.class}, version = 1)
public abstract class TimeTableDataBase extends RoomDatabase {

    public abstract TimeTableDao getTimeTableDao();

    private static TimeTableDataBase INSTANCE;

    static synchronized TimeTableDataBase getDatabase(Context context) {
        if (Utilities.isNull(INSTANCE )) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TimeTableDataBase.class, "timeTable.db")
                    .build();
        }
        return INSTANCE;
    }
}
