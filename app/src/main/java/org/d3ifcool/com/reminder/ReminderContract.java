package org.d3ifcool.com.reminder;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ReminderContract {
    private ReminderContract(){}

    public static final String CONTENT_AUTHORITY = "org.d3ifcool.com.reminder";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);
    public static final String PATH_REMINDERS = "reminders";

    public static final class ReminderEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_REMINDERS);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REMINDERS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REMINDERS;
        public final static String TABLE_NAME = "reminder_app";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_DESCRIPTION = "description";
        public final static String COLUMN_IMAGE = "image";
        public final static String COLUMN_TIME = "time";

    }

}
