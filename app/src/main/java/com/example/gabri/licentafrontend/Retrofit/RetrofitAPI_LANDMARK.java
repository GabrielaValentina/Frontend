package com.example.gabri.licentafrontend.Retrofit;

import com.example.gabri.licentafrontend.Domain.Landmark;
import com.example.gabri.licentafrontend.Domain.Mappers.LandmarkDetailsMapper;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by gabri on 6/11/2018.
 */

public interface RetrofitAPI_LANDMARK {
    @GET("/landmark/getAllLandmarksWithDetails")
    Call<List<LandmarkDetailsMapper>> getAllLandmarks();

    @GET("/landmark/getAllLandmarks/{route_id}")
    Call<List<Landmark>> getAllLandmarksByRoute(@Path("route_id") Long route_id);

    @POST("/landmark/add")
    Call<Landmark> addNewLandmark(@Body Landmark landmark,
                                  @Query("format") String format);
}
