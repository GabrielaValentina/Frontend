package com.example.gabri.licentafrontend.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.Mappers.LandmarkDetailsMapper;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteMapper;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteRequestForBD;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.User;
import com.squareup.okhttp.Route;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by gabri on 6/16/2018.
 */



public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABSE_NAME = "trainapp.db";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_LAST_NAME = "last_name";
    public static final String USER_COLUMN_FIRST_NAME = "first_name";
    public static final String USER_COLUMN_EMAIL = "email_address";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_STATUS = "account_status";

    public static final String ROUTE_TABLE_NAME = "route";
    public static final String ROUTE_COLUMN_ID = "id";
    public static final String ROUTE_COLUMN_DATA = "time";
    public static final String ROUTE_COLUMN_TRAIN = "train";

    public static final String STATION_TABLE_NAME = "station";
    public static final String STATION_COLUMN_ID = "id";
    public static final String STATION_COLUMN_NAME = "name";
    public static final String STATION_COLUMN_NUMBER = "number";
    public static final String STATION_COLUMN_ARRIVAL_TIME = "arrival_time";
    public static final String STATION_COLUMN_DEPARTURE_TIME = "departure_time";
    public static final String STATION_COLUMN_ROUTE_ID = "route_id";

    public static final String LANDMARK_TABLE_NAME = "landmark";
    public static final String LANDMARK_COLUMN_ID = "id";
    public static final String LANDMARK_COLUMN_LATITUDE = "latitude";
    public static final String LANDMARK_COLUMN_LONGITUDE = "longitude";
    public static final String LANDMARK_COLUMN_LOCATION = "location";
    public static final String LANDMARK_COLUMN_DESCRIPTION= "description";
    public static final String LANDMARK_COLUMN_ROUTE_ID = "route_id";
    public static final String LANDMARK_COLUMN_DEPARTURE = "departure";
    public static final String LANDMARK_COLUMN_DESTINATION = "destination";

    public DatabaseHelper(Context context){
        super(context, DATABSE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table user " +
                        "(id long primary key, last_name text, first_name text, email_address text, password text, account_status integer)"
        );

        db.execSQL(
                "create table route " +
                        "(id long primary key, time datetime, train text)"
        );

        db.execSQL(
                "create table landmark " +
                        "(id integer primary key, latitude float, longitude float, location text, description text, route_id long, departure text, destination text)"
        );

        db.execSQL(
                "create table station " +
                        "(id long primary key, name text, number integer, arrival_time datetime, departure_time datetime, route_id long)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS user");
      //  db.execSQL("DROP TABLE IF EXISTS landmark");
   //     db.execSQL("DROP TABLE IF EXISTS route");
      //  db.execSQL("DROP TABLE IF EXISTS station");
       // onCreate(db);
    }

    public String findRouteByTrainId(String train){
        SQLiteDatabase db = this.getReadableDatabase();
        String response  = "no";
        Cursor res =  db.rawQuery("select * from route where train = '" + train + "';", null );
        res.moveToFirst();
        if(res != null && res.getCount() != 0) {
            Log.d("route = ", Long.parseLong(res.getString(res.getColumnIndex(ROUTE_COLUMN_ID)))+"");
            response = res.getString(res.getColumnIndex(ROUTE_COLUMN_ID));
            if (!res.isClosed())  {
                res.close();
            }
        }
        return response;
    }

    public boolean insertNewUser (Long id, String last_name, String first_name, String email_address, String password, int account_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("last_name", last_name);
        contentValues.put("first_name", first_name);
        contentValues.put("email_address", email_address);
        contentValues.put("password", password);
        contentValues.put("account_status", account_status);
        db.insert("user", null, contentValues);
        Log.d("insert ",last_name);
        return true;
    }

    public boolean insertNewRoute (Long id, String time, String train) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("time", time);
        contentValues.put("train", train);
        db.insert("route", null, contentValues);
        return true;
    }

    public boolean insertNewStation (Long id, String name, int number, String arrival_time, String departure_time, Long route_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("number", number);
        contentValues.put("arrival_time", arrival_time);
        contentValues.put("departure_time", departure_time);
        contentValues.put("route_id", route_id);
        db.insert("station", null, contentValues);
        return true;
    }

    public boolean insertNewLandmark (Long id, Float latitude, Float longitude, String location,
                                      String description, Long id_route, String departure, String destination) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("id = " +id, "location = "+location);
        contentValues.put("id", id);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("location", location);
        contentValues.put("description", description);
        contentValues.put("route_id", id_route);
        contentValues.put("departure", departure);
        contentValues.put("destination", destination);
        db.insert("landmark", null, contentValues);
        return true;
    }

    public ArrayList<LandmarkDetailsMapper> getAllLandmarks() {
        ArrayList<LandmarkDetailsMapper> landmarks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from landmark", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            landmarks.add(new LandmarkDetailsMapper(Integer.parseInt(res.getString(res.getColumnIndex(LANDMARK_COLUMN_ID))),
                    Float.parseFloat(res.getString(res.getColumnIndex(LANDMARK_COLUMN_LATITUDE))),
                    Float.parseFloat(res.getString(res.getColumnIndex(LANDMARK_COLUMN_LONGITUDE))),
                    res.getString(res.getColumnIndex(LANDMARK_COLUMN_LOCATION)),
                    res.getString(res.getColumnIndex(LANDMARK_COLUMN_DESCRIPTION)),
                    Long.parseLong(res.getString(res.getColumnIndex(LANDMARK_COLUMN_ROUTE_ID))),
                    res.getString(res.getColumnIndex(LANDMARK_COLUMN_DEPARTURE)),
                    res.getString(res.getColumnIndex(LANDMARK_COLUMN_DESTINATION))));
            Log.d("indb", landmarks.size()+"");
            res.moveToNext();
        }
        return landmarks;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from user", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            users.add(new User(Long.parseLong(res.getString(res.getColumnIndex(USER_COLUMN_ID))),
                    res.getString(res.getColumnIndex(USER_COLUMN_FIRST_NAME)),
                    res.getString(res.getColumnIndex(USER_COLUMN_LAST_NAME)),
                    res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)),
                    res.getString(res.getColumnIndex(USER_COLUMN_PASSWORD)),
                    res.getString(res.getColumnIndex(USER_COLUMN_STATUS))));
            res.moveToNext();
        }
        return users;
    }

    public ArrayList<StationMapper1> getAllStations() {
        ArrayList<StationMapper1> stations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from station", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            stations.add(new StationMapper1(Long.parseLong(res.getString(res.getColumnIndex(STATION_COLUMN_ID))),
                    res.getString(res.getColumnIndex(STATION_COLUMN_NAME)),
                    Long.parseLong(res.getString(res.getColumnIndex(STATION_COLUMN_ROUTE_ID))),
                    Integer.parseInt(res.getString(res.getColumnIndex(STATION_COLUMN_NUMBER))),
                    res.getString(res.getColumnIndex(STATION_COLUMN_ARRIVAL_TIME)),
                    res.getString(res.getColumnIndex(STATION_COLUMN_DEPARTURE_TIME))));
            res.moveToNext();
        }
        return stations;
    }

    public ArrayList<RouteRequestForBD> getAllRoutes() {
        ArrayList<RouteRequestForBD> routes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from route", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            routes.add(new RouteRequestForBD(Long.parseLong(res.getString(res.getColumnIndex(ROUTE_COLUMN_ID))),
                    res.getString(res.getColumnIndex(ROUTE_COLUMN_DATA)),
                    res.getString(res.getColumnIndex(ROUTE_COLUMN_TRAIN))));
            res.moveToNext();
        }
        return routes;
    }

    public void deleteUserTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ USER_TABLE_NAME);
    }

    public void deleteLandmarkTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ LANDMARK_TABLE_NAME);
    }

    public void deleteRouteTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ ROUTE_TABLE_NAME);
    }

    public void deleteStationTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from "+ STATION_TABLE_NAME);
    }

    public User findUser(String email_address, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        User new_user = new User();
        Cursor res =  db.rawQuery("select * from user where email_address = '" + email_address + "' and password = '" + password + "';", null );
        res.moveToFirst();
        if(res != null && res.getCount() != 0) {
            Log.d("user = ", Long.parseLong(res.getString(res.getColumnIndex(USER_COLUMN_ID)))+"");
             new_user = new User(Long.parseLong(res.getString(res.getColumnIndex(USER_COLUMN_ID))),
                    res.getString(res.getColumnIndex(USER_COLUMN_FIRST_NAME)),
                    res.getString(res.getColumnIndex(USER_COLUMN_LAST_NAME)),
                    res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)),
                    res.getString(res.getColumnIndex(USER_COLUMN_PASSWORD)),
                    res.getString(res.getColumnIndex(USER_COLUMN_STATUS)));
            if (!res.isClosed())  {
                res.close();
            }
        }
        return new_user;
    }
}
