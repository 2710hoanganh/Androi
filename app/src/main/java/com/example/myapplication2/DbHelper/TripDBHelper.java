package com.example.myapplication2.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication2.Trip;
import com.example.myapplication2.Expensess;

import java.util.ArrayList;

public class TripDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "trips";
    public static final int DATABASE_VERSION = 1;


    public TripDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Trips.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(Expenses.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Trips.SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(Expenses.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
    //reset
    public void reset() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        onUpgrade(myDB,0,0);
    }

    //insert trip
    public long insert(String name, String destination, String date , String description , String accommodation , String vehicle ,int rra){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(Trips.COL_NAME_NAME ,name);
        contentValues.put(Trips.COL_NAME_DESTINATION ,destination);
        contentValues.put(Trips.COL_NAME_DATE,date);
        contentValues.put(Trips.COL_NAME_DESCRIPTION ,description);
        contentValues.put(Trips.COL_NAME_ACCOMMODATION,accommodation);
        contentValues.put(Trips.COL_NAME_VEHICLE,vehicle);
        contentValues.put(Trips.COL_NAME_RRA,rra);



        return myDB.insert(Trips.TABLE_NAME ,null ,contentValues);

    }
    //insert expenses
    public long insertExpenses(String type , String date ,String time ,String amount,String comment ,int trip_id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Expenses.COL_NAME_TYPE ,type);
        contentValues.put(Expenses.COL_NAME_DATE ,date);
        contentValues.put(Expenses.COL_NAME_TIME,time);
        contentValues.put(Expenses.COL_NAME_AMOUNT,amount);
        contentValues.put(Expenses.COL_NAME_CMT ,comment);
        contentValues.put(Expenses.COL_NAME_TRIP_ID,trip_id);


        return myDB.insert(Expenses.TABLE_NAME ,null ,contentValues);
    }
    //delete Trip
    public long delete (long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Trips.TABLE_NAME,Trips.COL_NAME_ID + " = ?" ,new String[]{Long.toString(id)});

    }
    //list expenses by trip id
    public ArrayList<Expensess> getListExpenses(int id){
        ArrayList<Expensess> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Expenses.TABLE_NAME + " WHERE " + Expenses.COL_NAME_TRIP_ID + "=" + id,null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor . getCount () ; i ++){
            expenses.add ( new Expensess(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
            cursor . moveToNext () ;

        }
        cursor.close () ;
        db.close () ;

        return expenses;
    }

    public ArrayList<Expensess> listExpenses(){
        ArrayList<Expensess> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Expenses.TABLE_NAME ,null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor . getCount () ; i ++){
            expenses.add ( new Expensess(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6)));
            cursor . moveToNext () ;

        }
        cursor.close () ;
        db.close () ;

        return expenses;
    }

    //delete expenses
    public long deleteExpenses (long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Expenses.TABLE_NAME,Expenses.COL_NAME_ID + " = ?" ,new String[]{Long.toString(id)});

    }

    //list trip
    public ArrayList<Trip> getListTrip(){
        ArrayList<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Trips.TABLE_NAME ,null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor . getCount () ; i ++){
            trips.add ( new Trip(cursor.getInt (0),cursor.getString (1), cursor.getString (2),cursor.getString(3),cursor.getString (4),cursor.getString (5),cursor.getString (6),cursor.getInt (7)));
            cursor . moveToNext () ;

        }
        cursor.close () ;
        db.close () ;

        return trips;
    }
    //super search
    public ArrayList<Trip> superSearch(String name ,String destination, String date){
        ArrayList<Trip> trips = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ Trips.TABLE_NAME +" where" +
                " "+ Trips.COL_NAME_NAME +" Like " + "'%" + name + "%'" +
                "and "+ Trips.COL_NAME_DESTINATION +" like" + "'%" + destination + "%'" +
                "and "+ Trips.COL_NAME_DATE +" like" + "'%" + date + "%'",null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor . getCount () ; i ++){
            trips.add ( new Trip(cursor.getInt (0),cursor.getString (1), cursor.getString (2),cursor.getString(3),cursor.getString (4),cursor.getString (5),cursor.getString (6),cursor.getInt (7)));
            cursor . moveToNext () ;

        }
        cursor.close () ;
        db.close () ;

        return trips;
    }

    //updatetrip
    public long updateTrip(long id, String name, String destination, String date, String description, String accommodation, String vehicle,int rra){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Trips.COL_NAME_NAME ,name);
        contentValues.put(Trips.COL_NAME_DESTINATION ,destination);
        contentValues.put(Trips.COL_NAME_DATE,date);
        contentValues.put(Trips.COL_NAME_DESCRIPTION ,description);
        contentValues.put(Trips.COL_NAME_ACCOMMODATION,accommodation);
        contentValues.put(Trips.COL_NAME_VEHICLE,vehicle);
        contentValues.put(Trips.COL_NAME_RRA,rra);


        return db.update(Trips.TABLE_NAME,contentValues,Trips.COL_NAME_ID + " = ?" ,new String[]{Long.toString(id)});
    }

    //take trip by id
    public Trip getTrip(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + Trips.TABLE_NAME + " WHERE " + Trips.COL_NAME_ID + "=" + id,null);
        cursor.moveToFirst();
        Trip trip = new Trip(cursor.getInt (0),cursor.getString (1), cursor.getString (2),cursor.getString(3),cursor.getString (4),cursor.getString (5),cursor.getString (6),cursor.getInt (7) );
        cursor.close();
        db.close();
        return trip;
    }
    // take expenses by id
    public Expensess getExpenses(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + Expenses.TABLE_NAME + " WHERE " + Expenses.COL_NAME_ID + "=" + id,null);
        cursor.moveToFirst();
        Expensess expenses = new Expensess(cursor.getInt (0),cursor.getString (1), cursor.getString (2),cursor.getString(3),cursor.getString (4),cursor.getString (5),cursor.getInt (6) );
        cursor.close();
        db.close();
        return expenses;
    }


}


class Trips {
    public static final String TABLE_NAME = "trips";
    public static final String COL_NAME_ID = "id";
    public static final String COL_NAME_NAME = "name";
    public static final String COL_NAME_DESTINATION = "destination";
    public static final String COL_NAME_DATE = "date";
    public static final String COL_NAME_DESCRIPTION = "description";
    public static final String COL_NAME_ACCOMMODATION = "accommodation";
    public static final String COL_NAME_VEHICLE = "vehicle";
    public static final String COL_NAME_RRA = "rra";


    public  static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_NAME_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME_NAME + " TEXT NOT NULL," +
                    COL_NAME_DESTINATION + " TEXT NOT NULL ," +
                    COL_NAME_DATE + " TEXT NOT NULL," +
                    COL_NAME_DESCRIPTION + " TEXT ," +
                    COL_NAME_ACCOMMODATION + " TEXT NOT NULL ," +
                    COL_NAME_VEHICLE + " TEXT NOT NULL ," +
                    COL_NAME_RRA + " INTEGER NOT NULL)";

    public  static final  String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME ;
}
class Expenses {
    public static final String TABLE_NAME ="expenses";
    public static final String COL_NAME_ID ="id";
    public static final String COL_NAME_TYPE ="type";
    public static final String COL_NAME_DATE = "date";
    public static final String COL_NAME_TIME ="time";
    public static final String COL_NAME_AMOUNT ="amount";
    public static final String COL_NAME_CMT = "comment";
    public static final String COL_NAME_TRIP_ID ="trip_id";
    public  static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COL_NAME_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME_TYPE + " TEXT NOT NULL," +
                    COL_NAME_DATE + " TEXT NOT NULL ," +
                    COL_NAME_TIME + " TEXT NOT NULL," +
                    COL_NAME_AMOUNT + " TEXT NOT NULL," +
                    COL_NAME_CMT + " TEXT NOT NULL," +
                    COL_NAME_TRIP_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + COL_NAME_TRIP_ID + ") " +
                    "REFERENCES " + Trips.TABLE_NAME + "(" + Trips.COL_NAME_ID + ") )";
    public  static final  String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME ;


}
