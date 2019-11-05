package com.kolllor3.lijnhaltecopanian.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

@Database(entities = Halte.class, version = 1)
public abstract class HalteDataBase extends RoomDatabase {

    public abstract HalteDao getHalteDao();

    private static HalteDataBase INSTANCE;

    static synchronized HalteDataBase getDatabase(Context context) {
        if (Utilities.isNull(INSTANCE )) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HalteDataBase.class, "halte.db")
                    .createFromAsset("databases/halte.db")
                    .build();
        }
        return INSTANCE;
    }
}
