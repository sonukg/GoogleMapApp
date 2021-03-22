package com.sonukg.theonepractical.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebCongif {

    val userConfig = Retrofit.Builder().run {
        baseUrl("https://maps.googleapis.com/maps/api/")
        addConverterFactory(GsonConverterFactory.create())
        build()
    }.create(ClientConfig::class.java)

}