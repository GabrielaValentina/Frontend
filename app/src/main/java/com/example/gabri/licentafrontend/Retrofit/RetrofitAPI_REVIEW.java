package com.example.gabri.licentafrontend.Retrofit;

import com.example.gabri.licentafrontend.Domain.Mappers.RouteReviewMapper;
import com.example.gabri.licentafrontend.Domain.Review;
import com.example.gabri.licentafrontend.Domain.User;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by gabri on 5/14/2018.
 */

public interface RetrofitAPI_REVIEW {

    //save a review
    @POST("/review/addNewReview")
    Call<Review> savePost(@Body Review post);

    //reviews for trains
    @GET("/review/getAllTrainsReviews")
    Call<List<Review>> getAllTrainsReviews();

    //reviews for routes
    @GET("/review/getAllRoutesReviews")
    Call<List<RouteReviewMapper>> getAllRoutesReviews();
}
