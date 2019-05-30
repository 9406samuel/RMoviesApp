package com.samuelcabezas.rmoviesapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory;
import com.samuelcabezas.rmoviesapp.utils.Constants
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        fun getApiClient(): MovieService {
            return getInstance().create(MovieService::class.java)
        }

        private fun getInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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