package com.example.covidapp.api

import com.example.covidapp.model.ResponseCovidItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("indonesia")
    fun getData(): Call<ArrayList<ResponseCovidItem>>
}