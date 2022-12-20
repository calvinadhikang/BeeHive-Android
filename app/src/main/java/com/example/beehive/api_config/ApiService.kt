package com.example.beehive.api_config

import com.example.beehive.data.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")

    @GET("nanoid")
    fun getNanoID(): Call<BasicDRO>

    @GET("cek/email")
    fun cekEmail(
        @Query("email") email:String
    ): Call<BasicDRO>

    @POST("login")
    fun login(@Body userLoginData: UserLoginDTO): Call<UserDRO>

    @POST("register")
    fun register(@Body userRegisterData: UserRegisterDTO): Call<UserDRO>

    //get user profile

    @GET("user/get")
    fun getProfile(
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<UserDRO>

    @POST("user/topup")
    fun TopUp(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body topUpData: TopUpDTO,
    ): Call<BasicDRO>

    @GET("category/fetch")
    fun fetchCategory(
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<ListCategoryDRO>



    @POST("sting/lelang/make")
    fun CreateLelangSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body lelangStingData: CreateLelangStingDTO
    ): Call<BasicDRO>
}