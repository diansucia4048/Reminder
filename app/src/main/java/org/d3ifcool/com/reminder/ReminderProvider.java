package org.d3ifcool.com.reminder;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.d3ifcool.com.reminder.ReminderContract.ReminderEntry;

public class ReminderProvider extends ContentProvider {
    public static final String LOG_TAG = ReminderProvider.class.getSimpleName();
    private static final int REMINDERS = 100;
    private static final int REMINDERS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_REMINDERS, REMINDERS);
        sUriMatcher.addURI(ReminderContract.CONTENT_AUTHORITY, ReminderContract.PATH_REMINDERS + "/#", REMINDERS_ID);
    }

    private ReminderDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ReminderDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case REMINDERS:
                cursor = database.query(ReminderContract.ReminderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case REMINDERS_ID:
                selection = ReminderContract.ReminderEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(ReminderEntry.TABLE_NAME, projection,selection,selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case REMINDERS:
                return ReminderEntry.CONTENT_LIST_TYPE;
            case REMINDERS_ID:
                return ReminderEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case REMINDERS:
                return insertReminder(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertReminder(Uri uri, ContentValues values) {
        String name = values.getAsString(ReminderEntry.COLUMN_NAME);
        if (name == null){
            throw new IllegalArgumentException("Receipts requires a name");
        }

        String desc = values.getAsString(ReminderEntry.COLUMN_DESCRIPTION);
        if (desc == null){
            throw new IllegalArgumentException("Receipts requires a description");
        }

        String image = values.getAsString(ReminderEntry.COLUMN_IMAGE);
        if (image == null){
            throw new IllegalArgumentException("Receipts requires an image");
        }

        String time = values.getAsString(ReminderEntry.COLUMN_TIME);
        if (time == null){
            throw new IllegalArgumentException("Receipts requires a time");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(ReminderEntry.TABLE_NAME, null, values);

        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final  int match = sUriMatcher.match(uri);

        switch (match){
            case REMINDERS:
                rowsDeleted = database.delete(ReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REMINDERS_ID:
                selection = ReminderEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ReminderEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @NonNull ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}

