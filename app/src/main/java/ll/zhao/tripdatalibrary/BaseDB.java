package ll.zhao.tripdatalibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/3/21.
 */

public class BaseDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "TripToYou.db";

    public static final String  TRIP_TABLE = "TRIP";
    public static final String  ID_TRIP_COLUMN = "id";
    public static final String  TRIP_NAME_TRIP_COLUMN = "trip_name";
    public static final String  MEMO_TRIP_COLUMN = "memo";
    public static final String  LOCATION_TRIP_COLUMN = "location";
    public static final String  COUNT_TRIP_COLUMN = "count";
    public static final String  START_TIME_TRIP_COLUMN = "start_time";
    public static final String  END_TIME_TRIP_COLUMN = "end_time";
    public static final String  STATE_TRIP_COLUMN = "state";
    public static final String  ADMIN_TRIP_COLUMN = "admin";
    public static final String  LOCATION_ID_TRIP_COLUMN = "location_id";
    public static final String  IMAGE1_TRIP_COLUMN = "image1";
    public static final String  IMAGE2_TRIP_COLUMN = "image2";

    public static final String  LOCATION_TABLE = "LOCATION";
    public static final String  ID_LOCATION_COLUMN = "id";
    public static final String  NAME_LOCATION_COLUMN = "name";
    public static final String  X_LOCATION_COLUMN = "x";
    public static final String  Y_LOCATION_COLUMN = "y";
    public static final String  STATE_LOCATION_COLUMN = "state";

    public static final String  PERSON_TABLE = "PERSON";
    public static final String  TEL_PERSON_COLUMN = "tel";
    public static final String  NAME_PERSON_COLUMN = "name";
    public static final String  ICON_PERSON_COLUMN = "icon";
    public static final String  TYPE_PERSON_COLUMN = "type";


    public static final String  TRIP_PERSON_TABLE = "TRIP_PERSON";
    public static final String  TRIP_ID_TRIP_PERSON_COLUMN = "trip_id";
    public static final String  PERSON_TEL_TRIP_PERSON_COLUMN = "person_tel";
    public static final String  MONEY_TRIP_PERSON_COLUMN = "money";

    public static final String  DETAIL_TABLE = "DETAIL";
    public static final String  DETAIL_ID_DETAIL_COLUMN = "detail_id";
    public static final String  TRIP_ID_DETAIL_COLUMN = "trip_id";
    public static final String  MONEY_DETAIL_COLUMN = "money";
    public static final String  CATEGORY_DETAIL_COLUMN = "category";
    public static final String  DETAIL_DETAIL_COLUMN = "detail";

    public static final String  DETAIL_PERSON_TABLE = "DETAIL_PERSON";
    public static final String  DETAIL_ID_DETAIL_PERSON_COLUMN = "detail_id";
    public static final String  PERSON_TEL_DETAIL_PERSON_COLUMN = "person_tel";
    public static final String  MONEY_DETAIL_PERSON_COLUMN = "money";

    public static final String  FOOTPRINT_TABLE = "FOOTPRINT";
    public static final String  FOOTPRINT_ID_COLUMN = "footprint_id";
    public static final String  FOOTPRINT_TIME_COLUMN = "footprint_time";
    public static final String  FOOTPRINT_LOCATION_COLUMN = "footprint_location";
    public static final String  TRIP_ID_FOOTPRINT_COLUMN = "trip_id";

    public String sqlFootprint =  "CREATE TABLE IF NOT EXISTS " +  FOOTPRINT_TABLE + " (" +
            FOOTPRINT_ID_COLUMN + " TEXT PRIMARY KEY NOT NULL,  " +
            FOOTPRINT_TIME_COLUMN + " TEXT NOT NULL,  " +
            FOOTPRINT_LOCATION_COLUMN + " TEXT NOT NULL,  " +
            TRIP_ID_FOOTPRINT_COLUMN + " TEXT)";

    public String sqlDetail = "CREATE TABLE IF NOT EXISTS " +  DETAIL_TABLE + " (" +
            DETAIL_ID_DETAIL_COLUMN + " TEXT PRIMARY KEY NOT NULL,  " +
            TRIP_ID_DETAIL_COLUMN + " TEXT NOT NULL,  " +
            MONEY_DETAIL_COLUMN + " DOUBLE NOT NULL,  " +
            CATEGORY_DETAIL_COLUMN + " INTEGER NOT NULL,  " +
            DETAIL_DETAIL_COLUMN + " TEXT)";
    public String sqlDetailPerson = "CREATE TABLE IF NOT EXISTS " + DETAIL_PERSON_TABLE + " (" +
            DETAIL_ID_DETAIL_PERSON_COLUMN + " TEXT NOT NULL,  " +
            PERSON_TEL_DETAIL_PERSON_COLUMN + " TEXT NOT NULL, " +
            MONEY_DETAIL_PERSON_COLUMN  + " DOUBLE NOT NULL)";
    public String sqlLocation = "CREATE TABLE IF NOT EXISTS " + LOCATION_TABLE + " (" +
            ID_LOCATION_COLUMN + " TEXT PRIMARY KEY NOT NULL, " +
            NAME_LOCATION_COLUMN + " TEXT NOT NULL, " +
            X_LOCATION_COLUMN + " DOUBLE NOT NULL, " +
            Y_LOCATION_COLUMN + " DOUBLE NOT NULL,  " +
            STATE_LOCATION_COLUMN + " INT)";
    public String sqlPerson = "CREATE TABLE IF NOT EXISTS " +  PERSON_TABLE + " (" +
            TEL_PERSON_COLUMN + " TEXT PRIMARY KEY NOT NULL,  " +
            NAME_PERSON_COLUMN  + " TEXT NOT NULL,  " +
            TYPE_PERSON_COLUMN + " TEXT NOT NULL,  " +
            ICON_PERSON_COLUMN + " BLOB)";
    public String sqlTrip = "CREATE TABLE IF NOT EXISTS " +  TRIP_TABLE + " ( " +
            ID_TRIP_COLUMN + " TEXT PRIMARY KEY NOT NULL,  " +
            MEMO_TRIP_COLUMN + " TEXT,  " +
            TRIP_NAME_TRIP_COLUMN + " TEXT NOT NULL,  " +
            LOCATION_TRIP_COLUMN + " TEXT,  " +
            COUNT_TRIP_COLUMN + " DOUBLE NOT NULL,  " +
            STATE_TRIP_COLUMN + " TEXT,  " +
            END_TIME_TRIP_COLUMN + " TEXT,  " +
            START_TIME_TRIP_COLUMN + " INTEGER,  " +
            ADMIN_TRIP_COLUMN  + " TEXT NOT NULL,  " +
            LOCATION_ID_TRIP_COLUMN + " TEXT,  " +
            IMAGE1_TRIP_COLUMN + " BLOB,  " +
            IMAGE2_TRIP_COLUMN + " BLOB)";
    public String sqlTripPerson = "CREATE TABLE IF NOT EXISTS  " + TRIP_PERSON_TABLE + " (" +
            TRIP_ID_TRIP_PERSON_COLUMN + " TEXT NOT NULL,  " +
            PERSON_TEL_TRIP_PERSON_COLUMN + " TEXT NOT NULL,  " +
            MONEY_TRIP_PERSON_COLUMN + " DOUBLE NOT NULL)";

    public BaseDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlDetail);
        db.execSQL(sqlDetailPerson);
        db.execSQL(sqlLocation);
        db.execSQL(sqlPerson);
        db.execSQL(sqlTrip);
        db.execSQL(sqlTripPerson);
        db.execSQL(sqlFootprint);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
