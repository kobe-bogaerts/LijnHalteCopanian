package com.kolllor3.lijnhaltecopanian.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kolllor3.lijnhaltecopanian.model.FavoriteHalte;
import com.kolllor3.lijnhaltecopanian.model.Halte;
import com.kolllor3.lijnhaltecopanian.util.Utilities;

@Database(entities = {Halte.class, FavoriteHalte.class}, version = 2)
public abstract class HalteDataBase extends RoomDatabase {

    public abstract HalteDao getHalteDao();
    public abstract FavoriteHalteDao getFavoriteHalteDao();

    private static HalteDataBase INSTANCE;

    static synchronized HalteDataBase getDatabase(Context context) {
        if (Utilities.isNull(INSTANCE )) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), HalteDataBase.class, "halte.db")
                    .createFromAsset("databases/halte.db")
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("CREATE TABLE FavoriteHalte(haltenummer INTEGER)");
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
}
