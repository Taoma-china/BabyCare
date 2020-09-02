package com.example.babycare.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserAccount.class}, version = 1, exportSchema = false)
public abstract class UserAccountDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    public abstract UserAccountDao userAccountDao();
    public static UserAccountDatabase INSTANCE;
    private static final Object LOCK = new Object();

    public static UserAccountDatabase getAppDatabase(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserAccountDatabase.class, DATABASE_NAME).build();
                }
            }


        }

        return INSTANCE;

    }

    public static void destroyInstance()
    {
        INSTANCE = null;
    }

}
