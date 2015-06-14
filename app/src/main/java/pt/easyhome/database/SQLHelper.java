package pt.easyhome.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "houseControl.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HOUSE = "house";
    public static final String TABLE_ROOM = "room";
    public static final String TABLE_DEVICE = "device";
    public static final String TABLE_PROFILE = "profile";

    public static final String COLUMN_HOUSE_IP_ADDRESS = "houseip";
    public static final String COLUMN_HOUSE_PORT = "houseport";
    public static final String COLUMN_HOUSE_MAC_ADDRESS = "housemac";
    public static final String COLUMN_SERVER_IP_ADDRESS = "serverip";
    public static final String COLUMN_SERVER_PORT = "serverport";
    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_HOUSE_ID = "house_id";

    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_ROOM_ID = "room_id";
    public static final String COLUMN_USE = "use";


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";

    /*------------------------------------------------------------------------------------------------*/

    public static final String CREATE_TABLE_HOUSE =
            "create table IF NOT EXISTS " + TABLE_HOUSE + " ("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_NAME + " text not null, "
                    + COLUMN_HOUSE_IP_ADDRESS + " text not null, "
                    + COLUMN_HOUSE_PORT + " integer not null, "
                    + COLUMN_HOUSE_MAC_ADDRESS + " text not null, "
                    + COLUMN_SERVER_IP_ADDRESS + " text not null, "
                    + COLUMN_SERVER_PORT + " integer not null, "
                    + COLUMN_PASSWORD + " text not null);";

    public static final String CREATE_TABLE_ROOM =
            "create table IF NOT EXISTS " + TABLE_ROOM + " ("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_NAME + " text not null, "
                    + COLUMN_HOUSE_ID + " integer references "
                    + TABLE_HOUSE + "(" + COLUMN_ID + ") not null);";

    public static final String CREATE_TABLE_DEVICE =
            "create table IF NOT EXISTS " + TABLE_DEVICE + " ("
                    + COLUMN_ID + " integer primary key, "
                    + COLUMN_NAME + " text not null, "
                    + COLUMN_TYPE + " integer not null, "
                    + COLUMN_VALUE + " real not null, "
                    + COLUMN_USE + " integer, "
                    + COLUMN_ROOM_ID + " integer references "
                    + TABLE_ROOM + "(" + COLUMN_ID + ") not null);";

    public static final String CREATE_TABLE_PROFILE = "create table IF NOT EXISTS " + TABLE_PROFILE + " ("
            + COLUMN_ID + " integer primary key, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_TYPE + " integer not null, "
            + COLUMN_USE + " integer, "
            + COLUMN_ROOM_ID + " integer references "
            + TABLE_ROOM + "(" + COLUMN_ID + ") not null, "
            + "value0 real, "
            + "value1 real, "
            + "value2 real, "
            + "value3 real, "
            + "value4 real, "
            + "value5 real, "
            + "value6 real, "
            + "value7 real, "
            + "value8 real, "
            + "value9 real, "
            + "value10 real, "
            + "value11 real, "
            + "value12 real, "
            + "value13 real, "
            + "value14 real, "
            + "value15 real, "
            + "value16 real, "
            + "value17 real, "
            + "value18 real, "
            + "value19 real, "
            + "value20 real, "
            + "value21 real, "
            + "value22 real, "
            + "value23 real);";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROOM);
        db.execSQL(CREATE_TABLE_DEVICE);
        db.execSQL(CREATE_TABLE_HOUSE);
        db.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
}