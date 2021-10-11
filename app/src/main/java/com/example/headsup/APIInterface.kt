package com.example.headsup

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/celebrities/")
    fun getCelebrities(): Call<ArrayList<Celebrity>>

    @POST("/celebrities/")
    fun addCelebrity(@Body celebrityData: Celebrity): Call<Celebrity>

    @GET("/celebrities/{id}")
    fun getCelebrity(@Path("id") id: Int): Call<Celebrity>

    // PUT replaces the full object (use PATCH to change individual fields)
    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id: Int, @Body celebrityData: Celebrity): Call<Celebrity>

    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id: Int): Call<Void>
}