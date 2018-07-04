package com.example.gabri.licentafrontend.Retrofit;

import com.example.gabri.licentafrontend.Domain.RecommendedRouteRequest;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by gabri on 6/11/2018.
 */

public interface RetrofitAPI_RECOMMENDED_ROUTES {
    //get all recommended routes
    @GET("/review/getAllRecommendedRoutes/{id_user}")
    Call<List<RecommendedRouteRequest>> getAllRecommendedRoutes(@Path("id_user") String id_user);
}
