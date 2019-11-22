package com.kolllor3.lijnhaltecopanian.database;

import android.content.Context;

import com.kolllor3.lijnhaltecopanian.model.LijnItem;
import com.kolllor3.lijnhaltecopanian.model.TimeTableItem;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TimeTableItem.class, LijnItem.class}, version = 2)
public abstract class TimeTableDataBase extends RoomDatabase {

    public abstract TimeTableDao getTimeTableDao();
    public abstract LijnKleurenDao getLijnKleurenDao();

    private static TimeTableDataBase INSTANCE;

    public static synchronized TimeTableDataBase getDatabase(Context context) {
        if (Utilities.isNull(INSTANCE )) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TimeTableDataBase.class, "timeTable.db").addMigrations(new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                    database.execSQL("CREATE TABLE IF NOT EXISTS `lijnKleuren` (`lijn` INTEGER NOT NULL, `entiteit` INTEGER NOT NULL, `kleurCodeVoor` TEXT, `kleurCodeAchter` TEXT, PRIMARY KEY(`lijn`, `entiteit`))");
                }
            }).build();
        }
        return INSTANCE;
    }
}
