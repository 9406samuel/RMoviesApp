package com.samuelcabezas.rmoviesapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        fun getApiClient(baseUrl: String): MovieService {
            return getInstance(baseUrl).create(MovieService::class.java)
        }

        private fun getInstance(url: String): Retrofit {

            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
        }

        private fun getOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().readTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .build()
        }
    }
}