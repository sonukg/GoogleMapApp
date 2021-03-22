package com.sonukg.theonepractical.apis

import com.google.gson.JsonObject
import com.sonukg.theonepractical.model.Place
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientConfig {
    @GET("place/search/json?")
    suspend fun getPlaces(
        @Query("location", encoded = true) location: String,
        @Query("rankby", encoded = true) rankby: String,
        @Query("types", encoded = true) types: String,
        @Query("sensor", encoded = true) sensor: Boolean,
        @Query("key", encoded = true) key: String):JsonObject
}