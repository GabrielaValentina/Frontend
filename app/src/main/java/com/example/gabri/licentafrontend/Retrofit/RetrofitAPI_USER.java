package com.example.gabri.licentafrontend.Retrofit;

import com.example.gabri.licentafrontend.Domain.Mappers.UserMapper;
import com.example.gabri.licentafrontend.Domain.User;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import retrofit.Call;
import retrofit.CallAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by gabri on 5/6/2018
 */

public interface RetrofitAPI_USER {
    //login
    @GET("/user/login/{email_address}/{password}")
    Call<User> login(@Path("email_address") String email_address,
                     @Path("password") String password,
                     @Query("format") String format);

    //add new user
    @POST("/user/addNewUser")
    Call<String> addNewUser(@Body UserMapper user, @Query("format") String format);

    //get all users
    @GET("/user/getUsersConfirmated")
    Call<List<User>> getAllUsers();

    //verification code
    @GET("/user/verificationCode/{email_address}/{code}")
    Call<String> verificationCode(@Path("email_address") String email_address,
                     @Path("code") String code,
                     @Query("format") String format);

}
