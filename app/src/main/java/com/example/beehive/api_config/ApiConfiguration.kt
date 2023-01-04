package com.example.beehive.api_config

import com.example.beehive.env
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfiguration {
    companion object {
        fun getApiService() : ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            var retrofit:Retrofit? = null
            if(env.mode=="production"){
                retrofit = Retrofit.Builder().baseUrl(env.URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            }else{
                retrofit = Retrofit.Builder().baseUrl(env.URL_LOCAL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client).build()
                env.URLIMAGE = "http://10.0.2.2:8000/"
            }
            return retrofit.create(ApiService::class.java)
        }
    }
}