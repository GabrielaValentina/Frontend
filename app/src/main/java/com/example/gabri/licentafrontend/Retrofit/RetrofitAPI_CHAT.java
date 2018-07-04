package com.example.gabri.licentafrontend.Retrofit;

import com.example.gabri.licentafrontend.Domain.Mappers.ChatMessageMapper;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by gabri on 5/25/2018.
 */

public interface RetrofitAPI_CHAT {
    //send message
    @POST("/sendMessage")
    Call<ChatMessageMapper> sendNewMessage(@Body ChatMessageMapper message);

    //get all messages
    @GET("/getMessages")
    Call<List<ChatMessageMapper>> getAllMessages();
}
