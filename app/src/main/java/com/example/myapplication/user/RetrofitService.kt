package com.example.myapplication

import com.example.myapplication.user.Signup
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    @POST("user/signup/")
    fun register(
        @Body signup: Signup
    ): Call<Signup>


    @POST("user/signin/")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<User>

    @POST("timer/")
    fun timer(
        @Body timer: Timer
    ): Call<Timer>

    @PUT("timer")
    fun status(
        @Query("status") status: String,
    ): Call<Timer>

}