package com.example.beehive.api_config

import com.example.beehive.data.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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

    @GET("beeworker")
    fun fetchBeeworker(
    ): Call<ListUserDRO>

//    USER SECTION
    @GET("user/get")
    fun getProfile(
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<UserDRO>

    @GET("user/get/token")
    fun getProfileNoAuth(
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

//    @GET("sting/category/{id}")
//    fun fetchStingByCategory(
//        @Path("id") id: String
//    ): Call<StingByCategoryDRO>

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
    fun fetchMostSting(): Call<StingDRO>

    @GET("sting/category/{category}")
    fun fetchStingByCategory(
//        @Query("REMEMBER_TOKEN") remember_token:String,
        @Path("category") category:Int
    ): Call<ListStingDRO>

    //SECTION TRANSACTION STING

    @GET("sting/transaction/current")
    fun getOngoingTransactionSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<TransactionStingDRO>

    @GET("sting/transaction/fetch")
    fun fetchTransactionSting(
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<ListTransactionStingDRO>


    @GET("sting/transaction/fetch/{status}")
    fun fetchTransactionStingByStatus(
        @Path("status") status:String,
        @Query("REMEMBER_TOKEN") remember_token:String
    ): Call<ListTransactionStingDRO>

    @POST("sting/buy/{kode}/{mode}")
    fun buySting(
        @Path("kode") kode:String,
        @Path("mode") mode:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body buyStingData: BuyStingDTO
    ): Call<BasicDRO>

    @Streaming
    @GET("download/{name}")
    fun downloadSubmission(
        @Path("name") name:String,
    ): Call<ResponseBody>

    @GET("sting/transaction/current")
    fun getCurrentProject(
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<TransactionStingDRO>

    @GET("sting/transaction/{id}")
    fun getTransactionSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<TransactionStingDRO>

    @GET("sting/transaction/{id}/cancel")
    fun cancelTransactionSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<BasicDRO>

    @POST("sting/transaction/{id}/decline")
    fun declineTransactionSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body declineTransactionStingData: DeclineTransactionStingDTO
    ): Call<BasicDRO>

    @POST("sting/transaction/{id}/accept")
    fun completeTransactionSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body completeTransactionStingData: CompleteTransactionStingDTO
    ): Call<BasicDRO>

    @GET("sting/transaction/{id}/complains")
    fun fetchComplainTransactionSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<ListComplainDRO>

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

    @GET("sting/lelang/{id}")
    fun getLelangSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<LelangStingDRO>

    @GET("sting/lelang/{id}/cancel")
    fun cancelLelangSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<BasicDRO>

    @POST("sting/lelang/{id}/decline")
    fun declineLelangSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body declineTransactionStingData: DeclineTransactionStingDTO
    ): Call<BasicDRO>

    @POST("sting/lelang/{id}/accept")
    fun completeLelangSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
        @Body completeTransactionStingData: CompleteTransactionStingDTO
    ): Call<BasicDRO>

    @GET("sting/lelang/{id}/complains")
    fun fetchComplainLelangSting(
        @Path("id") id:String,
        @Query("REMEMBER_TOKEN") remember_token:String,
    ): Call<ListComplainDRO>
}


interface ExternalApiService {
    //ini buat api external
    @Headers("Content-Type: application/json")

    @GET("latest/IDR")
    fun fetchConversionRate( ): Call<ConvertionRatesDRO>
}