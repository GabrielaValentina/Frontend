package com.example.gabri.licentafrontend.Repository;

import android.util.Log;

import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.TrainStationAdapter;
import com.example.gabri.licentafrontend.Domain.User;
import com.example.gabri.licentafrontend.Domain.UserDevice;
import com.example.gabri.licentafrontend.Retrofit.RetrofitAPI_TRAIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by gabri on 5/26/2018.
 */

public class LocalRepository {

    private List<UserDevice> list_user_device;
    private List<User> list_all_users;
    private HashMap<Long, ArrayList<TrainStationAdapter>> hashMap_stations;

    public LocalRepository() {
    }

    public void add_new_user(UserDevice user){
        this.list_user_device.add(user);
    }

    public List<User> getAllUsers(){
        Long id = Long.parseLong("1");
        list_all_users = new ArrayList<>();
        list_all_users.add(new User(id, "Ana", "Prenume", "ana@yahoo.com", "ana", "0743117867"));
        id = Long.parseLong("2");
        list_all_users.add(new User(id, "Gabi", "Vali", "gabi@yahoo.com", "g", "0743117869"));
        id = Long.parseLong("3");
        list_all_users.add(new User(id, "Alexandru", "Ion", "alex@yahoo.com", "ana", "0743117867"));
       return list_all_users;
    }

    public List<UserDevice> getList_user_device() {
        return list_user_device;
    }

    public void setHashMap_stations(HashMap<Long, ArrayList<TrainStationAdapter>> hashMap_stations) {
        this.hashMap_stations = hashMap_stations;
    }

    public HashMap<Long, ArrayList<TrainStationAdapter>> getHashMap_stations() {
        return hashMap_stations;
    }

    public void add_new_stations(Long id_route, ArrayList<TrainStationAdapter> stations){
        Log.d("localRepository", id_route + " " + stations.size());
        this.hashMap_stations.put(id_route, stations);
    }

    public ArrayList<TrainStationAdapter> getStationsByRoute(Long id_route){
        ArrayList<TrainStationAdapter> train_station_adapter = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.217:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RetrofitAPI_TRAIN retrofitAPI_train = retrofit.create(RetrofitAPI_TRAIN.class);
        Call<List<StationMapper1>> call = retrofitAPI_train.getStationsByRouteId(id_route);

        call.enqueue(new Callback<List<StationMapper1>>() {
            @Override
            public void onResponse(retrofit.Response<List<StationMapper1>> response, Retrofit retrofit) {

                try {
                    for(StationMapper1 station : response.body()){
                        if(station.getStation_number_in_route() ==1)
                            train_station_adapter.add(new TrainStationAdapter(station.getName(),  "-", station.getDeparture_time().substring(11,16)));
                        else
                        if( station.getStation_number_in_route() == response.body().size())
                            train_station_adapter.add(new TrainStationAdapter(station.getName(),  station.getArrival_time().substring(11,16), "-"));
                        else
                            train_station_adapter.add(new TrainStationAdapter(station.getName(),  station.getArrival_time().substring(11,16), station.getDeparture_time().substring(11,16)));
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "error onResponse");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
        return train_station_adapter;
    }
}
