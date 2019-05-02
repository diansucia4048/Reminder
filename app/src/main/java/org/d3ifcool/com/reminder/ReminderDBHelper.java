package org.d3ifcool.com.reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import org.d3ifcool.com.reminder.ReminderContract.ReminderEntry;

public class ReminderDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "reminder.db";
    private static final int DATABASE_VERSION = 1;

    public ReminderDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_REMINDER_TABLE = "CREATE TABLE " + ReminderEntry.TABLE_NAME + " ("
                + ReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReminderEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + ReminderEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + ReminderEntry.COLUMN_IMAGE + " TEXT NOT NULL, "
                + ReminderEntry.COLUMN_TIME + "TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_REMINDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
