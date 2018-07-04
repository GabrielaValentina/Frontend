package com.example.gabri.licentafrontend.Retrofit;


import com.example.gabri.licentafrontend.Domain.Mappers.RouteMapper;
import com.example.gabri.licentafrontend.Domain.Mappers.RouteRequestForBD;
import com.example.gabri.licentafrontend.Domain.Mappers.StationMapper1;
import com.example.gabri.licentafrontend.Domain.SearchedRoute;
import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by gabri on 5/6/2018.
 */

public interface RetrofitAPI_TRAIN {
    //verify if a train exists
    @GET("/route/findTrain/{id_train}")
    Call<String> findATrainById(@Path("id_train") String id_train,
                                @Query("format") String format);

    @GET("/route/getAllRoutes")
    Call<List<RouteRequestForBD>> getAllRoutes();

    @GET("/route/getStations/{route_id}")
    Call<List<StationMapper1>> getStationsByRouteId(@Path("route_id") Long route_id);

    @GET("/route/getAllStations")
    Call<List<StationMapper1>> getAllStations();

    @GET("/route/getSearchedRoutes/{departure}/{arrival}")
    Call<List<SearchedRoute>> getSearchedRoutes(@Path("departure") String departure,
                                                @Path("arrival") String arrival);
}
