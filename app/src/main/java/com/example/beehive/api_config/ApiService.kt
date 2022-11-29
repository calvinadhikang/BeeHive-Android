package com.example.beehive.api_config

import com.example.beehive.data.BasicDRO
import com.example.beehive.data.UserLoginDTO
import com.example.beehive.data.UserRegisterDTO
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")

    @GET("cek/email")
    fun cekEmail(
        @Query("email") email:String
    ): Call<BasicDRO>

    @POST("login")
    fun login(@Body userLoginData: UserLoginDTO): Call<UserDRO>

    @POST("register")
    fun register(@Body userRegisterData: UserRegisterDTO): Call<UserDRO>
}