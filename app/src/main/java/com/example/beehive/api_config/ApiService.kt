package com.example.beehive.api_config

import com.example.beehive.data.*
import okhttp3.MultipartBody
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


//    USER SECTION
    @GET("user/get")
    fun getProfile(
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<UserDRO>

    @POST("user/topup")
    fun TopUp(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body topUpData: TopUpDTO,
    ): Call<BasicDRO>

    @POST("user/password")
    fun changePassword(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body changePasswordData: ChangePasswordDTO,
    ): Call<BasicDRO>

    @POST("user/profile")
    fun changeProfile(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body changeProfileData: ChangeProfileUserDTO,
    ): Call<BasicDRO>

    @Multipart
    @POST("user/picture")
    fun changePicture(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Part picture: MultipartBody.Part
    ): Call<BasicDRO>

    //CATEGORY SECTION
    @GET("category/fetch")
    fun fetchCategory(
    ): Call<ListCategoryDRO>

    @GET("category/fetch")
    fun fetchCategoryNoAuth(): Call<ListCategoryDRO>

    //STING SECTION
    @GET("sting/{id}")
    fun getStingDetail(
        @Path("id") id:String
    ): Call<StingDRO>

    @GET("sting/fetch")
    fun fetchSting(): Call<ListStingDRO>

    @GET("sting/most")
    fun fetchMostSting(): Call<MostStingDRO>

    //SECTION TRANSACTION STING
    @GET("sting/transaction/fetch")
    fun fetchTransactionSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<ListTransactionStingDRO>

    @GET("sting/category/{category}")
    fun fetchTransactionStingByCategory(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Path("category") category:Int
    ): Call<ListTransactionStingDRO>

    @GET("sting/transaction/fetch/{status}")
    fun fetchTransactionStingByStatus(
        @Path("status") status:String,
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<ListTransactionStingDRO>

    @POST("sting/buy/{kode}/{mode}")
    fun buySting(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Path("kode") kode:String,
        @Path("mode") mode:String,
        @Body buyStingData: BuyStingDTO
    ): Call<BasicDRO>


    //SECTION LELANG
    @GET("sting/lelang/fetch")
    fun fetchLelangSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<ListLelangStingDRO>
    @POST("sting/lelang/make")
    fun CreateLelangSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body lelangStingData: CreateLelangStingDTO
    ): Call<BasicDRO>
}