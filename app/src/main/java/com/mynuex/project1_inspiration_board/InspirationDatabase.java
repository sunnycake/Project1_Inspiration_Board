package com.mynuex.project1_inspiration_board;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static okhttp3.internal.Internal.instance;

@Database(entities = {Inspiration.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class InspirationDatabase extends RoomDatabase {
    private static final String LOG_TAG = "DATABASE_LOG";

    // Volatile can only be reference from main memory
    private static volatile InspirationDatabase INSTANCE;
    // Abstract method that returns an instance of the InspirationDao interface
    public abstract InspirationDao mInspirationDao();
    // Creating the database object
    static InspirationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InspirationDatabase.class) {
                Log.d(LOG_TAG, "Creating new database instance");
                if (INSTANCE == null) { // If still null then create database reference
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InspirationDatabase.class, "inspiration_database")
                            .fallbackToDestructiveMigration()
                            .build();
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            InspirationDatabase.class, "Inspiration").build();
                }
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }

}
